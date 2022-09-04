package tk.wuzk.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import tk.wuzk.annotation.CustomJson;
import tk.wuzk.annotation.CustomJsons;
import tk.wuzk.aop.bean.JsonFilterProvider;
import tk.wuzk.aop.bean.JsonPropertyFilter;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonReturnValueHandler implements HandlerMethodReturnValueHandler {
    /**
     * 使用guava缓存已处理方法
     */
    private LoadingCache<MethodParameter, ObjectWriter> cache = CacheBuilder.newBuilder()
            .build(new CacheLoader<MethodParameter, ObjectWriter>() {
                @Override
                public ObjectWriter load(MethodParameter parameter) {
                    return build(parameter);
                }
            });


    /**
     * 指定基础的ObjectMapper
     */
    private ObjectMapper baseObjectMapper = new ObjectMapper()
            .setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
                @Override
                public Object findFilterId(Annotated annotated) {
                    // 提取待序列化对象的class作为FilterId
                    if (annotated instanceof AnnotatedClass) {
                        return annotated.getRawType();
                    }
                    return null;
                }
            });

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        CustomJson customJson = methodParameter.getMethodAnnotation(CustomJson.class);
        CustomJsons customJsons = methodParameter.getMethodAnnotation(CustomJsons.class);
        Assert.isTrue(customJson == null || customJsons == null, "only use one of CustomJson or CustomJsons ");
        return customJson != null || customJsons != null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest) throws Exception {
        modelAndViewContainer.setRequestHandled(true);

        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 获取ObjectWriter写入结果
        cache.get(methodParameter).writeValue(response.getWriter(), o);
    }

    /**
     * 将MethodParameter转换为ObjectWriter
     */
    private ObjectWriter build(MethodParameter parameter) {
        // key为CustomJson的targetClass，value为includes,excludes包装后的JsonPropertyFilter
        Map<Object, PropertyFilter> cache;
        CustomJson customJson = parameter.getMethodAnnotation(CustomJson.class);
        if (customJson == null) {
            cache = Arrays.stream(parameter.getMethodAnnotation(CustomJsons.class).value())
                    .collect(Collectors.toMap(CustomJson::targetClass, JsonPropertyFilter::build));
        } else {
            cache = Collections.singletonMap(customJson.targetClass(), JsonPropertyFilter.build(customJson));
        }
        return baseObjectMapper.writer(new JsonFilterProvider(cache));
    }
}

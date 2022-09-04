package com.toss.web.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodIntrospector.MetadataLookup;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.toss.web.common.IRoute;
import com.toss.web.common.RequestMapping;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * vertx Http模块
 * 
 * @author wuzk
 *
 */
@Component
public class HttpVerticle extends AbstractVerticle {
	private static Logger logger = LoggerFactory.getLogger(HttpVerticle.class);
	@Autowired
	private Router router;
	@Autowired
	private Vertx vertx;
	@Autowired
	private List<IRoute> routers;
	@Value("${http.port}")
	private int port;

	@Override
	public void start() throws Exception {
		HttpServer httpServer = vertx.createHttpServer();
		routers.forEach(controller -> {
			Map<Method, RequestMapping> annotatedMethods = MethodIntrospector.selectMethods(controller.getClass(),
					(MetadataLookup<RequestMapping>) method -> {
						return AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
					});
			annotatedMethods.forEach((method, request) -> {
				Class<?>[] params = method.getParameterTypes();
				Assert.isTrue(params.length == 1, "RequestMapping参数只能有RoutingContext参数");
				Assert.isAssignable(RoutingContext.class, params[0], "RequestMapping注解的方法必须有RoutingContext参数");
				router.route(request.method(), request.path()).handler(content -> {
					try {
						method.invoke(controller, content);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				});
			});
		});
		httpServer.requestHandler(router::accept);
		logger.info("Http启动端口为" + port);
		httpServer.listen(port);
	}
}

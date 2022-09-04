package tk.wuzk.aop.bean;

import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.springframework.util.Assert;
import tk.wuzk.annotation.CustomJson;

import java.util.Arrays;
import java.util.List;

public class JsonPropertyFilter extends SimpleBeanPropertyFilter {
    private final List<String> includes;
    private final List<String> excludes;

    private JsonPropertyFilter(String[] includes, String[] excludes) {
        this.includes = Arrays.asList(includes);
        this.excludes = Arrays.asList(excludes);
    }

    @Override
    protected boolean include(PropertyWriter writer) {
        String name = writer.getName();
        if (includes.isEmpty()) {
            return !excludes.contains(name);
        }
        return includes.contains(name);
    }

    public static JsonPropertyFilter build(CustomJson customJson) {
        String[] includes = customJson.includes();
        String[] excludes = customJson.excludes();
        // includes，excludes两种类型仅能存在一种
        boolean allExists = includes.length != 0 && excludes.length != 0;
        Assert.isTrue(!allExists, "includes or excludes must be empty");
        return new JsonPropertyFilter(includes, excludes);
    }
}

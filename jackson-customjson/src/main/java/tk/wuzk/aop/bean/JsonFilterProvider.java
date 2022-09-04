package tk.wuzk.aop.bean;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;

import java.util.Map;

public class JsonFilterProvider extends FilterProvider {
    private final Map<Object, PropertyFilter> cache;

    public JsonFilterProvider(Map<Object, PropertyFilter> cache) {
        this.cache = cache;
    }

    @Override
    public BeanPropertyFilter findFilter(Object filterId) {
        return null;
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        return cache.get(filterId);
    }

}

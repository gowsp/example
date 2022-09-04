package tk.wuzk.annotation;

import java.lang.annotation.*;

/**
 * 组合注解
 *
 * @author hunter
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomJsons {
    /**
     * 数据处理
     */
    CustomJson[] value();
}

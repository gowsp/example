package tk.wuzk.annotation;

import java.lang.annotation.*;

/**
 * 自定义Json注解,includes excludes不能同时存在
 *
 * @author hunter
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomJson {
    /**
     * 目标类
     */
    Class targetClass();

    /**
     * 包含字段
     */
    String[] includes() default {};

    /**
     * 排除字段
     */
    String[] excludes() default {};
}
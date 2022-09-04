package com.toss.annotation;

import org.springframework.data.annotation.QueryAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态Sql
 */
@QueryAnnotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicSql {
    /**
     * 查询类,{@link org.springframework.data.jdbc.repository.query.Query}值,方法名
     */
    Class value();
}
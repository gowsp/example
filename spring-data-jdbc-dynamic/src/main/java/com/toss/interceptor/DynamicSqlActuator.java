package com.toss.interceptor;

import com.toss.utll.SpringBeanUtil;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

public class DynamicSqlActuator {
    private final Object target;
    private final boolean isCollection;
    private final Method sqlMethod;
    private final RowMapper<?> rowMapper;
    private final NamedParameterJdbcOperations operations;

    public DynamicSqlActuator(Method method, Object target, Method sqlMethod, RowMapper<?> rowMapper) {
        this.isCollection = ClassUtils.isAssignable(Iterable.class, method.getReturnType());
        this.operations = SpringBeanUtil.getBean(NamedParameterJdbcOperations.class);
        this.sqlMethod = sqlMethod;
        this.rowMapper = rowMapper;
        this.target = target;
    }

    public Object execute(Object[] args) {
        SelectStatementProvider statementProvider = (SelectStatementProvider)
                ReflectionUtils.invokeMethod(sqlMethod, target, args);
        String sql = statementProvider.getSelectStatement();
        Map<String, Object> params = statementProvider.getParameters();
        if (isCollection) {
            return operations.query(sql, params, rowMapper);
        } else {
            return operations.queryForObject(sql, params, rowMapper);
        }
    }
}

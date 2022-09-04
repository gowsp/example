package com.toss.interceptor;

import com.toss.annotation.DynamicSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RepositoryActuatorMapper {
    @Autowired
    private JdbcConverter converter;
    @Autowired
    private RelationalMappingContext context;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private QueryMappingConfiguration queryMappingConfiguration;

    private Map<Method, Optional<DynamicSqlActuator>> sqlActuators = new ConcurrentHashMap<>();

    public Optional<DynamicSqlActuator> getSqlActuator(Method method, RepositoryInformation repositoryInfo) {
        DynamicSql dynamicSql = AnnotationUtils.findAnnotation(method, DynamicSql.class);
        if (dynamicSql == null) {
            return Optional.empty();
        }
        Optional<DynamicSqlActuator> sqlActuator = sqlActuators.getOrDefault(method, Optional.empty());
        if (sqlActuator.isPresent()) {
            return sqlActuator;
        }
        Class clazz = dynamicSql.value();
        Object target = applicationContext.getBean(clazz);
        Query query = AnnotationUtils.findAnnotation(method, Query.class);
        Method sqlProviderMethod = ReflectionUtils.findMethod(clazz, query.value(), method.getParameterTypes());
        DynamicSqlActuator map = new DynamicSqlActuator(method, target, sqlProviderMethod, createMapper(method, repositoryInfo));
        sqlActuator = Optional.of(map);
        sqlActuators.put(method, sqlActuator);
        return sqlActuator;
    }

    private RowMapper<?> createMapper(Method method, RepositoryInformation repositoryInfo) {
        RepositoryMetadata metadata = AbstractRepositoryMetadata.getMetadata(repositoryInfo.getRepositoryInterface());
        Class<?> returnedObjectType = metadata.getReturnedDomainClass(method);
        return determineDefaultMapper(returnedObjectType);
    }

    private RowMapper<?> determineDefaultMapper(Class<?> domainType) {
        RelationalPersistentEntity<?> persistentEntity = context.getPersistentEntity(domainType);
        if (persistentEntity == null) {
            return SingleColumnRowMapper.newInstance(domainType, converter.getConversionService());
        }
        RowMapper<?> configuredQueryMapper = queryMappingConfiguration.getRowMapper(domainType);
        if (configuredQueryMapper != null) {
            return configuredQueryMapper;
        }
        EntityRowMapper<?> defaultEntityRowMapper = new EntityRowMapper<>(
                context.getRequiredPersistentEntity(domainType),
                converter
        );
        return defaultEntityRowMapper;
    }
}

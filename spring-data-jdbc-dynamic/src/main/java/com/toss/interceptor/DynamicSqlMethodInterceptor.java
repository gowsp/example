package com.toss.interceptor;

import com.toss.utll.SpringBeanUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.repository.core.RepositoryInformation;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Supplier;

public class DynamicSqlMethodInterceptor implements MethodInterceptor {
    private final RepositoryInformation repositoryInfo;
    private final Supplier<RepositoryActuatorMapper> actuatorMapper;


    public DynamicSqlMethodInterceptor(RepositoryInformation repositoryInfo) {
        this.repositoryInfo = repositoryInfo;
        this.actuatorMapper = SpringBeanUtil.getDelayedBean(RepositoryActuatorMapper.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Optional<DynamicSqlActuator> optional = actuatorMapper.get()
                .getSqlActuator(method, repositoryInfo);
        if (!optional.isPresent()) {
            return invocation.proceed();
        }
        return optional.get().execute(invocation.getArguments());
    }
}

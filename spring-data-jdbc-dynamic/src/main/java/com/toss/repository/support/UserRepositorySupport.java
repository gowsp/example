package com.toss.repository.support;

import com.toss.entity.User;
import org.mybatis.dynamic.sql.select.render.DefaultSelectStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositorySupport {
    public SelectStatementProvider find(User user) {
        Map<String, Object> params = new HashMap<>(2);
        StringBuilder sql = new StringBuilder("select * from t_user u where u.name = :name");
        params.put("name", user.getName());
        String password = user.getPassword();
        if (password != null) {
            sql.append(" u.password = :password");
            params.put("password", password);
        }
        return DefaultSelectStatementProvider.withSelectStatement(sql.toString()).withParameters(params).build();
    }
}

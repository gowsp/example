package com.toss.repository;

import com.toss.annotation.DynamicSql;
import com.toss.entity.User;
import com.toss.repository.support.UserRepositorySupport;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select * from t_user u where u.name = :name")
    User findBy(@Param("name") String userName);

    @Query("find")
    @DynamicSql(UserRepositorySupport.class)
    List<User> findBy(User user);
}

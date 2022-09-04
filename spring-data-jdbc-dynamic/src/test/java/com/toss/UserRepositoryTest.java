package com.toss;


import com.toss.entity.User;
import com.toss.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void findBy() {
        User user = new User();
        user.setName("name");
        repository.findBy(user);
    }
}
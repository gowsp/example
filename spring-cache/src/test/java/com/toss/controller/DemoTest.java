package com.toss.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {
    @Autowired
    ParameterNameDiscoverer discoverer;

    @Test
    public void paramName() {
        System.out.println(discoverer);
    }

}
package com.toss.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.validation.constraints.NotEmpty;

@ShellComponent
public class CustomCmd {
    private boolean connected;

    /**
     * 简单的方法
     */
    @ShellMethod("add")
    public int add(int x, int y) {
        return x + y;
    }

    /**
     * 对参数进行验证
     */
    @ShellMethod("connect sever")
    public void connect(@NotEmpty String ip) {
        System.out.println("connect to :" + ip);
        connected = true;
    }

    /**
     * 需要前置验证且参数需校验
     */
    @ShellMethod("send message")
    public void chat(@NotEmpty String msg) {
        System.out.println("msg :" + msg);
    }

    /**
     * 对chat方法进行可用性检测
     */
    @ShellMethodAvailability({"chat"})
    public Availability downloadAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("server not connected");
    }
}

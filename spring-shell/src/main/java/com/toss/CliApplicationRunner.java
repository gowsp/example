package com.toss;

import org.jline.reader.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.shell.CommandNotFound;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.FileInputProvider;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.StringReader;

@Order(0)
@Component
public class CliApplicationRunner implements ApplicationRunner {
    private final Shell shell;
    private final Parser parser;
    private final ConfigurableEnvironment environment;


    @Autowired
    public CliApplicationRunner(Shell shell, Parser parser, ConfigurableEnvironment environment) {
        this.shell = shell;
        this.parser = parser;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] sourceArgs = args.getSourceArgs();
        // 参数长度为0时启用控制台模式
        if (sourceArgs.length == 0) {
            return;
        }
        // 禁用控制台模式
        InteractiveShellApplicationRunner.disable(environment);
        // 拼接输入的参数
        String argStr = StringUtils.arrayToDelimitedString(sourceArgs, " ");
        // 发送参数给shell执行
        try {
            shell.run(new FileInputProvider(new StringReader(argStr), parser));
        } catch (CommandNotFound ex) {
            shell.run(new FileInputProvider(new StringReader("help"), parser));
        }
    }
}

package ink.wuzk.examples.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.ServiceLoader;

public class SpiTest {
    /**
     * 服务加载器：ServiceLoader
     * 使用方式：直接加载使用
     */
    @Test
    void jdk() {
        for (Waiter waiter : ServiceLoader.load(Waiter.class)) {
            System.out.println(waiter.greet());
        }
    }

    /**
     * 服务加载器：ExtensionLoader
     * 使用方式：别名加载使用
     */
    @Test
    void dubbo() {
        ExtensionLoader<Waiter> loader = ExtensionLoader.getExtensionLoader(Waiter.class);
        Waiter chinese = loader.getExtension("chinese");
        System.out.println(chinese.greet());
        Waiter american = loader.getExtension("american");
        System.out.println(american.greet());
    }

    /**
     * 服务加载器：SpringFactoriesLoader
     * 使用方式：直接加载使用
     */
    @Test
    void spring() {
        List<Waiter> waiters = SpringFactoriesLoader.loadFactories(Waiter.class, null);
        for (Waiter waiter : waiters) {
            System.out.println(waiter.greet());
        }
    }
}

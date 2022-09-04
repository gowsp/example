package ink.wuzk.examples.spi;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Waiter {

    String greet();
}

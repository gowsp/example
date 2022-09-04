package ink.wuzk.examples.spi;

public class Chinese implements Waiter {
    @Override
    public String greet() {
        return "你好";
    }
}

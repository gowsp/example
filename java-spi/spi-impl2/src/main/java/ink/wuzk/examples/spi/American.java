package ink.wuzk.examples.spi;

public class American implements Waiter {
    @Override
    public String greet() {
        return "hello";
    }
}

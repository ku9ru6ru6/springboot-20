package test;

/**
 * test
 *
 * @author caiguangzheng
 * date: 2019/9/24
 * mail: caiguangzheng@terminus.io
 * description:
 */
public class A {

    public void methodOne() {
        System.out.println("A");
        this.methodTow();
    }

    public void methodTow() {
        System.out.println("B");
    }
}

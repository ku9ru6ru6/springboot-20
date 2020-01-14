package test;

/**
 * test
 *
 * @author caiguangzheng
 * date: 2019/9/24
 * mail: caiguangzheng@terminus.io
 * description:
 */
public class B extends A {

    @Override
    public void methodOne() {
        super.methodOne();
        System.out.println("C");

    }

    @Override
    public void methodTow() {
        super.methodTow();
        System.out.println("D");
    }
}

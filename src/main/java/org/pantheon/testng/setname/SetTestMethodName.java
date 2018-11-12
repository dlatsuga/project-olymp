package org.pantheon.testng.setname;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class SetTestMethodName extends SetTestMethodNameBase {

    @DataProvider(name = "dtls")
    public static Object[][] dataProvider() {
        return new Object[][]{{"First"}, {"Second"}};
    }

    @Factory(dataProvider = "dtls")
    public SetTestMethodName(String suffix) {
        this.suffix = suffix;
    }

    @Test
    public void test1() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.println("id : " + Thread.currentThread().getId() + " test: 1 : " + i);
            Thread.sleep(1000);
        }
    }

    @Test(dependsOnMethods = "test1")
    public void test2() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.println("id : " + Thread.currentThread().getId() + " test: 2 : " + i);
            Thread.sleep(20);
        }
    }
}

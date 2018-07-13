package org.pantheon;

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
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(dependsOnMethods = "test1")
    public void test2() {
        System.out.println("Test 2");
    }
}

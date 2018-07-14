package org.pantheon.testng;

import org.testng.ITest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static java.lang.String.format;

public class SetTestMethodNameBase implements ITest {
    private String testCaseName = "";
    String suffix = "";

    @Override
    public String getTestName() {
        return this.testCaseName;
    }

    @BeforeMethod(alwaysRun = true)
    public void testData(Method method) {
        this.testCaseName = format("%s.%s.%s", this.getClass().getSimpleName(), this.suffix, method.getName());
    }
}

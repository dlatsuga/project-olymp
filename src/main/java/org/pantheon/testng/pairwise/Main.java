package org.pantheon.testng.pairwise;

import org.pantheon.testng.pairwise.domain.builder.OrderSingleBuilder;
import org.pantheon.testng.pairwise.domain.entity.Currency;
import org.pantheon.testng.pairwise.generator.TestDataGenerator;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.pantheon.testng.pairwise.domain.builder.OrderSingleBuilder.anOrderSingle;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Object[][] data = new TestDataGenerator()
                .forTemplate(getTemplate())
                .withEnumData("currency", Currency.class)
                .generateTestData();

        for (Object[] dt : data) {
            for (Object o : dt) {
                System.out.println(o);
            }
        }

    }

    private static OrderSingleBuilder getTemplate() {
        return anOrderSingle()
                .withQuantity(new BigDecimal("1000"))
                .withPrice(new BigDecimal("20"));
    }
}

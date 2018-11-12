package org.pantheon.tmp;

import java.util.List;
import java.util.Map;

public class TMP {
    private static final String NAV_SCENARIO =
            "Side: Buy, Sell"
                    + "\nCurrency: CHF, USD, EUR"
                    + "\nQuantity: 10, 100";

    public static void main(String[] args) {
        IInventory inventory = PairwiseInventoryFactory.generateParameterInventory(NAV_SCENARIO);
        List<Map<String, String>> rawDataSet = inventory.getTestDataSet().getTestSets();

        for (Map<String, String> stringStringMap : rawDataSet) {
            stringStringMap.forEach((k, v) -> System.out.println(k + " : " + v));
        }
    }
}

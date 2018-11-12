package org.pantheon.tmp.my;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.stream;

public class MyTMP {
    public static void main(String[] args) {

        SubSetDescription parameter_A = new SubSetDescription("parameter_A", ParameterA.class);
        SubSetDescription parameter_B = new SubSetDescription("parameter_B", ParameterB.class);
        SubSetDescription parameter_C = new SubSetDescription("parameter_C", ParameterC.class);

        SetDescription setDescription = new SetDescription();
        setDescription.addSubSet(parameter_A);
        setDescription.addSubSet(parameter_B);
        setDescription.addSubSet(parameter_C);

        setDescription.generateUniquePairs();
        Map<Integer, List<Integer>> allPairsIndexes = setDescription.getUniquePairsIndexes();

        allPairsIndexes.forEach((k, v) -> System.out.println(k + ":\t" + v));
        System.out.println("***********************************************************");

        Map<Integer, List<Object>> allPairsValues = setDescription.getUniquePairsValues();
        allPairsValues.forEach((k, v) -> System.out.println(k + ":\t" + v));

        setDescription.generatePairWise();
    }
}

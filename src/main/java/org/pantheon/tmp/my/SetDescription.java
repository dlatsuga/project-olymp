package org.pantheon.tmp.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SetDescription {
    private int subSetIndex = 0;
    private List<SubSetDescription> subSets = new ArrayList<>();
    private Map<Integer, Set<Integer>> elementsInSet = new HashMap<>();
    private Map<Integer, List<Integer>> uniquePairsIndexes = new HashMap<>();
    private Map<Integer, List<Object>> uniquePairsValues = new HashMap<>();
    private Map<Integer, List<Integer>> pairsWise = new HashMap<>();
    private Map<Integer, List<Object>> pairsWiseValues = new HashMap<>();

    private Map<Integer, Boolean> freePairs = new HashMap<>();
    private List<Integer> indexesOfFreeElementsInTuple;

    public void addSubSet(SubSetDescription subSetDescription) {

        subSetDescription.setSubSetIndex(subSetIndex);
        subSets.add(subSetDescription);

        Set<Integer> elementsIndexesInSubSet = subSetDescription.getElementsInSubSet().keySet();
        elementsInSet.put(subSetIndex, elementsIndexesInSubSet);

        subSetIndex++;
    }


    public void generateUniquePairs() {

        int indexOfPair = 0;

        for (int currentSubSetIndex = 0; currentSubSetIndex < elementsInSet.keySet().size() - 1; currentSubSetIndex++) {

            int currentIndex = currentSubSetIndex;

            // Подмножество для первого элемента пары
            Map<Integer, Set<Integer>> firstElementSet = elementsInSet.entrySet().stream()
                    .filter(allSubSets -> allSubSets.getKey().equals(currentIndex))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

            // Подмножество для второго элемента пары
            Map<Integer, Set<Integer>> secondElementSet = elementsInSet.entrySet().stream()
                    .filter(allSubSets -> !allSubSets.getKey().equals(currentIndex))
                    .filter(allSubSets -> allSubSets.getKey() > currentIndex)
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));


            for (Integer indexOfCurrentElement : firstElementSet.get(currentIndex)) {
                for (Map.Entry<Integer, Set<Integer>> pairSubSets : secondElementSet.entrySet()) {

                    int pairSubSetIndex = pairSubSets.getKey();

                    for (Integer indexOfPairElement : pairSubSets.getValue()) {
                        uniquePairsIndexes.put(indexOfPair++, asList(currentSubSetIndex, indexOfCurrentElement, pairSubSetIndex, indexOfPairElement));
                    }

                }
            }
        }
    }

    public Map<Integer, List<Integer>> getUniquePairsIndexes() {
        return uniquePairsIndexes;
    }

    public Map<Integer, List<Object>> getUniquePairsValues() {
        for (Map.Entry<Integer, List<Integer>> uniquePairIndexes : uniquePairsIndexes.entrySet()) {

            Integer indexOfFirstSubSet = uniquePairIndexes.getValue().get(0);
            Integer indexOfFirstElement = uniquePairIndexes.getValue().get(1);

            Integer indexOfSecondSubSet = uniquePairIndexes.getValue().get(2);
            Integer indexOfSecondElement = uniquePairIndexes.getValue().get(3);

            SubSetDescription firstSubSetDescription = subSets.get(indexOfFirstSubSet);
            SubSetDescription secondSubSetDescription = subSets.get(indexOfSecondSubSet);
            Object firstElement = firstSubSetDescription.getElementsInSubSet().get(indexOfFirstElement);
            Object secondElement = secondSubSetDescription.getElementsInSubSet().get(indexOfSecondElement);

            uniquePairsValues.put(uniquePairIndexes.getKey(), asList(firstElement, secondElement));
        }
        return uniquePairsValues;
    }

    public void generatePairWise() {


        for (Integer indexOfUniquePair : uniquePairsIndexes.keySet()) {
            freePairs.put(indexOfUniquePair, TRUE);
        }


        Integer indexOfPairWise = 0;
        for (Integer currentUniquePairsIndex : uniquePairsIndexes.keySet()) {

            indexesOfFreeElementsInTuple = IntStream.rangeClosed(0, subSetIndex - 1).boxed().collect(toList());
            Integer[] tuple = new Integer[subSetIndex];

            // Заполнили две ячейки кортежа
            if (freePairs.get(currentUniquePairsIndex)) {
                System.out.println("currentUniquePairsIndex : " + currentUniquePairsIndex);
                if(currentUniquePairsIndex.equals(2)){
                    System.out.println();
                }

                List<Integer> pairCandidateForTuple = uniquePairsIndexes.get(currentUniquePairsIndex);

                if(checkIfFreePairForCandidateAvailable(pairCandidateForTuple)){
                    for (int carriage = 0; carriage < pairCandidateForTuple.size() - 1; ) {
                        int tupleIndex = pairCandidateForTuple.get(carriage);
                        tuple[tupleIndex] = pairCandidateForTuple.get(carriage + 1);

                        indexesOfFreeElementsInTuple.remove(valueOf(tupleIndex));
                        carriage += 2;
                    }
                }

                // Указали, что данная пара уже использована. Ее надо скипать в следующих итерациях
                freePairs.put(currentUniquePairsIndex, FALSE);
            }


            boolean tupleIsEmpty = stream(tuple).allMatch(Objects::isNull);
            // Если кортеж не пустой - его надо дозаполнить...
            if (!tupleIsEmpty) {
                // пока есть незаполненые позиции в кортеже
                while (indexesOfFreeElementsInTuple.size() > 0) {
                    outer:
                    for (Map.Entry<Integer, List<Integer>> candidatePair : uniquePairsIndexes.entrySet()) {

                        Integer pairCandidateIndex = candidatePair.getKey();
                        System.out.println("pairCandidateIndex : " + pairCandidateIndex);

                        if (freePairs.get(pairCandidateIndex)) { // если пара свободна

                            /*
                            *
                            * Вот тут сука надо сплитовочку захуярить..
                            * проверять где стоит нужный элемент и матчится ли его брат кандидат с уже существующими в кортеже
                            * если все ровно то дозаполняем кортеж...
                            * схожая проверка уже есть в проверке
                            * checkIfFreePairForCandidateAvailable
                            *
                            * */

                            // индекс подмножества которое хотим запиндорить в пустую ячейку кортежа
                            Integer indexOfCandidateSubSetForTuple = candidatePair.getValue().get(2);

                            // проверяем есть ли этот ебаный индекс в ебаном массиве индексов которые осталось заполнить
                            if (indexesOfFreeElementsInTuple.contains(indexOfCandidateSubSetForTuple)) {

                                //теперь надо проверить что первый элемент уже есть в кортеже
                                // это индекс подмножества первого элемента в паре (он же индекс в кортеже с которым будем сравнивать)
                                Integer indexOfSubSetOfFirstElementInPair = candidatePair.getValue().get(0);

                                // А это сука сам элемент
                                Integer firstElementInPair = candidatePair.getValue().get(1);

                                if (tuple[indexOfSubSetOfFirstElementInPair].equals(firstElementInPair)) {
//                                if (tuple.get(indexOfSubSetOfFirstElementInPair).equals(firstElementInPair)) {

                                    // Это сука элемент мать его кандидат в кортеж
                                    Integer candidateElementForFreePlaceInTuple = candidatePair.getValue().get(3);
                                    tuple[indexOfCandidateSubSetForTuple] = candidateElementForFreePlaceInTuple;
//                                    tuple.add(indexOfCandidateSubSetForTuple, candidateElementForFreePlaceInTuple);

                                    if (checkDuplicatesInPairWiseMap(tuple)) {
                                        // Сказали, что индекс пары уже использован
                                        freePairs.put(pairCandidateIndex, FALSE);
                                        break outer;
                                    } else {
                                        //грохнули индекс который надо было заполнить
                                        indexesOfFreeElementsInTuple.remove(indexOfCandidateSubSetForTuple);

                                        // Сказали, что индекс пары уже использован
                                        freePairs.put(pairCandidateIndex, FALSE);
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }


                pairsWise.put(indexOfPairWise++, asList(tuple));
            }

        }
        System.out.println("THE END");

    }

    public Map<Integer, List<Object>> getPairsWiseValues() {

        for (Map.Entry<Integer, List<Integer>> pairWiseIndexes : pairsWise.entrySet()) {
            List<Object> values = new ArrayList<>();
            List<Integer> valuesIndexes = pairWiseIndexes.getValue();
            for (int i = 0; i < valuesIndexes.size(); i++) {
                Integer indexOfElement = valuesIndexes.get(i);
                values.add(subSets.get(i).getElementsInSubSet().get(indexOfElement));
            }
            pairsWiseValues.put(pairWiseIndexes.getKey(), values);
        }

        return pairsWiseValues;
    }

    private boolean checkIfFreePairForCandidateAvailable(List<Integer> pairCandidateForTuple) {

        List<Integer> requiredIndexesForCompleteTuple = IntStream.rangeClosed(0, subSetIndex - 1).boxed().collect(toList());
        requiredIndexesForCompleteTuple.remove(pairCandidateForTuple.get(0));
        requiredIndexesForCompleteTuple.remove(pairCandidateForTuple.get(2));

        List<Integer> freeIndexes = freePairs.entrySet().stream()
                .filter(addSubSet -> addSubSet.getValue().equals(TRUE))
                .map(Map.Entry::getKey)
                .collect(toList());

        outer:for (Integer requiredIndex : requiredIndexesForCompleteTuple) {
            for (Integer freeIndex : freeIndexes) {
                List<Integer> checkedFreePair = uniquePairsIndexes.get(freeIndex);
                // checkedFreePair.get(0).equals(requiredIndex) -- проверяем, что у нас есть свободный элемент который нужен для кортежа
                // плюс проверяем, что второй едемент в проверяемой паре соответствует элементу в паре претенденте

                Integer indexOfFirstSubSetInCheckedPair = checkedFreePair.get(0);
                Integer indexOfSecondSubSetInCheckedPair = checkedFreePair.get(2);
                Integer indexOfFirstElementInCheckedPair = checkedFreePair.get(1);
                Integer indexOfSecondElementInCheckedPair = checkedFreePair.get(3);

                Integer indexOfFirstSubSetInCandidatePair = pairCandidateForTuple.get(0);
                Integer indexOfSecondSubSetInCandidatePair = pairCandidateForTuple.get(2);
                Integer indexOfFirstElementInCandidatePair = pairCandidateForTuple.get(1);
                Integer indexOfSecondElementInCandidatePair = pairCandidateForTuple.get(3);

                if (checkedFreePair.get(0).equals(requiredIndex)
                        && (indexOfSecondSubSetInCheckedPair.equals(indexOfSecondSubSetInCandidatePair)
                        && indexOfSecondElementInCheckedPair.equals(indexOfSecondElementInCandidatePair))) {
                    requiredIndexesForCompleteTuple.remove(checkedFreePair.get(0));
                    break outer;
                } else if (checkedFreePair.get(2).equals(requiredIndex)
                        && (indexOfFirstSubSetInCheckedPair.equals(indexOfFirstSubSetInCandidatePair)
                        && indexOfFirstElementInCheckedPair.equals(indexOfFirstElementInCandidatePair))){
                    requiredIndexesForCompleteTuple.remove(checkedFreePair.get(2));
                    break outer;
                }
            }
        }
        return requiredIndexesForCompleteTuple.size() == 0;
    }

    private boolean checkDuplicatesInPairWiseMap(Integer[] tuple) {

        for (Map.Entry<Integer, List<Integer>> pairWiseTuples : pairsWise.entrySet()) {
            List<Integer> pairWiseValues = pairWiseTuples.getValue();
            for (int i = 0; i < pairWiseValues.size() - 1; i++) {
                boolean isFirstElementOfPairDuplicate = tuple[i].equals(pairWiseValues.get(i));
                boolean isSecondElementOfPairDuplicate = tuple[i + 1].equals(pairWiseValues.get(i + 1));
                if (isFirstElementOfPairDuplicate && isSecondElementOfPairDuplicate) {
                    return true;
                }
            }
        }

        return false;
    }

    public Map<Integer, List<Integer>> getPairsWise() {
        return pairsWise;
    }
}
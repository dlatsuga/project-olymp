package org.pantheon.tmp.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SetDescription {

    private static final Integer INDEX_OF_FIRST_SUB_SET_IN_PAIR = 0;
    private static final Integer INDEX_OF_FIRST_ELEMENT_IN_PAIR = 1;
    private static final Integer INDEX_OF_SECOND_SUB_SET_IN_PAIR = 2;
    private static final Integer INDEX_OF_SECOND_ELEMENT_IN_PAIR = 3;

    private int subSetIndex = 0;
    private List<SubSetDescription> subSets = new ArrayList<>();
    private Map<Integer, Set<Integer>> elementsInSet = new HashMap<>();
    private Map<Integer, List<Integer>> uniquePairsIndexes = new HashMap<>();
    private Map<Integer, List<Object>> uniquePairsValues = new HashMap<>();
    private Map<Integer, List<Integer>> pairsWise = new HashMap<>();
    private Map<Integer, List<Object>> pairsWiseValues = new HashMap<>();

    private Map<Integer, Boolean> freePairs = new HashMap<>();

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

    public void newGeneratePairWise() {

        initFreePairs();

        Integer indexOfPairWiseTuple = 0;

        Integer indexOfCurrentCandidateForPairWiseTuple = getFirstFreeIndex();
        // Если есть свободный индекс - начинаем собирать кортеж
        if (indexOfCurrentCandidateForPairWiseTuple >= 0) {

//            System.out.println("indexOfCurrentCandidateForPairWiseTuple : " + indexOfCurrentCandidateForPairWiseTuple);
//            if(indexOfCurrentCandidateForPairWiseTuple.equals(2)){
//                System.out.println();
//            }

            for (; indexOfCurrentCandidateForPairWiseTuple < freePairs.keySet().size() && indexOfCurrentCandidateForPairWiseTuple >= 0; ) {
                // Все индексы которые надо заполнить в кортеже
//                indexesOfRequiredElementsForCompleteTuple = IntStream.rangeClosed(0, subSetIndex - 1).boxed().collect(toList());
                Integer[] tuple = new Integer[subSetIndex];

                List<Integer> initialCandidatePairForTuple = uniquePairsIndexes.get(indexOfCurrentCandidateForPairWiseTuple);

                // Ищем свободные пары для кортежа
                Map<Integer, List<Integer>> freeMatchedPairsToCompleteTuple =
                        findFreeMatchedPairsToCompleteTuple(initialCandidatePairForTuple, indexOfCurrentCandidateForPairWiseTuple);

//                freePairs.put(indexOfCurrentCandidateForPairWiseTuple, FALSE);
//                System.out.println("init index FALSE : " + indexOfCurrentCandidateForPairWiseTuple);
//                Integer key = (Integer) freeMatchedPairsToCompleteTuple.keySet().toArray()[0];
//                freePairs.put(key, FALSE);
//                System.out.println("pair index FALSE : " + key);
//                indexOfCurrentCandidateForPairWiseTuple = getFirstFreeIndex();
//                System.out.println("new index : " + indexOfCurrentCandidateForPairWiseTuple);
//                System.out.println();


                if (freeMatchedPairsToCompleteTuple.keySet().size() > 0) {

                    Integer firstSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR);
                    Integer firstElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_ELEMENT_IN_PAIR);
                    Integer secondSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR);
                    Integer secondElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_ELEMENT_IN_PAIR);

//                    List<Integer> pairWiseTuple = new ArrayList<>(subSets.size());
                    Integer[] pairWiseTuple = new Integer[subSets.size()];
                    pairWiseTuple[firstSubSetCandidate] = firstElementCandidate;
                    pairWiseTuple[secondSubSetCandidate] = secondElementCandidate;

                    freePairs.put(indexOfCurrentCandidateForPairWiseTuple, FALSE);

                    for (Integer indexOfPairToCompleteTuple : freeMatchedPairsToCompleteTuple.keySet()) {
                        List<Integer> pairToCompleteTuple = freeMatchedPairsToCompleteTuple.get(indexOfPairToCompleteTuple);

                        Integer firstSubSetPotentialMatched = pairToCompleteTuple.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR);
                        Integer firstElementPotentialMatched = pairToCompleteTuple.get(INDEX_OF_FIRST_ELEMENT_IN_PAIR);
                        Integer secondSubSetPotentialMatched = pairToCompleteTuple.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR);
                        Integer secondElementPotentialMatched = pairToCompleteTuple.get(INDEX_OF_SECOND_ELEMENT_IN_PAIR);

                        pairWiseTuple[firstSubSetPotentialMatched] = firstElementPotentialMatched;
                        pairWiseTuple[secondSubSetPotentialMatched] = secondElementPotentialMatched;

                        freePairs.put(indexOfPairToCompleteTuple, FALSE);

                    }
                    pairsWise.put(indexOfPairWiseTuple++, asList(pairWiseTuple));
                }
                indexOfCurrentCandidateForPairWiseTuple = getFirstFreeIndex();
            }
        }
        System.out.println("THE END");
    }

    private void initFreePairs() {
        // Мапа которая хранит флаг свободен индекс или нет
        // Изначально все свободны
        for (Integer indexOfUniquePair : uniquePairsIndexes.keySet()) {
            freePairs.put(indexOfUniquePair, TRUE);
        }
    }

    private Integer getFirstFreeIndex() {
        for (Map.Entry<Integer, Boolean> candidateForFreeIndex : freePairs.entrySet()) {
            if (candidateForFreeIndex.getValue()) {
                return candidateForFreeIndex.getKey();
            }
        }
        return -1; // Если нет свободного индекса - вернет -1
    }

    private Map<Integer, List<Integer>> findFreeMatchedPairsToCompleteTuple(List<Integer> initialCandidatePairForTuple, Integer currentIndex) {

        Map<Integer, List<Integer>> freeMatchedPairsForCompleteTuple = new HashMap<>();

//        System.out.println("currentIndex : " + currentIndex);

        if (currentIndex.equals(7)) {
            System.out.println();
        }

        List<Integer> requiredIndexesToCompleteTuple = IntStream.rangeClosed(0, subSetIndex - 1).boxed().collect(toList());
        requiredIndexesToCompleteTuple.remove(initialCandidatePairForTuple.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR));
        requiredIndexesToCompleteTuple.remove(initialCandidatePairForTuple.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR));

        List<Integer> freeIndexesToCheck = freePairs.entrySet().stream()
                .filter(allSubSet -> !allSubSet.getKey().equals(currentIndex)) // Исключаем текущий индекс
                .filter(allSubSet -> allSubSet.getValue().equals(TRUE)) // Включаем только свободные индексы
                .map(Map.Entry::getKey)
                .collect(toList());

        Integer firstSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR);
        Integer firstElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_ELEMENT_IN_PAIR);
        Integer secondSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR);
        Integer secondElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_ELEMENT_IN_PAIR);

//        requiredIndex: for (Integer requiredIndex : requiredIndexesToCompleteTuple) {
        requiredIndex:
        while (!requiredIndexesToCompleteTuple.isEmpty()) {
            for (Integer freeIndex : freeIndexesToCheck) {


                List<Integer> potentialMatchedFreePair = uniquePairsIndexes.get(freeIndex);


                // Проверяем есть ли данная пара уже в мапе pairsWise
                if (!pairCandidateAlreadyExistInPairWiseMap(potentialMatchedFreePair)) {

                    Integer firstSubSetPotentialMatched = potentialMatchedFreePair.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR);
                    Integer firstElementPotentialMatched = potentialMatchedFreePair.get(INDEX_OF_FIRST_ELEMENT_IN_PAIR);
                    Integer secondSubSetPotentialMatched = potentialMatchedFreePair.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR);
                    Integer secondElementPotentialMatched = potentialMatchedFreePair.get(INDEX_OF_SECOND_ELEMENT_IN_PAIR);

                    // Проверяем есть ли индекс первого сабсета в Листе Требуемых индексов requiredIndexesToCompleteTuple для Кортежа
                    if (requiredIndexesToCompleteTuple.contains(firstSubSetPotentialMatched)) {

                        //Второй сабсет Matched равен ПЕРВОМУ сабсет Candidate ?
                        if (secondSubSetPotentialMatched.equals(firstSubSetCandidate)) {
                            //Второй элемент Matched равен первому элементу Candidate ?
                            if (secondElementPotentialMatched.equals(firstElementCandidate)) {

//                                // TODO : Протестировать это гавно
                                List<Integer> temporaryPairToCheck = asList(secondSubSetPotentialMatched, secondElementPotentialMatched, secondSubSetCandidate, secondElementCandidate);
                                if (!pairCandidateAlreadyExistInPairWiseMap(temporaryPairToCheck)) {
                                    // Да сука равен...
                                    // Добавляем его в нашу ебаную мапу Matched
                                    freeMatchedPairsForCompleteTuple.put(freeIndex, potentialMatchedFreePair);
                                    // Удаляем Требуемый индекс из requiredIndexesToCompleteTuple (Пара для индекса найдена)
                                    requiredIndexesToCompleteTuple.remove(firstSubSetPotentialMatched);
                                    // ??????????????????????????????????????
                                    // Надо ли блокировать индекс как занятый в freePairs -- Возможно в методе где конкретно заюзаем... пару
                                    break requiredIndex;
                                }
                            }
                            //Второй сабсет Matched равен ВТОРОМУ сабсет Candidate ?
                        } else if (secondSubSetPotentialMatched.equals(secondSubSetCandidate)) {
                            if (secondElementPotentialMatched.equals(secondElementCandidate)) {

                                // TODO : Протестировать это гавно
                                List<Integer> temporaryPairToCheck = asList(firstSubSetCandidate, firstElementCandidate, firstSubSetPotentialMatched, firstElementPotentialMatched);
                                if (!pairCandidateAlreadyExistInPairWiseMap(temporaryPairToCheck)) {

                                    freeMatchedPairsForCompleteTuple.put(freeIndex, potentialMatchedFreePair);
                                    requiredIndexesToCompleteTuple.remove(firstSubSetPotentialMatched);
                                    break requiredIndex;
                                }
                            }
                        }
                    }
                    // Проверяем есть ли индекс Второго сабсета в Листе Требуемых индексов requiredIndexesToCompleteTuple для Кортежа
                    else if (requiredIndexesToCompleteTuple.contains(secondSubSetPotentialMatched)) {
                        if (firstSubSetPotentialMatched.equals(firstSubSetCandidate)) {
                            if (firstElementPotentialMatched.equals(firstElementCandidate)) {

                                // TODO : Протестировать это гавно ++
                                List<Integer> temporaryPairToCheck = asList(secondSubSetCandidate, secondElementCandidate, secondSubSetPotentialMatched, secondElementPotentialMatched);
                                if (!pairCandidateAlreadyExistInPairWiseMap(temporaryPairToCheck)) {

                                    freeMatchedPairsForCompleteTuple.put(freeIndex, potentialMatchedFreePair);
                                    requiredIndexesToCompleteTuple.remove(secondSubSetPotentialMatched);
                                    break requiredIndex;
                                }
                            }
                        } else if (firstSubSetPotentialMatched.equals(secondSubSetCandidate)) {
                            if (firstElementPotentialMatched.equals(secondElementCandidate)) {

                                // TODO : Протестировать это гавно
                                List<Integer> temporaryPairToCheck = asList(secondSubSetCandidate, secondElementCandidate, secondSubSetPotentialMatched, secondElementPotentialMatched);
                                if (!pairCandidateAlreadyExistInPairWiseMap(temporaryPairToCheck)) {
                                    freeMatchedPairsForCompleteTuple.put(freeIndex, potentialMatchedFreePair);
                                    requiredIndexesToCompleteTuple.remove(secondSubSetPotentialMatched);
                                    break requiredIndex;
                                }
                            }
                        }
                    }
                }


            }

            // TODO : Проверить правильная ли тут логика и куда надо кидать если свободные пары закончились, а кортеж не собран
            break requiredIndex; // Если сука закончились свободные индексы
        }


        // Если не все нужные для Кортежа индексы найдены
        if (!requiredIndexesToCompleteTuple.isEmpty()) {
            if (pairCandidateAlreadyExistInPairWiseMap(initialCandidatePairForTuple)) {
                freePairs.put(currentIndex, FALSE); // Скипаем initialCandidatePairForTuple, уже покрыта в мапе Кортежей, для нее не надо искать пары
            } else {
                // дохуяриваем случайными значениями Из конкретного Параметра (СабСета) чтоюбы дозаполнить мапу freeMatchedPairsForCompleteTuple
                // TODO : черячим пары с фиктивным индексом -- Например (-2)
                final Integer INDEX_FOR_ARTIFICIAL_PAIR = -2;
                for (Integer requiredIndex : requiredIndexesToCompleteTuple) {

                    Integer RANDOM_ARTIFICIAL_ELEMENT = subSets.get(requiredIndex)
                            .getElementsInSubSet().keySet()
                            .stream()
                            .findAny()
                            .get();

                    // TODO : проверить можно ли пихать на первое место первый Элемент из InitialCandidate
                    List<Integer> ARTIFICIAL_PAIR = asList(firstSubSetCandidate, firstElementCandidate, requiredIndex, RANDOM_ARTIFICIAL_ELEMENT);
                    freeMatchedPairsForCompleteTuple.put(INDEX_FOR_ARTIFICIAL_PAIR, ARTIFICIAL_PAIR);
                }
            }
        }

        return freeMatchedPairsForCompleteTuple;
    }

    private boolean pairCandidateAlreadyExistInPairWiseMap(List<Integer> initialCandidatePairForTuple) {

        Integer firstSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_SUB_SET_IN_PAIR);
        Integer firstElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_FIRST_ELEMENT_IN_PAIR);
        Integer secondSubSetCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_SUB_SET_IN_PAIR);
        Integer secondElementCandidate = initialCandidatePairForTuple.get(INDEX_OF_SECOND_ELEMENT_IN_PAIR);

        for (Map.Entry<Integer, List<Integer>> pairWiseTuple : pairsWise.entrySet()) {

            List<Integer> pairWiseTupleValues = pairWiseTuple.getValue();

            Integer firstElementPairWiseToCompare = pairWiseTupleValues.get(firstSubSetCandidate);
            Integer secondElementPairWiseToCompare = pairWiseTupleValues.get(secondSubSetCandidate);

            boolean isFirstElementOfPairDuplicate = firstElementCandidate.equals(firstElementPairWiseToCompare);
            boolean isSecondElementOfPairDuplicate = secondElementCandidate.equals(secondElementPairWiseToCompare);

            if (isFirstElementOfPairDuplicate && isSecondElementOfPairDuplicate) {
                return true;
            }

        }

        return false;
    }


    // TODO: решить какой вариант лучше getPairWiseValues VS getPairsWiseValues
    public Map<Integer, List<Object>> getPairWiseValues() {
        for (Map.Entry<Integer, List<Integer>> tuple : pairsWise.entrySet()) {

            Object[] values = new Object[subSets.size()];
            List<Integer> indexes = tuple.getValue();

            for (int i = 0; i < indexes.size(); i++) {
                SubSetDescription subSetDescription = subSets.get(i);
                Object element = subSetDescription.getElementsInSubSet().get(indexes.get(i));
                values[i] = element;
            }
            pairsWiseValues.put(tuple.getKey(), asList(values));
        }
        return pairsWiseValues;
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
}
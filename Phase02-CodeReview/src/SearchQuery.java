import java.util.ArrayList;

public class SearchQuery {
    public static ArrayList<Node> doQuery(String query, ProcessOnResources por) {
        ArrayList<Node> normalDocs = listOfDocsWithWords(WorksOnStrings.findWordWithPattern(" " + WorksOnStrings.normalizeTheContent(query), WorksOnStrings.NORMAL_WORDS_PATTERN), por, true);
        ArrayList<Node> minusDocs = listOfDocsWithWords(WorksOnStrings.findWordWithPattern(query, WorksOnStrings.MINUS_WORDS_PATTERN), por, false);
        ArrayList<Node> plusDocs = listOfDocsWithWords(WorksOnStrings.findWordWithPattern(query, WorksOnStrings.PLUS_WORDS_PATTERN), por, false);

        normalDocs = listIsNull(normalDocs);
        return createResultDocsOfQuery(plusDocs, minusDocs, normalDocs);
    }

    public static ArrayList<Node> createResultDocsOfQuery(ArrayList<Node> plusDocs, ArrayList<Node> minusDocs, ArrayList<Node> normalDocs) {
        ArrayList<Node> result = new ArrayList<>();
        result.addAll(normalDocs);
        result.addAll(plusDocs);
        result.removeAll(minusDocs);
        return result;
    }

    public static ArrayList<Node> listIsNull(ArrayList<Node> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public static ArrayList<Node> listOfDocsWithWords(ArrayList<String> listOfWords, ProcessOnResources por, boolean isNormalWords) {
        ArrayList<Node> returnValue;
        ArrayList<Node> listOfGoalDocs = new ArrayList<>();

        boolean firstIteration = true;
        for (String word : listOfWords) {
            returnValue = por.searchWordInMap(word);
            if (firstIteration) {
                listOfGoalDocs = por.searchWordInMap(word);
                firstIteration = false;
            } else {
                operationBetweenTwoLists(isNormalWords, listOfGoalDocs, returnValue);
            }
        }

        return listOfGoalDocs;
    }

    public static void operationBetweenTwoLists(boolean isAdd, ArrayList<Node> firstList, ArrayList<Node> secondList) {
        if (isAdd) {
            firstList.retainAll(secondList);
        } else {
            firstList.addAll(secondList);
        }

    }
}

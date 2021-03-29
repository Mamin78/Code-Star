import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvertedIndex {
    public static final ArrayList<String> listOfUnusableWords = new ArrayList<>(Arrays.asList("i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"));
    public static HashMap<String, ArrayList<Node>> invertedIndexMap = new HashMap<String, ArrayList<Node>>();

    public static void main(String[] args) {
//        create inverted index from all documents in "SampleEnglishDocuments" folder
        readDocsAndCreateInvertedIndexMap();
//        read search query
        Scanner inp = new Scanner(System.in);
        String query = inp.nextLine();

        query = query.toLowerCase();

        printResult(doQuery(query));
    }

    public static void printResult(ArrayList<Node> resultDocs) {
        System.out.println("Result Documents: ");
        System.out.println();
        printArrayList(resultDocs);
    }

    public static void printArrayList(ArrayList<Node> resultDocs) {
        if (resultDocs != null) {
            for (Node node : resultDocs) {
                System.out.println(node.getDocumentId());
            }
        }
    }

    public static ArrayList<Node> createListOfDocsWithGoalWords(ArrayList<String> words) {
        ArrayList<Node> listOfDocsWithGoalWords = new ArrayList<Node>();
        ArrayList<Node> tempArr = new ArrayList<Node>();

        for (String word : words) {
            tempArr = searchWordInMap(word);
            if (tempArr != null) {
                listOfDocsWithGoalWords.addAll(tempArr);
            }
        }

        return listOfDocsWithGoalWords;
    }

    public static ArrayList<Node> createListOfDocsWithGoalNormalWords(ArrayList<String> listOfNormalWords) {
        ArrayList<Node> tempArr = new ArrayList<Node>();
        ArrayList<Node> normalDocs = new ArrayList<Node>();

        boolean firstIteration = true;
        for (String word : listOfNormalWords) {
            tempArr = searchWordInMap(word);
            if (firstIteration) {
                if (tempArr != null) {
                    normalDocs = searchWordInMap(word);
                    firstIteration = false;
                }
            } else {
                if (tempArr != null) {
                    normalDocs.retainAll(tempArr);
                } else {
                    normalDocs = new ArrayList<Node>();
                }
            }
        }

        return normalDocs;
    }


    public static ArrayList<Node> doQuery(String query) {
        ArrayList<Node> normalDocs = new ArrayList<Node>();
        ArrayList<Node> plusDocs = new ArrayList<Node>();
        ArrayList<Node> minusDocs = new ArrayList<Node>();

        ArrayList<String> listOfPlusWords = wordsWithPlus(query);
        ArrayList<String> listOfMinusWords = wordsWithMinus(query);
        ArrayList<String> listOfNormalWords = otherWords(query);

        normalDocs = createListOfDocsWithGoalNormalWords(listOfNormalWords);
        minusDocs = createListOfDocsWithGoalWords(listOfMinusWords);
        plusDocs = createListOfDocsWithGoalWords(listOfPlusWords);

        if (normalDocs == null) {
            normalDocs = new ArrayList<Node>();
        }

        normalDocs.addAll(plusDocs);
        normalDocs.removeAll(minusDocs);

        return normalDocs;
    }

    public static ArrayList<Node> searchWordInMap(String word) {
        if (invertedIndexMap.containsKey(word)) {
            return invertedIndexMap.get(word);
        }
        return null;
    }

    public static ArrayList<String> wordsWithPlus(String query) {
        ArrayList<String> listOfPlusWords = new ArrayList<>();
        Pattern plusWordsPatter = Pattern.compile("\\+(\\w+)");
        Matcher matcher = plusWordsPatter.matcher(query);
        while (matcher.find()) {
            listOfPlusWords.add(matcher.group(1));
        }

        return listOfPlusWords;
    }

    public static ArrayList<String> wordsWithMinus(String query) {
        ArrayList<String> listOfMinusWords = new ArrayList<>();
        Pattern minusWordsPattern = Pattern.compile("-(\\w+)");
        Matcher matcher = minusWordsPattern.matcher(query);
        while (matcher.find()) {
            listOfMinusWords.add(matcher.group(1));
        }

        return listOfMinusWords;
    }

    public static ArrayList<String> otherWords(String query) {
        String normalQuery = normalizeTheContent(query);
        ArrayList<String> listOfNormalWords = new ArrayList<>();
        Pattern normalWordsPattern = Pattern.compile("\\s(\\w+)");
        Matcher matcher = normalWordsPattern.matcher(" " + deleteRedundantChars(normalQuery));
        while (matcher.find()) {
            listOfNormalWords.add(matcher.group(1));
        }

        return listOfNormalWords;
    }

    public static String normalizeTheContent(String content) {
        String normalContent = content.toLowerCase().trim();
        normalContent = deleteRedundantChars(normalContent);
        return normalContent;
    }

    public static String deleteRedundantChars(String content) {
        content = content.replace(";", "");
        content = content.replace("'", "");
        content = content.replace("\"", "");
        content = content.replace("'s", "");
        content = content.replace("'", "");
        content = content.replace(":", "");
        content = content.replace("?", "");
        content = content.replace("/", "");
        content = content.replace("\\", "");
        content = content.replace(",", "");
        content = content.replace(".", "");
        content = content.replace("!", "");
        content = content.replace("#", "");
        content = content.replace("&", "");
        content = content.replace("(", "");
        content = content.replace(")", "");

        return content;
    }

    public static void readDocsAndCreateInvertedIndexMap() {
        File dir = new File("SampleEnglishData");
        File[] listOfDocuments = dir.listFiles();
        assert listOfDocuments != null;
        findWordsOfDoc(listOfDocuments);
    }

    public static void findWordsOfDoc(File[] listOfDocuments) {
        int indexOfWord;
        for (File file : listOfDocuments) {
            String documentId = file.getName();
            try {
                Scanner currentDocument = new Scanner(file);
                indexOfWord = 0;
                while (currentDocument.hasNext()) {
                    String currentWord = currentDocument.next();
                    addWord(documentId, currentWord, indexOfWord);
                    indexOfWord++;
                }
            } catch (FileNotFoundException exception) {

            }
        }
    }

    public static void addWord(String documentId, String word, int indexOfWord) {
        word = normalizeTheContent(word);
//        If current word is not in list of words which must omit.
        if (!listOfUnusableWords.contains(word)) {
            if (invertedIndexMap.containsKey(word)) {
//            Word is in the map.
                invertedIndexMap.get(word).add(new Node(documentId, indexOfWord));
            } else {
//            Word isn't in the map.
                ArrayList<Node> temp = new ArrayList<>();
                temp.add(new Node(documentId, indexOfWord));
                invertedIndexMap.put(word, temp);
            }
        }
    }
}
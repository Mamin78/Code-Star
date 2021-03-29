import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProcessOnResources por = new ProcessOnResources(new HashMap<String, ArrayList<Node>>(), "SampleEnglishData");
        por.findWordsOfDoc();

        Scanner inp = new Scanner(System.in);
        String query = inp.nextLine();
        query = query.toLowerCase();
        printResult(SearchQuery.doQuery(query, por));
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
}

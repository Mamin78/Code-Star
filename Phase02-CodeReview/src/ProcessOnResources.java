import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessOnResources {
    //    public HashMap<String, ArrayList<Node>> invertedIndexMap = new HashMap<>();
    private HashMap<String, ArrayList<Node>> invertedIndexMap;
    private String directory;

    public String getDirectory() {
        return directory;
    }

    public HashMap<String, ArrayList<Node>> getInvertedIndexMap() {
        return invertedIndexMap;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setInvertedIndexMap(HashMap<String, ArrayList<Node>> invertedIndexMap) {
        this.invertedIndexMap = invertedIndexMap;
    }

    public ProcessOnResources(HashMap<String, ArrayList<Node>> invertedIndexMap, String directory) {
        this.invertedIndexMap = invertedIndexMap;
        this.directory = directory;
    }

    public File[] listOfDocs() {
        File dir = new File(this.getDirectory());
        return dir.listFiles();
    }

    public void findWordsOfDoc() {
        File[] listOfDocuments = this.listOfDocs();
        for (File file : listOfDocuments) {
            readWordAndAddIntoMap(file);
        }
    }

    public void readWordAndAddIntoMap(File file) {
        int indexOfWord = 0;
        try {
            Scanner currentDocument = new Scanner(file);
            while (currentDocument.hasNext()) {
                addWord(file.getName(), currentDocument.next(), indexOfWord);
                indexOfWord++;
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void addWord(String documentId, String word, int indexOfWord) {
        word = WorksOnStrings.normalizeTheContent(word);
        if (!RedundantWords.isRedundantWord(word)) {
            if (this.getInvertedIndexMap().containsKey(word)) {
                this.searchWordInMap(word).add(new Node(documentId, indexOfWord));
            } else {
                ArrayList<Node> temp = new ArrayList<>();
                temp.add(new Node(documentId, indexOfWord));
                this.getInvertedIndexMap().put(word, temp);
            }
        }
    }

    public ArrayList<Node> searchWordInMap(String word) {
        if (this.getInvertedIndexMap().containsKey(word)) {
            return this.getInvertedIndexMap().get(word);
        }
        return null;
    }
}

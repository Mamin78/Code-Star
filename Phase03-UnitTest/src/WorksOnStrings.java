import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorksOnStrings {
    public static final String PLUS_WORDS_PATTERN = "\\+(\\w+)";
    public static final String MINUS_WORDS_PATTERN = "-(\\w+)";
    public static final String NORMAL_WORDS_PATTERN = "\\s(\\w+)";

    public static ArrayList<String> findWordWithPattern(String query, String pattern) {
        ArrayList<String> listOfWords = new ArrayList<>();
        Pattern WordsPattern = Pattern.compile(pattern);
        Matcher matcher = WordsPattern.matcher(query);
        while (matcher.find()) {
            listOfWords.add(matcher.group(1));
        }

        return listOfWords;
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
}

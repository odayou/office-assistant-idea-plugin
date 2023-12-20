package com.tomy.ideaplugin.helper;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author tomy.li
 * @date : 2023/12/20 11:17
 */
public class TextHelper {
    // 将文本中的单词拼接成下划线形式
    public static String concatenateWordsWithUnderline(String selectedText) {
        return concatenateWordsWithDelimiter(selectedText, "_");
    }

    // 将文本中的单词拼接成点号形式
    public static String concatenateWordsWithDot(String selectedText) {
        return concatenateWordsWithDelimiter(selectedText, ".");
    }

    // 将文本中的单词拼接成指定分隔符的形式
    public static String concatenateWordsWithDelimiter(String selectedText, String delimiter) {
        String[] words = selectedText.split("(?=[A-Z])|(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
        return String.join(delimiter, words).toLowerCase();
    }

    // 移除文本中的分隔符，组合成单词
    public static String joinWordsFromDelimiter(String input, String delimiter) {
        String regexDelimiter = Pattern.quote(delimiter);

        String[] words = input.split(regexDelimiter);
        String camelCaseString = Arrays.stream(words)
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining());

        return camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1);
    }

//    public static void main(String[] args) {
//        System.out.println(joinWordsFromDelimiter("search_msg-test_template.hello", "_"));
//        System.out.println(joinWordsFromDelimiter(joinWordsFromDelimiter(joinWordsFromDelimiter("search_msg-test_template.hello", "_"), "-"), "."));
//    }
}

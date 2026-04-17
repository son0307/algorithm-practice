package programmers.lv1.pccpTest121683;

import java.util.HashSet;

public class pccpTest121683 {
    public String solution(String input_string) {
        StringBuilder sb = new StringBuilder();
        char recentChar = input_string.charAt(0);
        HashSet<Character> appearedChars = new HashSet<>();
        HashSet<Character> aloneAlphabet = new HashSet<>();

        appearedChars.add(recentChar);
        for (int i = 1; i < input_string.length(); i++) {
            char c = input_string.charAt(i);

            if (recentChar == c) continue;

            if (appearedChars.contains(c)) {
                aloneAlphabet.add(c);
            } else {
                appearedChars.add(c);
            }

            recentChar = c;
        }

        if (aloneAlphabet.isEmpty()) return "N";

        aloneAlphabet.stream().sorted().forEach(sb::append);
        return sb.toString();
    }

    public static void main(String[] args) {
        String input_string = "zbzbz";
        System.out.println(new pccpTest121683().solution(input_string));
    }
}

package programmers.lv2.p84512;

import java.util.ArrayList;
import java.util.List;

public class p84512 {
    private static char[] chars = {'A', 'E', 'I', 'O', 'U'};

    private List<String> makeDict(String word) {
        List<String> dict = new ArrayList<>();
        dict.add(word);

        if (word.length() == 5) {
            return dict;
        }

        for (char c : chars) {
            dict.addAll(makeDict(word + c));
        }

        return dict;
    }

    public int solution(String word) {
        List<String> dict = new ArrayList<>();
        for(char c : chars) {
            dict.addAll(makeDict(Character.toString(c)));
        }
        return dict.indexOf(word) + 1;
    }

    public static void main(String[] args) {
        p84512 p = new p84512();
        System.out.println(p.solution("EIO"));
    }
}

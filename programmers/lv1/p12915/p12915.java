package programmers.lv1.p12915;

import java.util.Arrays;

public class p12915 {
    public String[] solution(String[] strings, int n) {
        String[] answer = {};

        Arrays.sort(strings, (o1, o2) -> {
            if (o1.charAt(n) == o2.charAt(n)) {
                return o1.compareTo(o2);
            } else {
                return o1.charAt(n) - o2.charAt(n);
            }
        });

        answer = strings;

        return answer;
    }

    public static void main(String[] args) {
        p12915 p = new p12915();
        System.out.println(Arrays.toString(p.solution(new String[]{"abce", "abcd", "cdx"}, 2)));
    }
}

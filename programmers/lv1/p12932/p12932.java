package programmers.lv1.p12932;

import java.util.Arrays;

public class p12932 {
    public int[] solution(long n) {
        String stringN = Long.toString(n);
        String str = new StringBuilder(stringN).reverse().toString();
        char[] chars = str.toCharArray();

        int[] answer = new int[str.length()];
        for (int i=0; i<str.length(); i++) {
            answer[i] = str.charAt(i) - '0';
        }
        return answer;
    }

    public static void main(String[] args) {
        p12932 p = new p12932();
        System.out.println("p.solution(12345) = " + Arrays.toString(p.solution(12345)));
    }
}

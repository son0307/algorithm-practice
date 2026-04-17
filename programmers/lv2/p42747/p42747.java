package programmers.lv2.p42747;

import java.util.Arrays;

public class p42747 {
    public int solution(int[] citations) {
        int answer = 0;

        Arrays.sort(citations);
        int i;
        for (i = citations.length; i > 0; i--) {
            if (citations[citations.length - i] >= i)
                break;
        }

        return i;
    }

    public static void main(String[] args) {
        p42747 p = new p42747();
        System.out.println(p.solution(new int[]{5,6,7,8}));
    }
}

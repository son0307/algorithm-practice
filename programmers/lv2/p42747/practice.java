package programmers.lv2.p42747;

import java.util.Arrays;

public class practice {
    public int solution(int[] citations) {
        int answer = 0;

        Arrays.sort(citations);
        for(int i = citations.length - 1; i >= 0; i--) {
            if(citations[i] >= answer + 1)
                answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        practice p = new practice();
        int[] citations = new int[]{5,5,5,5};

        System.out.println(p.solution(citations));
    }
}

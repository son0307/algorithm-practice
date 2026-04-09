package programmers.lv2;

import java.util.Arrays;

public class pccpTest121685 {
    public String getGene(int n, int p) {
        if (n == 1) return "Rr";

        int slice = (int) Math.pow(4, n - 2);
        int group = p / slice;

        if (group == 0) return "RR";
        if (group == 3) return "rr";

        return getGene(n - 1, p % slice);
    }

    public String[] solution(int[][] queries) {
        String[] answer = new String[queries.length];

        for (int i = 0; i < queries.length; i++) {
            answer[i] = getGene(queries[i][0], queries[i][1] - 1);
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] queries = {{5,198}};
        System.out.println(Arrays.toString(new pccpTest121685().solution(queries)));
    }
}

package programmers.lv2.p12946;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class p12946 {
    private int[][] hanoi(int n, int from, int to, int blank) {
        if (n == 2) {
            return new int[][] {{from, blank}, {from, to}, {blank , to}};
        }

        int[][] ans1 = hanoi(n-1, from, blank, to);
        int[][] ans2 = {{from, to}};
        int[][] ans3 = hanoi(n-1, blank, to, from);

        List<int[]> ans = new ArrayList<>(Arrays.asList(ans1));
        ans.addAll(Arrays.asList(ans2));
        ans.addAll(Arrays.asList(ans3));

        return ans.toArray(new int[ans.size()][]);
    }

    public int[][] solution(int n) {
        int[][] answer = hanoi(n, 1, 3, 2);
        return answer;
    }

    public static void main(String[] args) {
        p12946 p = new p12946();
        int[][] ans1 = p.solution(4);
        System.out.println(Arrays.toString(ans1));
    }
}

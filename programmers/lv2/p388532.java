package programmers.lv2;

import java.util.ArrayList;
import java.util.List;

public class p388532 {
    int answer = 0;

    void combination(int start, int n, int count, List<Integer> current, int[][] q, int[] ans) {
        if (count == 5) {
            if (check(current, q, ans)) answer++;
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            combination(i + 1, n, count + 1, current, q, ans);
            current.remove(current.size() - 1);
        }
    }

    boolean check(List<Integer> current, int[][] q, int[] ans) {
        for (int i = 0; i < q.length; i++) {
            int matchCount = 0;
            for (int j : q[i]) {
                if (current.contains(j)) matchCount++;
            }
            if (matchCount != ans[i]) return false;
        }
        return true;
    }

    public int solution(int n, int[][] q, int[] ans) {
        combination(1, n, 0, new ArrayList<>(), q, ans);
        return answer;
    }

    public static void main(String[] args) {
        int n = 10;
        int[][] q = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {3, 7, 8, 9, 10}, {2, 5, 7, 9, 10}, {3, 4, 5, 6, 7}};
        int[] ans = {2,3,4,3,3};
        p388532 p = new p388532();
        System.out.println(p.solution(n, q, ans));
    }
}

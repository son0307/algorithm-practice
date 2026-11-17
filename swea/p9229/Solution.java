package swea.p9229;

import java.util.*;

public class Solution {
    static int maxWeight;

    static void dfs(int[] weights, int idx, int selected, int limit, int sum) {
        if(selected == 2) {
            if (sum > limit) return;

            maxWeight = Math.max(sum, maxWeight);
            return;
        }

        for(int i = idx; i < weights.length; i++) {
            dfs(weights, i + 1, selected + 1, limit, sum + weights[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            maxWeight = -1;

            int N = sc.nextInt();
            int M = sc.nextInt();
            int[] weights = new int[N];
            for(int i = 0; i < N; i++) {
                weights[i] = sc.nextInt();
            }

            dfs(weights, 0, 0, M, 0);

            System.out.println("#" + tc + " " + maxWeight);
        }
    }
}

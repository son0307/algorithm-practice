package swea.p2817;

import java.util.Scanner;

public class Solution {
    static int N, K;
    static int[] arr;

    static int dfs(int idx, int cnt, int sum, int goal) {
        if(cnt == goal) {
            if(sum == K) return 1;
            else return 0;
        }

        int ans = 0;
        for(int i = idx; i <= arr.length - goal + cnt; i++) {
            ans += dfs(i + 1, cnt + 1, sum + arr[i], goal);
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            K = sc.nextInt();

            arr = new int[N];
            for(int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }

            int ans = 0;
            for(int i = 1; i <= N; i++) {
                ans += dfs(0, 0, 0, i);
            }
            System.out.println("#" + tc + " " + ans);
        }
    }
}

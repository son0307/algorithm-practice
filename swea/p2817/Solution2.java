package swea.p2817;

import java.util.Scanner;

public class Solution2 {
    static int N, K;
    static int[] arr;

    static int dfs(int idx, int sum) {
        if (idx == N) {
            return sum == K ? 1 : 0;
        }

        int select = dfs(idx + 1, sum + arr[idx]);
        int skip = dfs(idx + 1, sum);

        return select + skip;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            K = sc.nextInt();

            arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }

            System.out.println("#" + tc + " " + dfs(0, 0));
        }
    }
}

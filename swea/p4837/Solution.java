package swea.p4837;

import java.util.*;

public class Solution {
    static int N, K;

    static int dfs(int idx, int cnt, int sum) {
        if(cnt == N) {
            if(sum == K) return 1;
            else return 0;
        }

        int ans = 0;
        for(int i = idx; i <= 13 - N + cnt; i++) {
            ans += dfs(i + 1, cnt + 1, sum + i);
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            K = sc.nextInt();

            System.out.println("#" + tc + " " + dfs(1, 0, 0));
        }
    }
}

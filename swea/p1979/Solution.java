package swea.p1979;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for(int tc = 1; tc <= T; tc++) {
            int n = sc.nextInt();
            int k = sc.nextInt();

            int[][] map = new int[n+1][n+1];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    map[i][j] = sc.nextInt();
                }
            }

            int ans = 0;

            for(int i = 0; i < n; i++) {
                int hor = 0;
                int ver = 0;

                for(int j = 0; j <= n; j++) {
                    if(map[i][j] == 1) hor++;
                    else {
                        if(hor == k) ans++;
                        hor = 0;
                    }

                    if(map[j][i] == 1) ver++;
                    else {
                        if(ver == k) ans++;
                        ver = 0;
                    }
                }
            }

            System.out.println("#" + tc + " " + ans);
        }
    }
}

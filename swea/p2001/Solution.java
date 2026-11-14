package swea.p2001;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for(int tc = 1; tc <= n; tc++) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int[][] arr = new int[N][N];
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    arr[i][j] = sc.nextInt();
                }
            }

            int max = 0;
            for(int i = 0; i < N - M + 1; i++) {
                for(int j = 0; j < N - M + 1; j++) {
                    int cur = 0;
                    for(int y = i; y < i + M; y++) {
                        for(int x = j; x < j + M; x++) {
                            cur += arr[y][x];
                        }
                    }

                    max = Math.max(max, cur);
                }
            }

            System.out.println("#" + tc + " " + max);
        }
    }
}

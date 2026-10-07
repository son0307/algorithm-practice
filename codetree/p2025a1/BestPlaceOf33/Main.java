package codetree.p2025a1.BestPlaceOf33;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int max = 0;
        for(int i = 0; i < n-2; i++) {
            for(int j = 0; j < n-2; j++) {
                int cnt = 0;
                for(int r = i; r < i + 3; r++) {
                    for(int c = j; c < j + 3; c++) {
                        cnt += grid[r][c];
                    }
                }
                max = Math.max(max, cnt);
            }
        }

        System.out.println(max);
    }
}

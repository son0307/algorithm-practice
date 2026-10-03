package codetree.p2024m1.BestPlaceOf33;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int max = 0;
        for(int i = 0; i < n - 2; i++) {
            for(int j = 0; j < n - 2; j++) {
                int cnt = 0;
                for(int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (grid[i + k][j + l] == 1) cnt++;
                    }
                }
                max = Math.max(max, cnt);
            }
        }

        System.out.println(max);
    }
}
package codetree.p2024m1.MaxAreaOfPositiveRectangle;

import java.util.Scanner;

public class Main {
    public static int n, m;
    public static int[][] grid = new int[20][20];

    private static boolean isAvailable(int x1, int y1, int x2, int y2) {
        for(int i = y1; i <= y2; i++) {
            for(int j = x1; j <= x2; j++) {
                if(grid[i][j] <= 0) return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                grid[i][j] = sc.nextInt();

        int ans = -1;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                for(int k = i; k < n; k++) {
                    for(int l = j; l < m; l++) {
                        if(isAvailable(j, i, l, k))
                            ans = Math.max((k - i + 1) * (l - j + 1), ans);
                    }
                }
            }
        }

        System.out.println(ans);
    }
}
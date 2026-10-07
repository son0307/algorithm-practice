package codetree.p2025_2m1.FallingHorizontalBlock;

import java.util.*;

public class Main {
    static int MAX_N = 100;

    static int n, m, k;
    static int[][] map = new int[MAX_N][MAX_N];

    static boolean allBlank(int row, int startCol, int endCol) {
        for(int col = startCol; col <= endCol; col++) {
            if(map[row][col] == 1)
                return false;
        }

        return true;
    }

    static int findRow() {
        for(int row = 0; row < n - 1; row++) {
            if(!allBlank(row + 1, k, k + m - 1))
                return row;
        }

        return n - 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt() - 1;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        int targetRow = findRow();
        for(int col = k; col < k + m; col++)
            map[targetRow][col] = 1;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}

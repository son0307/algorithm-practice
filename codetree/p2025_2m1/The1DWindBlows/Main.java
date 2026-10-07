package codetree.p2025_2m1.The1DWindBlows;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static int MAX_N = 100;
    static int n, m, q;

    static int[][] grid = new int[MAX_N][MAX_N];

    // 바람 처리(0: R, 1: L)
    static void windBlow(int row, int direction) {
        int temp;

        switch (direction) {
            case 0:
                temp = grid[row][0];
                for(int i = 0; i < m-1; i++)
                    grid[row][i] = grid[row][i+1];
                grid[row][m-1] = temp;
                break;
            case 1:
                temp = grid[row][m-1];
                for(int i = m-1; i > 0; i--)
                    grid[row][i] = grid[row][i-1];
                grid[row][0] = temp;
        }
    }

    // 동일한 열이 있는지 검사 (1: 위쪽, -1: 아래쪽)
    static boolean searchSameColumn(int row, int direction) {
        for(int i = 0; i < m; i++) {
            if(grid[row][i] == grid[row-direction][i])
                return true;
        }

        return false;
    }

    // 한번의 처리 흐름
    static void simulate(int row, int direction) {
        // 1차 바람 처리
        windBlow(row, direction);

        // 위쪽 방향으로 전파 확인 (끝에 닿을때까지)
        int curRow = row;
        int curDirection = direction;
        while(curRow > 0) {
            if (searchSameColumn(curRow, 1)) {
                windBlow(--curRow, (++curDirection) % 2);
            } else break;
        }

        // 아래쪽 방향으로 전파 확인 (끝에 닿을때까지)
        curRow = row;
        curDirection = direction;
        while(curRow < n-1) {
            if(searchSameColumn(curRow, -1)) {
                windBlow(++curRow, (++curDirection) % 2);
            }
            else break;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Character, Integer> directionMap = new HashMap<>();
        directionMap.put('R', 0);
        directionMap.put('L', 1);

        n = sc.nextInt();
        m = sc.nextInt();
        q = sc.nextInt();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        for(int i = 0; i < q; i++) {
            int r = sc.nextInt() - 1;
            char d = sc.next().charAt(0);
            simulate(r, directionMap.get(d));
        }

        for(int C = 0; C < n; C++) {
            for(int R = 0; R < m; R++) {
                System.out.print(grid[C][R] + " ");
            }
            System.out.println();
        }
    }
}

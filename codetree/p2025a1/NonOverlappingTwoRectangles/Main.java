package codetree.p2025a1.NonOverlappingTwoRectangles;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int MAX_N = 5;
    static int n, m;

    static int[][] grid = new int[MAX_N][MAX_N];
    static int[][] totalGrid = new int[MAX_N][MAX_N];

    static class Square {
        int r1, c1;
        int r2, c2;
        int sum;

        public Square(int r1, int c1, int r2, int c2) {
            this.r1 = r1;
            this.c1 = c1;
            this.r2 = r2;
            this.c2 = c2;
        }

        public void calculateSum() {
            if (r1 == 0 && c1 == 0) {
                sum = totalGrid[r2][c2];
            }
            else if (r1 == 0) {
                sum = totalGrid[r2][c2]
                        - totalGrid[r2][c1 - 1];
            }
            else if (c1 == 0) {
                sum = totalGrid[r2][c2]
                        - totalGrid[r1 - 1][c2];
            }
            else {
                sum = totalGrid[r2][c2]
                        - totalGrid[r2][c1 - 1]
                        - totalGrid[r1 - 1][c2]
                        + totalGrid[r1 - 1][c1 - 1];
            }
        }
    }

    // 누적 합 구하기
    static void makeTotalSumArray() {
        totalGrid = new int[n][m];
        totalGrid[0][0] = grid[0][0];

        for(int r = 1; r < n; r++) {
            totalGrid[r][0] = totalGrid[r-1][0]
                                + grid[r][0];
        }

        for(int c = 1; c < m; c++) {
            totalGrid[0][c] = totalGrid[0][c-1]
                                + grid[0][c];
        }

        for(int r = 1; r < n; r++) {
            for(int c = 1; c < m; c++) {
                totalGrid[r][c] = totalGrid[r-1][c]
                                    + totalGrid[r][c-1]
                                    - totalGrid[r-1][c-1]
                                    + grid[r][c];
            }
        }
    }

    // 두 직사각형이 겹치는지 아닌지 검사
    static boolean isOverlap(Square a, Square b) {
        if(a.r2 < b.r1) return false;
        if(a.c2 < b.c1) return false;
        if(b.r2 < a.r1) return false;
        if(b.c2 < a.c1) return false;

        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        grid = new int[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        makeTotalSumArray();

        ArrayList<Square> squares = new ArrayList<>();
        for(int r1 = 0; r1 < n; r1++) {
            for(int c1 = 0; c1 < m; c1++) {
                for(int r2 = r1; r2 < n; r2++) {
                    for(int c2 = c1; c2 < m; c2++) {
                        Square s = new Square(r1,c1,r2,c2);
                        s.calculateSum();
                        squares.add(s);
                    }
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for(int i = 0; i < squares.size(); i++) {
            for(int j = i + 1; j < squares.size(); j++) {
                Square a = squares.get(i);
                Square b = squares.get(j);
                if(!isOverlap(a, b))
                    max = Math.max(max, a.sum + b.sum);
            }
        }

        System.out.println(max);
    }
}

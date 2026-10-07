package codetree.p2025a1.AreaOfNonOverlappingRectangle;

import java.util.Scanner;

public class Main {
    static int[][] board = new int[2000][2000];

    static void checkSquare(int r1, int c1, int r2, int c2, int cnt) {
        for(int i = r1; i < r2; i++) {
            for(int j = c1; j < c2; j++) {
                board[i][j] = cnt;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 첫 번째 직사각형 입력
        int ac1 = sc.nextInt() + 1000;
        int ar1 = sc.nextInt() + 1000;
        int ac2 = sc.nextInt() + 1000;
        int ar2 = sc.nextInt() + 1000;
        checkSquare(ar1, ac1, ar2, ac2, 1);

        // 두 번째 직사각형 입력
        int bc1 = sc.nextInt() + 1000;
        int br1 = sc.nextInt() + 1000;
        int bc2 = sc.nextInt() + 1000;
        int br2 = sc.nextInt() + 1000;
        checkSquare(br1, bc1, br2, bc2, 1);

        // M 직사각형 입력
        int mc1 = sc.nextInt() + 1000;
        int mr1 = sc.nextInt() + 1000;
        int mc2 = sc.nextInt() + 1000;
        int mr2 = sc.nextInt() + 1000;
        checkSquare(mr1, mc1, mr2, mc2, 0);

        int cnt = 0;
        for(int i = 0; i < 2000; i++) {
            for(int j = 0; j < 2000; j++) {
                cnt += board[i][j];
            }
        }

        System.out.println(cnt);
    }
}

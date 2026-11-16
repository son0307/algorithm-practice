package swea.p1974;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int tc = 1; tc <= T; tc++) {
            boolean[][] rows = new boolean[9][10];
            boolean[][] cols = new boolean[9][10];
            boolean[][] boxes = new boolean[9][10];
            int ans = 1;

            for(int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    int num = sc.nextInt();
                    int box = (y / 3) * 3 + (x / 3);

                    if(rows[y][num] || cols[x][num] || boxes[box][num])
                        ans = 0;

                    rows[y][num] = true;
                    cols[x][num] = true;
                    boxes[box][num] = true;
                }
            }

            System.out.println("#" + tc + " " + ans);
        }
    }
}

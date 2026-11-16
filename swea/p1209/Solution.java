package swea.p1209;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for(int i = 1; i <= 10; i++) {
            int tc = sc.nextInt();

            int max = 0;
            int[][] map = new int[100][100];
            for(int y = 0; y < 100; y++)
                for(int x = 0; x < 100; x++)
                    map[y][x] = sc.nextInt();

            int dia1 = 0;
            int dia2 = 0;

            for(int y = 0; y < 100; y++) {
                int hor = 0;
                int ver = 0;

                for(int x = 0; x < 100; x++) {
                    hor += map[y][x];
                    ver += map[x][y];
                }

                dia1 += map[y][y];
                dia2 += map[y][99-y];

                max = Math.max(max, Math.max(hor, ver));
                max = Math.max(max, Math.max(dia1, dia2));
            }

            System.out.println("#" + tc + " " + max);
        }
    }
}

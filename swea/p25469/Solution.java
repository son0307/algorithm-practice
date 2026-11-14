package swea.p25469;

import java.util.Scanner;

public class Solution {
    public static int h, w;
    public static char[][] map;

    public static int getMin() {
        // 가로로 페인트 검사
        int hor = 0;
        for(int i = 0; i < h; i++) {
            boolean isAvailable = true;
            for(int j = 0; j < w; j++) {
                if(map[i][j] != '#') {
                    isAvailable = false;
                    break;
                }
            }
            if(isAvailable) hor++;
        }

        // 세로로 페인트 검사
        int ver = 0;
        for(int i = 0; i < w; i++) {
            boolean isAvailable = true;
            for(int j = 0; j < h; j++) {
                if(map[j][i] != '#') {
                    isAvailable = false;
                    break;
                }
            }
            if(isAvailable) ver++;
        }

        if(hor == h) return Math.min(hor, ver);

        return hor + ver;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for(int i = 0; i < t; i++) {
            h = sc.nextInt();
            w = sc.nextInt();

            map = new char[h][w];
            for(int y = 0; y < h; y++) {
                map[y] = sc.next().toCharArray();
            }

            System.out.println(getMin());
        }
    }
}

package codetree.simulation01.slantedRectangle;

import java.io.*;
import java.util.*;

public class Main {
    // 1(우상), 2(좌상), 3(좌하), 4(우하)
    static int[] dy = {-1, -1, 1, 1};
    static int[] dx = {1, -1, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        // 모든 점을 시작점으로 설정
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // 1, 3번 방향 이동횟수, 2, 4번 방향 이동횟수 설정
                for (int w = 1; w < n; w++) {
                    for (int h = 1; h < n; h++) {

                        if (isPossible(i, j, w, h, n))
                            max = Math.max(max, getScore(i, j, w, h, map));
                    }
                }
            }
        }

        System.out.println(max);
    }

    // 만들어진 직사각형이 맵 위에 있는지 검사
    static boolean isPossible(int y, int x, int w, int h, int n) {
        // 1번 방향
        int y1 = y - w, x1 = x + w;
        if (y1 < 0 || y1 >= n || x1 < 0 || x1 >= n) return false;

        // 2번 방향
        int y2 = y1 - h, x2 = x1 - h;
        if (y2 < 0 || y2 >= n || x2 < 0 || x2 >= n) return false;

        // 3번 방향
        int y3 = y2 + w, x3 = x2 - w;
        if (y3 < 0 || y3 >= n || x3 < 0 || x3 >= n) return false;

        return true;
    }

    // 테두리를 따라 숫자 합 계산
    static int getScore(int i, int j, int w, int h, int[][] map) {
        int sum = 0;
        int[] moveCount = {w, h, w ,h};

        int curY = i, curX = j;

        for (int dir = 0; dir < 4; dir++) {
            for (int step = 0; step < moveCount[dir]; step++) {
                curY += dy[dir];
                curX += dx[dir];
                sum += map[curY][curX];
            }
        }

        return sum;
    }
}

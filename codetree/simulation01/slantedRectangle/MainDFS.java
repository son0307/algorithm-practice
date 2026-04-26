package codetree.simulation01.slantedRectangle;

import java.util.*;
import java.io.*;

public class MainDFS {
    static int[] dy = {0, -1, -1, 1, 1};
    static int[] dx = {0, 1, -1, -1, 1};

    static int n;
    static int startY, startX;
    static int[][] map;

    static int max = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 2; i < n; i++) {
            for (int j = 1; j < n-1; j++) {
                startY = i;
                startX = j;
                dfs(1, i, j, 0);
            }
        }

        System.out.println(max);
    }

    static void dfs(int dir, int curY, int curX, int sum) {
        // 범위 벗어났으면 탐색 중단
        if (curY < 0 || curY >= n || curX < 0 || curX >= n) return;

//        System.out.println("dir: " + dir + ", curY: " + curY + ", curX: " + curX + ", sum: " + sum);

        // 시작지점으로 되돌아 왔으면 최대값 갱신
        if (sum != 0 && curY == startY && curX == startX) {
            max = Math.max(max, sum);
            return;
        }

        // 현재 방향으로 계속 전진
        dfs(dir, curY + dy[dir], curX + dx[dir], sum + map[curY][curX]);

        // 방향을 바꿔서 전진 (시작 지점, 4번 방향 전진 에서는 해당 x)
        if (!(curY == startY && curX == startX) && dir != 4) {
            dir++;
            dfs(dir, curY + dy[dir], curX + dx[dir], sum + map[curY][curX]);
        }
    }
}

package codetree.backTracking03.collectCoinsEasy;

import java.io.*;
import java.util.*;

public class MainBFS {
    static int n;
    static char[][] map;
    static boolean[][][][] visited;
    static int[] start;
    static int min = -1;

    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    static class Cell {
        int x, y;
        int distance;
        int coinCount;
        int lastCoin;

        public Cell(int y, int x, int distance, int coinCount, int lastCoin) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.coinCount = coinCount;
            this.lastCoin = lastCoin;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        map = new char[n][n];
        visited = new boolean[n][n][10][10];

        int coinCount = 0;

        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'S') start = new int[]{i, j};
                else if (map[i][j] != '.' && map[i][j] != 'E') coinCount++;
            }
        }

        if (coinCount < 3) {
            System.out.println(-1);
            return;
        }

        bfs();

        System.out.println(min);
    }

    static void bfs() {
        Queue<Cell> q = new ArrayDeque<>();
        q.add(new Cell(start[0], start[1], 0, 0, 0));
        visited[start[0]][start[1]][0][0] = true;

        while (!q.isEmpty()) {
            Cell current = q.poll();
            int x = current.x;
            int y = current.y;
            int distance = current.distance;
            int coinCount = current.coinCount;
            int lastCoin = current.lastCoin;

            if (map[y][x] == 'E' && current.coinCount >= 3) {
                min = current.distance;
                return;
            }

            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];

                if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                if (visited[ny][nx][lastCoin][coinCount]) continue;

                if (map[ny][nx] != '.' && map[ny][nx] != 'E' && map[ny][nx] != 'S') {
                    int coin = map[ny][nx] - '0';
                    if (coin > lastCoin) {
                        if (visited[ny][nx][coin][coinCount + 1]) continue;

                        visited[ny][nx][coin][coinCount + 1] = true;
                        q.add(new Cell(ny, nx, distance + 1, coinCount + 1, coin));
                    }
                }

                visited[ny][nx][lastCoin][coinCount] = true;
                q.add(new Cell(ny, nx, distance + 1, coinCount, lastCoin));
            }
        }
    }
}

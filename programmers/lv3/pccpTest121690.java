package programmers.lv3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class pccpTest121690 {
    static class State {
        int x;
        int y;
        int canJump;
        int step;

        public State(int x, int y, int canJump, int step) {
            this.x = x;
            this.y = y;
            this.canJump = canJump;
            this.step = step;
        }
    }

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public int solution(int n, int m, int[][] hole) {
        boolean[][] map = new boolean[m][n];
        for (boolean[] row : map)
            Arrays.fill(row, true);

        for (int[] h : hole) {
            int x = h[0] - 1;
            int y = h[1] - 1;
            map[y][x] = false;
        }

        boolean[][][] visited = new boolean[m][n][2];

        Queue<State> q = new LinkedList<>();
        q.add(new State(0, 0, 1, 0));
        visited[0][0][1] = true;

        while(!q.isEmpty()) {
            State s = q.poll();

            if (s.x == n - 1 && s.y == m - 1)
                return s.step;

            for (int i = 0; i < 4; i++) {
                int nx = s.x + dx[i];
                int ny = s.y + dy[i];

                if (nx < 0 || nx >= n || ny < 0 || ny >= m)
                    continue;

                if (map[ny][nx]) {
                    if (visited[ny][nx][s.canJump])
                        continue;

                    visited[ny][nx][s.canJump] = true;
                    q.add(new State(nx, ny, s.canJump, s.step + 1));
                }

                if (s.canJump != 1)
                    continue;

                int nnx = nx + dx[i];
                int nny = ny + dy[i];

                if (nnx < 0 || nnx >= n || nny < 0 || nny >= m)
                    continue;

                if (map[nny][nnx]) {
                    if (visited[nny][nnx][0])
                        continue;

                    visited[nny][nnx][0] = true;
                    q.add(new State(nnx, nny, 0, s.step + 1));
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int n = 4;
        int m = 4;
        int[][] hole = {{2,3},{3,3}};

        System.out.println(new pccpTest121690().solution(n, m, hole));
    }
}

package codetree.bfs01.weAreTheOne;

import java.util.*;
import java.io.*;

public class Main {
    static int n, k, u, d;
    static int max = Integer.MIN_VALUE;
    static int[][] map;
    static List<int[]> selected = new ArrayList<>();

    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        u = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);

        System.out.println(max);
    }

    static void dfs(int idx, int cnt) {
        if (cnt == k) {
            max = Math.max(max, bfs());
            return;
        }

        for (int i = idx; i < n*n; i++) {
            int row = i / n;
            int col = i % n;

            selected.add(new int[]{row, col});
            dfs(i + 1, cnt + 1);
            selected.remove(selected.size() - 1);
        }
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        int count = 0;

        for (int[] s : selected) {
            q.add(s);
            visited[s[0]][s[1]] = true;
            count++;
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                if (visited[ny][nx]) continue;

                int diff = Math.abs(map[ny][nx] - map[cur[0]][cur[1]]);
                if (diff >= u && diff <= d) {
                    visited[ny][nx] = true;
                    count++;
                    q.add(new int[]{ny, nx});
                }
            }
        }

        return count;
    }
}

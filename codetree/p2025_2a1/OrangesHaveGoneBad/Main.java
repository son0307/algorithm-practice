package codetree.p2025_2a1.OrangesHaveGoneBad;

import java.util.*;

public class Main {
    static int n, k;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    static boolean inRange(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();

        int[][] ans = new int[n][n];
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> q = new ArrayDeque<>();

        int[][] grid = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
                if(grid[i][j] == 0) ans[i][j] = -1;
                else if(grid[i][j] == 2) {
                    ans[i][j] = 0;
                    visited[i][j] = true;
                    q.add(new int[]{i, j});
                }
            }
        }

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int i=0; i<4; i++) {
                int r = cur[0], c = cur[1];
                int nr = r + dr[i], nc = c + dc[i];

                if(inRange(nr, nc) && !visited[nr][nc] && grid[nr][nc] == 1) {
                    ans[nr][nc] = ans[r][c] + 1;
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1 && !visited[i][j])
                    System.out.print("-2 ");
                else
                    System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }
}

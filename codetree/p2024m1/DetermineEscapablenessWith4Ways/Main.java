package codetree.p2024m1.DetermineEscapablenessWith4Ways;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] dy = {1,-1,0,0};
        int[] dx = {0,0,1,-1};
        Queue<int[]> q = new ArrayDeque<>();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                grid[i][j] = sc.nextInt();

        visited[0][0] = true;
        q.add(new int[]{0, 0});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                if(ny == n - 1 && nx == m - 1) {
                    System.out.println(1);
                    return;
                }
                if(ny < 0 || nx < 0 || ny >= n || nx >= m) continue;
                if(visited[ny][nx] || grid[ny][nx] == 0) continue;

                visited[ny][nx] = true;
                q.add(new int[]{ny,nx});
            }
        }

        System.out.println(0);
    }
}

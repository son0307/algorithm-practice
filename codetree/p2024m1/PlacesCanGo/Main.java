package codetree.p2024m1.PlacesCanGo;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] dy = {1,-1,0,0};
        int[] dx = {0,0,1,-1};

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[][] grid = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int cnt = 0;
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> q = new ArrayDeque<>();
        for(int i = 0; i < k; i++) {
            int y = sc.nextInt()-1;
            int x = sc.nextInt()-1;
            q.add(new int[]{y,x});
            visited[y][x] = true;
            cnt++;
        }

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                if(ny < 0 || nx < 0 || ny >= n || nx >= n) continue;
                if(visited[ny][nx] || grid[ny][nx] == 1) continue;

                visited[ny][nx] = true;
                cnt++;
                q.add(new int[]{ny, nx});
            }
        }

        System.out.println(cnt);
    }
}

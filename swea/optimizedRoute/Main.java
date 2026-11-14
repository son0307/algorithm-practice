package swea.optimizedRoute;

import java.io.*;
import java.util.*;

public class Main {
    static int minDistance;

    static void dfs(int cur, int n, int cnt, int curDistance, int[][] map, boolean[] visited) {
        if(curDistance >= minDistance) return;

        if(n-2 == cnt) {
            int distance = Math.abs(map[cur][0] - map[1][0]) + Math.abs(map[cur][1] - map[1][1]);
            minDistance = Math.min(minDistance, curDistance + distance);
            return;
        }

        for(int i = 2; i < n; i++) {
            if(!visited[i]) {
                visited[i] = true;
                int distance = Math.abs(map[cur][0] - map[i][0]) + Math.abs(map[cur][1] - map[i][1]);
                dfs(i, n, cnt + 1, curDistance + distance, map, visited);
                visited[i] = false;
            }
        }

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for(int t = 1; t <= T; t++) {
            int n = Integer.parseInt(br.readLine().trim()) + 2;
            minDistance = Integer.MAX_VALUE;
            int[][] map = new int[n][n];
            boolean[] visited = new boolean[n];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++) {
                map[i][0] = Integer.parseInt(st.nextToken());
                map[i][1] = Integer.parseInt(st.nextToken());
            }

            dfs(0, n, 0, 0, map, visited);

            sb.append("#" + t + " ").append(minDistance).append("\n");
        }

        System.out.println(sb);
    }
}

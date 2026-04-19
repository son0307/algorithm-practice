package codetree.backTracking04.minValueMaximize;

import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static boolean[] visited;
    static int[][] arr;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        visited = new boolean[n];
        arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, Integer.MAX_VALUE);

        System.out.println(max);
    }

    static void dfs(int cnt, int nowMin) {
        if (cnt == n) {
            max = Math.max(max, nowMin);
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(cnt + 1, Math.min(arr[cnt][i], nowMin));
                visited[i] = false;
            }
        }
    }
}
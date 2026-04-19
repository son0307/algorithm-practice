package codetree.backTracking04.sumMaximize;

import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static boolean[] visited;
    static int[][] arr;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        maximize(0, 0);

        System.out.println(max);
    }

    static void maximize(int cnt, int sum) {
        if (cnt == n) {
            max = Math.max(sum, max);
            return;
        }

        for (int j = 0; j < n; j++) {
            if (visited[j]) continue;

            visited[j] = true;
            maximize(cnt + 1, sum + arr[cnt][j]);
            visited[j] = false;
        }
    }
}

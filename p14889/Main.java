package p14889;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    int minDiff = Integer.MAX_VALUE;

    public void dfs(int n, int[][] S, boolean[] visited, int idx, int count) {
        if (count == n/2) {
            int stat1 = 0;
            int stat2 = 0;

            for (int i = 1; i <= n; i++) {
                for (int j = i; j <= n; j++) {
                    if (visited[i] && visited[j]) stat1 += S[i][j] + S[j][i];
                    else if (!visited[i] && !visited[j]) stat2 += S[i][j] + S[j][i];
                }
            }

            minDiff = Math.min(minDiff, Math.abs(stat1 - stat2));

            return;
        }

        for (int i = idx; i <= n; i++) {
            visited[i] = true;
            dfs(n, S, visited, i + 1, count + 1);
            visited[i] = false;
            if (minDiff == 0) return;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] S = new int[n+1][n+1];
        boolean[] visited = new boolean[n+1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Main m = new Main();
        visited[1] = true;
        m.dfs(n, S, visited, 2, 1);
        System.out.println(m.minDiff);
    }
}

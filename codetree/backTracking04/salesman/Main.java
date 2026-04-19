package codetree.backTracking04.salesman;

import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static boolean[] visited;
    static int[][] distance;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        visited = new boolean[n];
        distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited[0] = true;
        dfs(0, 0, 0);

        System.out.println(min);
    }

    static void dfs(int cnt, int i, int sum) {
        // 현재까지의 비용이 최솟값 이상이 되면 더 탐색할 필요 없음
        if (sum >= min) return;

        // 0번을 제외한 모든 노드 방문 시 최솟값 갱신
        if (cnt == n-1) {
            // 마지막 노드에서 0번 노드로 돌아가는 길 체크
            if (distance[i][0] == 0) return;

            min = Math.min(min, sum + distance[i][0]);
            return;
        }

        for (int j = 0; j < n; j++) {
            // 방문 X, 이동 가능한 경로 존재한 경우
            if (!visited[j] && distance[i][j] != 0) {
                visited[j] = true;
                dfs(cnt + 1, j, sum + distance[i][j]);
                visited[j] = false;
            }
        }
    }
}

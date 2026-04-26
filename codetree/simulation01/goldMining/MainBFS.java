package codetree.simulation01.goldMining;

import java.util.*;
import java.io.*;

public class MainBFS {
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        for (int k = 0; k <= n; k++) {
            int price = k*k + (k+1)*(k+1);

            // 각 좌표를 중심점으로 삼아 거리가 K 이하인 마름모 영역 탐색
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int goldCnt = map[i][j];
                    boolean[][] visited = new boolean[n][n];
                    Queue<int[]> q = new ArrayDeque<>();

                    visited[i][j] = true;
                    q.add(new int[]{i, j, 0});
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int y = cur[0];
                        int x = cur[1];
                        int d = cur[2];

                        for (int l = 0; l < 4; l++) {
                            int ny = y + dy[l];
                            int nx = x + dx[l];
                            int nd = d + 1;

                            if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                            if (visited[ny][nx] || nd > k) continue;

                            goldCnt += map[ny][nx];
                            visited[ny][nx] = true;
                            q.add(new int[]{ny, nx, nd});
                        }
                    }

                    if (goldCnt * m >= price) {
                        max = Math.max(max, goldCnt);
                    }
                }
            }
        }

        System.out.println(max);
    }
}

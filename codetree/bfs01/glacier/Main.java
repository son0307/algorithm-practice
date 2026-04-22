package codetree.bfs01.glacier;

import java.util.*;
import java.io.*;

public class Main {
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> waterQ = new ArrayDeque<>();

        // (0, 0) 시작점 설정
        waterQ.add(new int[]{0, 0});
        visited[0][0] = true;

        int time = 0;
        int lastMelted = 0;

        while(true) {
            int meltedCount = 0;
            // 이번 턴에 녹은 빙하들의 좌표 저장
            Queue<int[]> meltedQ = new ArrayDeque<>();

            while(!waterQ.isEmpty()) {
                int[] cur = waterQ.poll();

                for (int i = 0; i < 4; i++) {
                    int ny = cur[0] + dy[i];
                    int nx = cur[1] + dx[i];

                    if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                    if (visited[ny][nx]) continue;

                    visited[ny][nx] = true;

                    if (map[ny][nx] == 1) {
                        // 빙하를 만나면 녹이고, 녹은 빙하 좌표 저장
                        meltedCount++;
                        map[ny][nx] = 0;
                        meltedQ.add(new int[]{ny, nx});
                    } else {
                        // 물을 만나면 현재 턴에 계속해서 탐색
                        waterQ.add(new int[]{ny, nx});
                    }
                }
            }

            // 이번 턴에 녹은 빙하가 하나도 없다면 종료
            if (meltedCount == 0) {
                System.out.println(time + " " + lastMelted);
                return;
            }

            lastMelted = meltedCount;
            time++;
            // 다음 턴의 탐색은 이번 턴에서 녹은 빙하들의 위치들부터 탐색 시작
            waterQ = meltedQ;
        }
    }
}

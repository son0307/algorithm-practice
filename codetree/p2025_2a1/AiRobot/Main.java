package codetree.p2025_2a1.AiRobot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int MAX_N = 30;
    static int N, K, L;

    static int[][] dustMap = new int[MAX_N][MAX_N];
    static int[][] AIMap = new int[MAX_N][MAX_N];

    static int[] dr = {1,-1,0,0};
    static int[] dc = {0,0,1,-1};
    static int[][] cleanDr = {{-1,0,0,1}, {0,0,0,1}, {-1,0,0,1}, {-1,0,0,0}};
    static int[][] cleanDc = {{0,0,1,0}, {-1,0,1,0}, {0,-1,0,0}, {0,-1,0,1}};

    static boolean inRange(int r, int c) {
        return r >= 0 && c >= 0 && r < N && c < N;
    }

    static class AI {
        int r, c;

        public AI(int r, int c) {
            this.r = r;
            this.c = c;
        }

        // 다음에 이동할 위치 탐색
        public void moveToClosest() {
            Queue<int[]> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[N][N];

            q.add(new int[]{r, c});
            visited[r][c] = true;

            while (!q.isEmpty()) {
                int size = q.size();

                int targetR = Integer.MAX_VALUE;
                int targetC = Integer.MAX_VALUE;

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int cr = cur[0];
                    int cc = cur[1];

                    // 행이 더 작거나 행은 같은데 열이 더 작으면 교체
                    if (dustMap[cr][cc] > 0) {
                        if (cr < targetR || (cr == targetR && cc < targetC)) {
                            targetR = cr;
                            targetC = cc;
                        }
                    }

                    // 다음 레벨 탐색
                    for (int d = 0; d < 4; d++) {
                        int nr = cr + dr[d];
                        int nc = cc + dc[d];

                        if (!inRange(nr, nc) || visited[nr][nc] || dustMap[nr][nc] == -1 || AIMap[nr][nc] == 1)
                            continue;

                        visited[nr][nc] = true;
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }

        // 청소 수행
        public void clean() {
            int max = 0;
            int maxDir = 0;

            // 4방향 청소 먼지량 비교
            for (int i = 0; i < 4; i++) {
                int cur = 0;
                for (int j = 0; j < 4; j++) {
                    int nr = r + cleanDr[i][j];
                    int nc = c + cleanDc[i][j];
                    if (inRange(nr, nc) && dustMap[nr][nc] > 0)
                        cur += Math.min(dustMap[nr][nc], 20);
                }

                if (cur > max) {
                    max = cur;
                    maxDir = i;
                }
            }

            // 가장 많이 청소할 수 있는 방향이 정해졌으면 청소
            for (int j = 0; j < 4; j++) {
                int nr = r + cleanDr[maxDir][j];
                int nc = c + cleanDc[maxDir][j];
                if (inRange(nr, nc) && dustMap[nr][nc] > 0)
                    dustMap[nr][nc] -= Math.min(dustMap[nr][nc], 20);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                dustMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        AI[] ais = new AI[K];
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            ais[i] = new AI(r, c);
            AIMap[r][c] = 1;
        }

        for (int t = 0 ; t < L; t++) {
            // 순서대로 로봇 옮기기
            for (AI a : ais)
                a.moveToClosest();

            // 순서대로 청소
            for (AI a : ais)
                a.clean();

            // 먼지 5 추가
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dustMap[i][j] != -1 && dustMap[i][j] != 0)
                        dustMap[i][j] += 5;
                }
            }

            // 먼지 확산
            int[][] nextDustMap = new int[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (dustMap[r][c] == 0) {
                        int sum = 0;
                        for (int i = 0; i < 4; i++) {
                            int nr = r + dr[i];
                            int nc = c + dc[i];

                            if (inRange(nr, nc) && dustMap[nr][nc] != -1) {
                                sum += dustMap[nr][nc];
                            }
                        }
                        nextDustMap[r][c] = sum / 10;
                    }
                }
            }

            // 먼지 총량 계산
            int total = 0;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (nextDustMap[r][c] != 0)
                        dustMap[r][c] += nextDustMap[r][c];

                    if (dustMap[r][c] != -1)
                        total += dustMap[r][c];
                }
            }
            System.out.println(total);

            if(total == 0) break;
        }
    }
}

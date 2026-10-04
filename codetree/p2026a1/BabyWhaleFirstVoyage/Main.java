package codetree.p2026a1.BabyWhaleFirstVoyage;

import java.util.*;

public class Main {
    static int N;
    static int[][] grid;
    static boolean[][] visited;

    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    static Boolean inRange(int r, int c) {
        return 0 <= r && 0 <= c && r < N && c < N;
    }

    // 주어진 좌표를 기준으로 거리맵 계산
    static int[][] getDistanceMap(int sr, int sc) {
        int[][] dist = new int[N][N];
        for(int[] row : dist) Arrays.fill(row, -1);

        dist[sr][sc] = 0;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            for(int i = 0; i < 4; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if(inRange(nr, nc) && dist[nr][nc] == -1 && grid[nr][nc] == 0) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        // System.out.println(Arrays.deepToString(dist));

        return dist;
    }

    // 1단계 인접 검사 후 다음 좌표 및 방향 제공, 이동 불가 시 -1로 채워서 반환
    static int[] getNextMove(int r, int c, int d) {
        int[] deltas = {0, 1, -1, 2};

        for(int i = 0; i < 4; i++) {
            int nd = (d + deltas[i] + 4) % 4;
            int nr = r + dr[nd];
            int nc = c + dc[nd];

            if(inRange(nr, nc) && grid[nr][nc] == 0 && !visited[nr][nc]) {
                return new int[]{nr, nc, nd};
            }
        }

        return new int[]{-1, -1, -1};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        int r = sc.nextInt() - 1;
        int c = sc.nextInt() - 1;
        int d = sc.nextInt() - 1;

        int[] dirMap = {0, 2, 1, 3};
        d = dirMap[d];
        int total = 0;

        grid = new int[N][N];
        visited = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = sc.nextInt();
                if(grid[i][j] == 0) total++;
            }
        }

        visited[r][c] = true;
        int cnt = 1;
        System.out.println((r+1) + " " + (c+1));

        while (cnt < total) {
            // 1단계 인접 영역 검사
            while(true) {
                int[] next = getNextMove(r, c, d);
                if(next[0] == -1) break;
                r = next[0]; c = next[1]; d = next[2];
                visited[r][c] = true;
                cnt++;
                System.out.println((r+1) + " " + (c+1));
            }

            // 2단계 가장 가까운 바다로 이동
            // 현재 위치에서 최단 거리 바다 찾기
            int[][] distanceMapFromWhale = getDistanceMap(r, c);

            int nextR = -1; int nextC = -1; int minDist = Integer.MAX_VALUE;
            for(int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(grid[i][j] != 0 || visited[i][j] || distanceMapFromWhale[i][j] == -1) continue;
                    if(distanceMapFromWhale[i][j] < minDist) {
                        nextR = i; nextC = j; minDist = distanceMapFromWhale[i][j];
                    }
                }
            }

            // System.out.println("r: " + r + " c: " + c + " nextR: " + nextR + " nextC: " + nextC);

            // 최단 거리 바다에서부터 모든 칸에 대한 거리 구하기
            // 이후, 고래를 한칸씩 옮기기
            int[][] distanceMapFromSea = getDistanceMap(nextR, nextC);

            int[] searchPriority = {1,2,3,0};
            while(r != nextR || c != nextC) {
                for(int p : searchPriority) {
                    int nr = r + dr[p], nc = c + dc[p];
                    if (inRange(nr, nc) && grid[nr][nc] == 0 && distanceMapFromSea[r][c] - distanceMapFromSea[nr][nc] == 1) {
                        r = nr; c = nc; d = p;
                        // System.out.println("nr: " + nr + " nc: " + nc + " d: " + d);
                        break;
                    }
                }
            }

            System.out.println((r+1) + " " + (c+1));
            cnt++;
            visited[r][c] = true;
        }
    }
}

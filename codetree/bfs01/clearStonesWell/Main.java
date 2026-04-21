package codetree.bfs01.clearStonesWell;

import java.io.*;
import java.util.*;

public class Main {
    static int n, m, k;
    static int[][] map;
    static List<int[]> startPoints = new ArrayList<>();
    static List<int[]> stones = new ArrayList<>();
    static int maxArea = 0;

    static int[] dy = {0, 0, 1, -1};
    static int[] dx = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    stones.add(new int[]{i, j});
                }
            }
        }

        // 시작 점을 입력받아 위치 저장
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            startPoints.add(new int[]{r, c});
        }

        dfs(0, 0);

        System.out.println(maxArea);
    }

    public static void dfs(int idx, int removeCount) {
        // 치운 돌 개수가 m개가 되면 BFS를 통해 방문 가능한 칸 최댓값 계산
        if (removeCount == m) {
            maxArea = Math.max(maxArea, bfs());
            return;
        }

        // 더 이상 치울 돌이 없으면 종료
        if (idx == stones.size()) return;

        // 현재 인덱스의 돌을 치우는 경우
        int[] stone = stones.get(idx);
        map[stone[0]][stone[1]] = 0;
        dfs(idx + 1, removeCount + 1);
        map[stone[0]][stone[1]] = 1;

        // 현재 인덱스의 돌을 치우지 않는 경우
        dfs(idx + 1, removeCount);
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        int count = 0;

        // 시작점 방문 처리 후 큐에 삽입
        for (int[] startPoint : startPoints) {
            int y = startPoint[0];
            int x = startPoint[1];
            if (!visited[y][x]) {
                visited[y][x] = true;
                q.add(new int[]{y, x});
                count++;
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                // 맵을 벗어났거나, 이미 방문했거나, 돌이 있으면 스킵
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                if (map[ny][nx] == 1 || visited[ny][nx]) continue;

                visited[ny][nx] = true;
                q.add(new int[]{ny, nx});
                count++;
            }
        }

        return count;
    }
}

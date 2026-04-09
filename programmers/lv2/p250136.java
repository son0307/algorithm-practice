package programmers.lv2;

import java.util.*;

public class p250136 {

    int n, m;
    int[] dr = { -1, 1, 0, 0 };
    int[] dc = { 0, 0, -1, 1 };

    public int solution(int[][] land) {
        n = land.length;
        m = land[0].length;
        int answer = 0;

        HashMap<Integer, Integer> oilSizes = new HashMap<>();
        int oilId = 2;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (land[r][c] == 1) {
                    int oilSize = bfs(land, r, c, oilId);
                    oilSizes.put(oilId, oilSize);
                    oilId++;
                }
            }
        }

        for (int c = 0; c < m; c++) {
            HashSet<Integer> checkedOils = new HashSet<>();
            int curSize = 0;

            for (int r = 0; r < n; r++) {
                if (land[r][c] != 0) {
                    if (!checkedOils.contains(land[r][c])) {
                        checkedOils.add(land[r][c]);
                        curSize += oilSizes.get(land[r][c]);
                    }
                }
            }

            System.out.println("curSize = " + curSize);
            if (answer < curSize)
                answer = curSize;
        }

        return answer;
    }

    private int bfs(int[][] land, int x, int y, int oilId) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        land[x][y] = oilId;
        int size = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            size++;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m && land[nr][nc] == 1) {
                    land[nr][nc] = oilId;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        return size;
    }

    public static void main(String[] args) {
        int[][] land = new int[][]{
                {0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 0, 0, 0, 1, 1, 0}, {1, 1, 1, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0, 1, 1}
        };
        p250136 solution = new p250136();
        System.out.println(solution.solution(land));
    }
}

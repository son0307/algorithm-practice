package programmers.lv2;

import java.util.LinkedList;
import java.util.Queue;

public class p159993 {
    int[] dc = {0, 0, -1, 1};
    int[] dr = {1, -1, 0, 0};

    public int solution(String[] maps) {
        int StoL = 0;
        int LtoE = 0;

        int n = maps.length;
        int m = maps[0].length();

        String[][] map = new String[n][m];
        for (int i = 0; i < n; i++) {
            map[i] = maps[i].split("");
        }

        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j].equals("S"))
                    StoL = bfs(map, visited, i, j, "L");
            }
        }
        if (StoL == -1)
            return -1;

        visited = new boolean[n][m];

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j].equals("L"))
                    LtoE = bfs(map, visited, i, j, "E");
            }
        }
        if (LtoE == -1)
            return -1;

        System.out.println("StoL = " + StoL + " LtoE = " + LtoE);
        return StoL + LtoE;
    }

    private int bfs(String[][] map, boolean[][] visited, int x, int y, String target) {
        Queue<int[]> q = new LinkedList<>();
        visited[x][y] = true;
        q.add(new int[]{x, y, 1});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int curX = cur[0];
            int curY = cur[1];
            int count = cur[2];

            for (int i = 0; i < 4; i++) {
                int nx = curX + dc[i];
                int ny = curY + dr[i];

                if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length && !visited[nx][ny] && !map[nx][ny].equals("X")) {
                    visited[nx][ny] = true;
                    if(map[nx][ny].equals(target))
                        return count;
                    q.add(new int[]{nx, ny, count + 1});
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        p159993 p = new p159993();
        String[] maps = new String[] {"LOOXS","OOOOX","OOOOO","OOOOO","EOOOO"};
        String[] maps2 = new String[] {"SOOOO", "OOOLO", "OOOOE", "OOOOO", "OOOOO"};
        String[] maps3 = new String[] {"SOOOO", "OOOOO", "OOOOO", "OOOOO", "OOOOO", "OOOLE"};
        System.out.println(p.solution(maps3));
    }
}

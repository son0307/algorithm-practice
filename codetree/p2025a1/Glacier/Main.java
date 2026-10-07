package codetree.p2025a1.Glacier;

import java.util.*;

public class Main {
    static int MAX_N = 200;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static int[][] map = new int[MAX_N][MAX_N];
    static int n, m;

    static boolean inRange(int r, int c) {
        return 0 <= r && 0 <= c && r < n && c < m;
    }

    static int bfs() {
        int melted = 0;

        int[][] nextMap = map;
        boolean[][] visited= new boolean[n][m];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        visited[0][0] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if(inRange(nr, nc) && !visited[nr][nc]) {
                    visited[nr][nc] = true;

                    if(nextMap[nr][nc] == 1) {
                        nextMap[nr][nc] = 0;
                        melted++;
                    }
                    else {
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }

        map = nextMap;
        return melted;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        map = new int[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        int turn = 0;
        int meltedInThisTurn = 0;
        while(true) {
            int melted = bfs();

            if(melted == 0) {
                System.out.println(turn + " " + meltedInThisTurn);
                break;
            } else {
                turn++;
                meltedInThisTurn = melted;
            }
        }
    }
}

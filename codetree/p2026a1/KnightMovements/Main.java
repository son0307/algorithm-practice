package codetree.p2026a1.KnightMovements;

import java.util.*;

public class Main {
    public static int[] dr = {-1, -2, -2, -1, 1, 2, 2, 1};
    public static int[] dc = {-2, -1, 1, 2, -2, -1, 1, 2};

    public static int n;

    public static boolean inBoard(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();

        int r1 = sc.nextInt();
        int c1 = sc.nextInt();
        int r2 = sc.nextInt();
        int c2 = sc.nextInt();

        r1--; c1--; r2--; c2--;

        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];

        visited[r1][c1] = true;
        q.add(new int[]{r1, c1, 0});
        while(!q.isEmpty()) {
            int[] cur = q.poll();

            if(cur[0] == r2 && cur[1] == c2) {
                System.out.println(cur[2]);
                return;
            }

            for(int i = 0; i < 8; i++) {
                int newR = cur[0] + dr[i];
                int newC = cur[1] + dc[i];

                if(inBoard(newR, newC) && !visited[newR][newC] ) {
                    visited[newR][newC] = true;
                    q.add(new int[]{newR, newC, cur[2] + 1});
                }
            }
        }

        System.out.println(-1);
    }
}

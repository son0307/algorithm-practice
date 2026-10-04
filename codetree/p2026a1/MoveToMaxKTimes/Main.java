package codetree.p2026a1.MoveToMaxKTimes;

import java.util.*;

public class Main {
    public static int[][] grid = new int[101][101];
    public static int[] dr = {1, -1, 0, 0};
    public static int[] dc = {0, 0, 1, -1};

    public static int n, k;
    public static int r, c;

    public static boolean inRange(int r, int c) {
        return r >= 1 && c >= 1 && r <= n && c <= n;
    }

    public static void search() {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n + 1][n + 1];

        int curValue = grid[r][c];
        int curMax = 0;
        int[] maxPos = new int[2];

        visited[r][c] = true;
        q.add(new int[]{r, c});
        while (!q.isEmpty()) {
            int[] curLocation = q.poll();
            int curR = curLocation[0];
            int curC = curLocation[1];

            for (int i = 0; i < 4; i++) {
                int nextR = curR + dr[i];
                int nextC = curC + dc[i];

                if (inRange(nextR, nextC) && !visited[nextR][nextC] && grid[nextR][nextC] < curValue) {
                    visited[nextR][nextC] = true;
                    q.add(new int[]{nextR, nextC});

                    if (grid[nextR][nextC] > curMax) {
                        curMax = grid[nextR][nextC];
                        maxPos = new int[]{nextR, nextC};
                    } else if (grid[nextR][nextC] == curMax) {
                        if (nextR < maxPos[0]) {
                            maxPos = new int[]{nextR, nextC};
                        } else if (nextR == maxPos[0]) {
                            if (nextC < maxPos[1]) {
                                maxPos[1] = nextC;
                            }
                        }
                    }
                }
            }
        }

        if (curMax != 0) {
            r = maxPos[0];
            c = maxPos[1];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        r = sc.nextInt();
        c = sc.nextInt();

        for(int i = 0; i < k; i++) {
            int prevR = r;
            int prevC = c;

            search();

            if(prevR == r && prevC == c)
                break;
        }

        System.out.println(r + " " + c);
    }
}

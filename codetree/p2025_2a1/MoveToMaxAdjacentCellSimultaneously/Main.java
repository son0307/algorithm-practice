package codetree.p2025_2a1.MoveToMaxAdjacentCellSimultaneously;

import java.util.*;

public class Main {
    static final int MAX_N = 20;
    static int n, m, t;

    static int[][] grid = new int[MAX_N][MAX_N];
    static int[][] marbles_map = new int[MAX_N][MAX_N];

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static boolean inRange(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    static int[] findMax(int sr, int sc) {
        int maxValue = 0;
        int[] maxPos = new int[2];

        for(int i = 0; i < 4; i++) {
            int nr = sr + dr[i];
            int nc = sc + dc[i];

            if(inRange(nr, nc) && maxValue < grid[nr][nc]) {
                maxValue = grid[nr][nc];
                maxPos[0] = nr; maxPos[1] = nc;
            }
        }

        return maxPos;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        t = sc.nextInt();

        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int[][] marbles = new int[m][2];
        for (int i = 0; i < m; i++) {
            marbles[i][0] = sc.nextInt() - 1;
            marbles[i][1] = sc.nextInt() - 1;
            marbles_map[marbles[i][0]][marbles[i][1]]++;
        }

        for(int i = 0; i < t; i++) {
            int[][] next_marbles_map = new int[MAX_N][MAX_N];

            // 구슬이 있는 위치 찾아내기
            for(int r = 0; r < n; r++) {
                for(int c = 0; c < n; c++) {
                    if(marbles_map[r][c] == 1) {
                        // 상하좌우 방향 순서대로 검사 -> 최댓값이 있는 위치로 이동
                        int[] nextPos = findMax(r, c);
                        next_marbles_map[nextPos[0]][nextPos[1]]++;
                    }
                }
            }

            // 모든 구슬 이동 후 2개 이상 같이 있는 칸은 제거
            for(int r = 0; r < n; r++) {
                for(int c = 0; c < n; c++) {
                    if(next_marbles_map[r][c] >= 2) {
                        next_marbles_map[r][c] = 0;
                    }
                }
            }

            marbles_map = next_marbles_map;
        }

        // t번 반복 후 마지막으로 남은 구슬 카운트
        int cnt = 0;
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                if(marbles_map[r][c] == 1) cnt++;
            }
        }

        System.out.println(cnt);
    }
}

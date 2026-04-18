package codetree.backTracking01.strongExplosion;

import java.io.*;
import java.util.*;

public class Main {
    static int max = Integer.MIN_VALUE;
    static int n;
    static int[][] damaged;
    static List<int[]> bombs = new ArrayList<>();

    static int[][][] bombShapes = {
            {{-2, 0}, {-1, 0}, {1, 0}, {2, 0}},     // 1번 폭탄
            {{-1, 0}, {1, 0}, {0, -1}, {0, 1}},     // 2번 폭탄
            {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}    // 3번 폭탄
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        damaged = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                if (Integer.parseInt(st.nextToken()) == 1)
                    bombs.add(new int[]{i,j});
            }
        }

        dfs(0);

        System.out.println(max);
    }

    static void dfs(int idx) {
        if (idx == bombs.size()) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (damaged[i][j] > 0) count++;
                }
            }
            max = Math.max(max, count);
            return;
        }

        int[] currentBomb = bombs.get(idx);
        int r = currentBomb[0];
        int c = currentBomb[1];

        // 3가지 폭탄 순서대로 놓기
        for (int t = 0; t < 3; t++) {
            boom(r, c, t, 1);

            dfs(idx + 1);

            boom(r, c, t, -1);
        }
    }

    public static void boom(int r, int c, int t, int delta) {
        damaged[r][c] += delta;

        for (int i = 0; i < 4; i++) {
            int nr = r + bombShapes[t][i][0];
            int nc = c + bombShapes[t][i][1];

            if (nr >= 0 && nc >= 0 && nr < n && nc < n)
                damaged[nr][nc] += delta;
        }
    }
}

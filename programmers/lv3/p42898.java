package programmers.lv3;

import java.util.Arrays;

public class p42898 {
    private int[][] dp = new int[101][101];

    private int count(int x, int y, int w, int h, boolean[][] puddles_map) {
        if (x > w || y > h) return 0;
        if (puddles_map[y][x]) return 0;

        if (dp[y][x] != -1) return dp[y][x];
        if (x == w && y == h) return 1;
        return dp[y][x] = (count(x, y + 1, w, h, puddles_map) + count(x+1, y, w, h, puddles_map)) % 1000000007;
    }

    public int solution(int m, int n, int[][] puddles) {
        boolean[][] puddles_map = new boolean[n+1][m+1];
        for (int[] puddle : puddles)
            puddles_map[puddle[1]][puddle[0]] = true;

        for (int[] row : dp)
            Arrays.fill(row, -1);

        count(1, 1, m, n, puddles_map);

        return dp[1][1];
    }

    public static void main(String[] args) {
        p42898 p = new p42898();
        int m = 4;
        int n = 3;
        int[][] puddles = {{2, 2}};
        System.out.println(p.solution(m, n, puddles));
    }
}

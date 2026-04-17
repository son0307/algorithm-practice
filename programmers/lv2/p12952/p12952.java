package programmers.lv2.p12952;

public class p12952 {
    private int count = 0;

    public int solution(int n) {
        boolean[] rows = new boolean[n];
        boolean[] diag1 = new boolean[2*n - 1];
        boolean[] diag2 = new boolean[2*n - 1];

        backtrack(0, n, rows, diag1, diag2);

        return count;
    }

    public void backtrack(int col, int n, boolean[] rows, boolean[] diag1, boolean[] diag2) {
        if (col == n) {
            count++;
            return;
        }

        for (int row = 0; row < n; row++) {
            if (rows[row] || diag1[row + col] || diag2[n-1 + (col - row)])
                continue;

            rows[row] = true;
            diag1[row + col] = true;
            diag2[n-1 + (col - row)] = true;

            backtrack(col + 1, n, rows, diag1, diag2);

            rows[row] = false;
            diag1[row + col] = false;
            diag2[n-1 + (col - row)] = false;
        }
    }

    public static void main(String[] args) {
        int n = 8;
        System.out.println(new p12952().solution(n));
    }
}

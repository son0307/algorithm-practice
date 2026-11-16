package swea.p2805;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            int n = sc.nextInt();
            int[][] arr = new int[n][n];

            for (int i = 0; i < n; i++) {
                String row = sc.next();
                for (int j = 0; j < n; j++) {
                    arr[i][j] = row.charAt(j) - '0';
                }
            }

            int blank = -(n / 2);
            int sum = 0;

            for (int i = 0; i < n; i++) {
                int absBlank = Math.abs(blank);
                for (int j = absBlank; j < n - absBlank; j++) {
                    sum += arr[i][j];
                }
                blank++;
            }

            System.out.println("#" + tc + " " + sum);
        }
    }
}

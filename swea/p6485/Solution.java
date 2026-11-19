package swea.p6485;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            StringBuilder sb = new StringBuilder();
            int[] stops = new int[5001];
            int n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();

                for(int j = a; j <= b; j++)
                    stops[j]++;
            }

            int p = sc.nextInt();
            for(int i = 0; i < p; i++) {
                int c = sc.nextInt();
                sb.append(stops[c]).append(" ");
            }

            System.out.println("#" + tc + " " + sb);
        }
    }
}

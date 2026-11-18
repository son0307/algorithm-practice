package swea.p1946;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int n = sc.nextInt();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < n; i++) {
                String s = sc.next();
                int k = sc.nextInt();
                for(int j = 0; j < k; j++)
                    sb.append(s);
            }

            System.out.println("#" + tc);
            for(int i = 0; i < Math.ceil((double) sb.length() / 10); i++) {
                String line = sb.substring(10*i, Math.min(10*i+10, sb.length()));
                System.out.println(line);
            }
        }
    }
}

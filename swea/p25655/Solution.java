package swea.p25655;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int i = 0; i < T; i++) {
            int X = sc.nextInt();

            StringBuilder sb = new StringBuilder();
            if (X == 1) System.out.println("0");
            else if (X%2 == 0) {
                for(int j = 0; j < X/2; j++)
                    sb.append("8");
                System.out.println(sb);
            }
            else {
                sb.append("4");
                for(int j = 0; j < (X-1)/2; j++)
                    sb.append("8");
                System.out.println(sb);
            }
        }
    }
}

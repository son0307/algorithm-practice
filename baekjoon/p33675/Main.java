package baekjoon.p33675;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        long ans;

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for(int i=0; i<t; i++) {
            int n = sc.nextInt();
            if (n % 2 == 1) {
                ans = 0;
            } else {
                ans = (long) Math.pow(2, (n/2));
            }
            System.out.println(ans);
        }
    }
}

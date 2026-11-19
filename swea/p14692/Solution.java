package swea.p14692;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();

            String winner;
            if(N % 2 == 0) winner = "Alice";
            else winner = "Bob";

            System.out.println("#" + tc + " " + winner);
        }
    }
}

package swea.p1989;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            String s = sc.next();

            int mid = s.length() / 2;
            int valid = 1;
            for(int i = 0; i < mid; i++) {
                char c1 = s.charAt(i);
                char c2 = s.charAt(s.length() - 1 - i);
                if(c1 != c2) {
                    valid = 0;
                    break;
                }
            }

            System.out.println("#" + tc + " " + valid);
        }
    }
}

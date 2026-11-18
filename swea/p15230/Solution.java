package swea.p15230;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            String s = sc.next();

            int ans = 0;
            for(int i = 0; i < s.length(); i++) {
                if('a' + i == s.charAt(i)) {
                    ans++;
                } else {
                    break;
                }
            }

            System.out.println("#" + tc + " " + ans);
        }
    }
}

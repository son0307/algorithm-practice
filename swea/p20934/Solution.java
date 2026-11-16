package swea.p20934;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            String s = sc.next();
            int k = sc.nextInt();
            int ans = 0;

            if(s.equals("o..") || s.equals("..o")) {
                if(k == 0) {
                    ans = s.indexOf('o');
                }
                else {
                    if(k % 2 == 1) ans = 1;
                }
            }
            else {
                if(k % 2 == 0) ans = 1;
            }

            System.out.println("#" + tc + " " + ans);
        }
    }
}

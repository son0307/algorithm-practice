package swea.p7272;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] alphabets = new int[26];
        for(int i = 0; i < 26; i++) {
            if(i==0 || i==3 || i==14 || i==15 || i==16 || i == 17)
                alphabets[i] = 1;
            else if(i==1)
                alphabets[i] = 2;
            else
                alphabets[i] = 0;
        }

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            String s1 = sc.next();
            String s2 = sc.next();

            String ans = "SAME";
            if(s1.length() == s2.length()) {
                for(int i = 0; i < s1.length(); i++) {
                    char c1 = s1.charAt(i);
                    char c2 = s2.charAt(i);

                    if(alphabets[c1 - 'A'] != alphabets[c2 - 'A']) {
                        ans = "DIFF";
                        break;
                    }
                }
            } else {
                ans = "DIFF";
            }

            System.out.println("#" + tc + " " + ans);
        }
    }
}

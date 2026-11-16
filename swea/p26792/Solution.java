package swea.p26792;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for(int tc = 0; tc < t; tc++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            int a = (x + y) / 2;
            int b = (x - y) / 2;

            System.out.println(a + " " + b);
        }
    }
}

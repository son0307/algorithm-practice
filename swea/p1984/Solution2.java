package swea.p1984;

import java.util.*;

public class Solution2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int tc = 1; tc <= T; tc++) {
            int sum = 0;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for(int i = 0; i < 10; i++) {
                int value = sc.nextInt();
                sum += value;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }

            int answer = (int) Math.round((sum - min - max) / 8.0);

            System.out.println("#" + tc + " " + answer);
        }
    }
}

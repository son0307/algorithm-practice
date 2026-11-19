package swea.p5203;

import java.util.*;

public class Solution {
    public static boolean isWin(int[] cards) {
        for(int i = 0; i < 10; i++) {
            if(cards[i] >= 3)
                return true;

            if(i <= 7 && cards[i] >= 1 && cards[i+1] >= 1 && cards[i+2] >= 1)
                return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int[] p1 = new int[10];
            int[] p2 = new int[10];

            int winner = 0;

            for(int i = 0; i < 12; i++) {
                int num = sc.nextInt();

                if (winner != 0)
                    continue;

                if (i % 2 == 0) {
                    p1[num]++;

                    if(isWin(p1))
                        winner = 1;
                } else {
                    p2[num]++;

                    if(isWin(p2))
                        winner = 2;
                }
            }

            System.out.println("#" + tc + " " + winner);
        }
    }
}

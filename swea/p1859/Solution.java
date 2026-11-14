package swea.p1859;

import java.util.*;

public class Solution {
    static long getProfit(int days, int[] prices) {
        int max = prices[days-1];
        long profit = 0;

        for(int i = days-2; i >= 0; i--) {
            if(prices[i] > max) {
                max = prices[i];
                continue;
            }

            profit += max - prices[i];
        }

        return profit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        for(int i = 1; i <= n; i++) {
            int days = sc.nextInt();
            int[] prices = new int[days];
            for(int j = 0; j < days; j++) {
                prices[j] = sc.nextInt();
            }
            System.out.printf("#%d %d\n", i, getProfit(days, prices));
        }
    }
}

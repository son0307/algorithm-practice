package swea.p9229;

import java.util.*;

public class Solution2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int maxWeight = -1;

            int N = sc.nextInt();
            int M = sc.nextInt();
            int[] weights = new int[N];
            for(int i = 0; i < N; i++) {
                weights[i] = sc.nextInt();
            }

            Arrays.sort(weights);
            int p1 = 0;
            int p2 = weights.length - 1;
            while(p1 != p2) {
                int sum = weights[p1] + weights[p2];
                if(sum > M)
                    p2--;
                else {
                    p1++;
                    maxWeight = Math.max(maxWeight, sum);
                }
            }

            System.out.println("#" + tc + " " + maxWeight);
        }
    }
}
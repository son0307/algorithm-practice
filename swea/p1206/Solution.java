package swea.p1206;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for(int i = 1; i <= 10; i++) {
            int ans = 0;

            int n = sc.nextInt();
            int[] arr = new int[n];
            for(int j = 0; j < n; j++) {
                arr[j] = sc.nextInt();
            }

            for(int j = 0; j < n; j++) {
                if(arr[j] == 0) continue;
                int max = 0;

                for(int k = -2; k <= 2; k++) {
                    if(k == 0) continue;

                    max = Math.max(max, arr[j + k]);
                }

                if(arr[j] <= max) continue;

                ans += arr[j] - max;
            }

            System.out.printf("#%d %d\n", i, ans);
        }
    }
}

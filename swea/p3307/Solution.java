package swea.p3307;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            int[] nums = new int[N+1];
            int[] lens = new int[N+1];
            for(int i = 1; i <= N; i++) {
                nums[i] = sc.nextInt();
                lens[i] = 1;
            }

            int max = 1;
            for(int i = 1; i <= N; i++) {
                for(int j = 1; j < i; j++) {
                    if(nums[j] < nums[i]) {
                        lens[i] = Math.max(lens[i], lens[j] + 1);
                    }
                }
                max = Math.max(max, lens[i]);
            }

            System.out.println("#" + tc + " " + max);
        }
    }
}

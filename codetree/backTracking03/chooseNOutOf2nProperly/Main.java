package codetree.backTracking03.chooseNOutOf2nProperly;

import java.util.*;

public class Main {
    static int n;
    static int[] arr;
    static boolean[] selected;
    static int minDiff = Integer.MAX_VALUE;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        arr = new int[2*n];
        selected = new boolean[2*n];
        for (int i = 0; i < 2*n; i++) {
            arr[i] = sc.nextInt();
        }

        dfs(0, 0);

        System.out.println(minDiff);
    }

    static void dfs (int cnt, int idx) {
        if (cnt == n) {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 2*n; i++) {
                if (selected[i]) sum1 += arr[i];
                else sum2 += arr[i];
            }

            int diff = Math.abs(sum1 - sum2);
            minDiff = Math.min(diff, minDiff);
        }

        for (int i = idx; i < 2*n; i++) {
            selected[i] = true;
            dfs(cnt + 1, i + 1);
            selected[i] = false;
        }
    }
}

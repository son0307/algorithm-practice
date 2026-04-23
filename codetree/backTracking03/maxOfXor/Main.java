package codetree.backTracking03.maxOfXor;

import java.util.*;

public class Main {
    static int n, m;
    static int max = 0;
    static int[] nums;
    static List<Integer> selected = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();

        nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        dfs(0, 0);

        System.out.println(max);
    }

    static void dfs(int cnt, int idx) {
        if (cnt == m) {
            int cur = 0;
            for (int s : selected) {
                cur = cur ^ s;
            }
            max = Math.max(max, cur);
        }

        if ((n - idx) < (cnt - m) - 1) return;

        for (int i = idx; i < n; i++) {
            selected.add(nums[i]);
            dfs(cnt + 1, i + 1);
            selected.remove(selected.size() - 1);
        }
    }
}

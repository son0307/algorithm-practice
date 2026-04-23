package codetree.backTracking03.nChooseM;

import java.util.*;

public class Main {
    static int n, m;
    static List<Integer> selected = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        comb(0,1);
    }

    static void comb(int cnt, int cur) {
        if (cnt == m) {
            for (int s : selected) {
                System.out.print(s + " ");
            }
            System.out.println();
        }

        if ((n - cur) < (m - cnt) - 1) return;

        for (int i = cur; i <= n; i++) {
            selected.add(i);
            comb(cnt + 1,i + 1);
            selected.remove(selected.size() - 1);
        }
    }
}

package codetree.backTracking01.beautifulNum;

import java.util.*;

public class Main {
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        dfs(n, new StringBuilder());

        System.out.println(count);
    }

    static void dfs(int n, StringBuilder sb) {
        if (sb.length() == n) {
            count++;
            return;
        }
        else if (sb.length() > n) {
            return;
        }

        for (int i = 1; i <= 4; i++) {
            for (int j = 0; j < i; j++)
                sb.append(i);
            dfs(n, sb);
            for (int j = 0; j < i; j++)
                sb.deleteCharAt(sb.length() - 1);
        }
    }
}

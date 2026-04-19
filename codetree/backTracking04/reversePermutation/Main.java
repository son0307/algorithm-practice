package codetree.backTracking04.reversePermutation;

import java.util.*;

public class Main {
    static StringBuilder sb;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        visited = new boolean[n+1];
        sb = new StringBuilder();
        makePermutation(n);
    }

    static void makePermutation(int n) {
        if (sb.length() == n * 2) {
            System.out.println(sb.toString());
            return;
        }

        for (int i = n; i > 0; i--) {
            if (!visited[i]) {
                visited[i] = true;
                sb.append(i).append(" ");
                makePermutation(n);
                sb.delete(sb.length() - 2, sb.length());
                visited[i] = false;
            }
        }
    }
}

package baekjoon.p14888;

import java.io.*;
import java.util.*;

public class Main {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    public void dfs(int n, int[] A, int[] operators, int count, int res) {
        if (count == n) {
            max = Math.max(max, res);
            min = Math.min(min, res);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operators[i] > 0) {
                operators[i]--;
                switch (i) {
                    case 0: dfs(n, A, operators, count + 1, res + A[count]); break;
                    case 1: dfs(n, A, operators, count + 1, res - A[count]); break;
                    case 2: dfs(n, A, operators, count + 1, res * A[count]); break;
                    case 3: dfs(n, A, operators, count + 1, res / A[count]); break;
                }
                operators[i]++;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int[] operators = new int[4];
        for (int i = 0; i < 4; i++) {
            operators[i] = Integer.parseInt(st.nextToken());
        }

        Main m = new Main();
        m.dfs(n, A, operators, 1, A[0]);
        System.out.println(m.max);
        System.out.println(m.min);
    }
}

package swea.p1860;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int K = sc.nextInt();

            int[] customers = new int[N];
            for(int i = 0; i < N; i++) {
                customers[i] = sc.nextInt();
            }
            Arrays.sort(customers);

            boolean available = true;
            for(int i = 0; i < N; i++) {
                int time = customers[i];
                int made = (time / M) * K;

                if(made - (i + 1) < 0) {
                    available = false;
                    break;
                }
            }

            System.out.println("#" + tc + " " + (available ? "Possible" : "Impossible"));
        }
    }
}

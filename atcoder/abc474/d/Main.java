package atcoder.abc474.d;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] a = new int[n];
        int[] diff = new int[n];

        boolean hasPlus = false;

        for(int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        for(int i = 0; i < n; i++) {
            diff[i] = a[i] - sc.nextInt();

            if(diff[i] > 0)
                hasPlus = true;
        }

        if(!hasPlus) {
            System.out.println("No");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            if(diff[i] <= 0) sb.append(1);
            else if (diff[i] > 0) sb.append((long) Math.pow(10, 18));

            sb.append(" ");
        }

        System.out.println("Yes");
        System.out.println(sb);
    }
}

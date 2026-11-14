package swea.p1208;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for(int tc = 1; tc <= 10; tc++) {
            int n = sc.nextInt();
            Integer[] arr = new Integer[100];

            for(int i = 0; i < 100; i++)
                arr[i] = sc.nextInt();

            Arrays.sort(arr);
            for(int i = 0; i < n; i++) {
                arr[0]++;
                arr[99]--;
                Arrays.sort(arr);

                if(arr[99] - arr[0] <= 1)
                    break;
            }

            System.out.println("#" + tc + " " + (arr[99] - arr[0]));
        }
    }
}

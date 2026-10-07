package codetree.p2025_2m1.Jenga1d;

import java.util.*;

public class Main {
    static final int MAX_N = 100;

    static int n;
    static int[] numbers = new int[MAX_N];

    static int end;

    static void cutArray(int s, int e) {
        int[] temp = new int[MAX_N];
        int tempEnd = 0;

        for(int i = 0; i < end; i++) {
            if (i < s || i > e)
                temp[tempEnd++] = numbers[i];
        }

        for(int i = 0; i < tempEnd; i++) {
            numbers[i] = temp[i];
        }

        end = tempEnd;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        end = n;
        for(int i = 0; i < n; i++) {
            numbers[i] = sc.nextInt();
        }

        int s1 = sc.nextInt() - 1, e1 = sc.nextInt() - 1;
        int s2 = sc.nextInt() - 1, e2 = sc.nextInt() - 1;

        cutArray(s1,e1);
        cutArray(s2,e2);

        System.out.println(end);
        for(int i = 0; i < end; i++)
            System.out.println(numbers[i]);
    }
}

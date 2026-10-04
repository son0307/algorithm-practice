package codetree.p2024a1.MoveInDirection;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] ans = {0, 0};
        for(int i = 0; i < n; i++) {
            char direction = sc.next().charAt(0);
            int distance = sc.nextInt();

            switch(direction) {
                case 'E':
                    ans[0] += distance;
                    break;
                case 'W':
                    ans[0] -= distance;
                    break;
                case 'S':
                    ans[1] -= distance;
                    break;
                case 'N':
                    ans[1] += distance;
                    break;
            }
        }

        System.out.println(ans[0] + " " + ans[1]);
    }
}

package swea.p1926;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            StringBuilder sb369 = new StringBuilder();
            StringBuilder sb = new StringBuilder();

            int nums = i;
            while(nums != 0) {
                int num = nums % 10;

                if(num == 3 || num == 6 || num == 9)
                    sb369.insert(0, "-");
                else
                    sb.insert(0, num);

                nums /= 10;
            }

            if(sb369.isEmpty())
                System.out.print(sb + " ");
            else
                System.out.print(sb369 + " ");
        }
    }
}

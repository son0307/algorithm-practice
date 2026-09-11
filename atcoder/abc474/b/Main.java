package atcoder.abc474.b;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        String[] s = sc.nextLine().split(" ");
        for(int i = 0; i < n; i++) {
            int num = Integer.parseInt(s[i]);
            int group = i / 10;
            if(num/10 != group && num != (group+1) * 10) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }
}

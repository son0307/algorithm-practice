package codetree.p2024m1.The1DBombGame;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer> bombs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            bombs.add(sc.nextInt());
        }

        while (true) {
            List<Integer> next = new ArrayList<>();

            boolean exploded = false;

            int i = 0;

            while (i < bombs.size()) {
                int j = i + 1;

                // 같은 숫자가 어디까지 연속되는지 확인
                while (j < bombs.size()
                        && bombs.get(i).equals(bombs.get(j))) {
                    j++;
                }

                int cnt = j - i;

                if (cnt >= m) {
                    exploded = true;
                } else {
                    for (int k = i; k < j; k++) {
                        next.add(bombs.get(k));
                    }
                }

                i = j;
            }

            if (!exploded) {
                break;
            }

            bombs = next;
        }

        System.out.println(bombs.size());

        for (int bomb : bombs) {
            System.out.println(bomb);
        }
    }
}

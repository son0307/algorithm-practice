package atcoder.abc474.c;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();

        HashMap<Integer, Integer> order = new HashMap<>();
        for(int i = 0; i < n; i++) {
            order.put(sc.nextInt(), i);
        }

        for(int i = 0; i < q; i++) {
            int a = sc.nextInt();
            order.put(a, n);
            n++;
        }

        Integer[] keys = order.keySet().toArray(new Integer[0]);
        Arrays.sort(keys, (k1, k2) -> order.get(k1) - order.get(k2));
        StringBuilder sb = new StringBuilder();
        for(Integer k : keys) {
            sb.append(k);
            sb.append(" ");
        }
        System.out.println(sb);
    }
}

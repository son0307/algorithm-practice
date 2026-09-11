package atcoder.abc474.e;

import java.util.*;

public class Main {
    static class Price {
        int before;
        int after;
        int discount;

        public Price(int before, int after) {
            this.before = before;
            this.after = after;
            this.discount = before - after;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long[] answers = new long[n];

        for (int i = 0; i < n; i++) {
            int minPrice = Integer.MAX_VALUE;
            int t = sc.nextInt();

            List<Price> prices = new ArrayList<>();

            for (int k = 0; k < t; k++) {
                int before = sc.nextInt();
                int after = sc.nextInt();

                minPrice = Math.min(minPrice, before);
                prices.add(new Price(before, after));
            }

            prices.sort((p1, p2) ->
                    Integer.compare(p2.discount, p1.discount));

            answers[i] = getAnswer(t, prices, minPrice);
        }

        for (long a : answers) {
            System.out.println(a);
        }
    }

    private static long getAnswer(
            int t,
            List<Price> prices,
            int minPrice
    ) {
        long beforeTotal = 0;

        for (Price p : prices) {
            beforeTotal += p.before;
        }

        long min = beforeTotal;
        long discountTotal = 0;

        for (int i = 0; i < t; i++) {
            discountTotal += prices.get(i).discount;

            int k = i + 1;

            long additionalCoupon = Math.max(0, 2L * k - t);

            long totalPrice = beforeTotal - discountTotal + additionalCoupon * minPrice;

            min = Math.min(min, totalPrice);
        }

        return min;
    }
}
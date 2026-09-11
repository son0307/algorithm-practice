## 문제 요약

N개의 상품이 존재하고, 각 상품은 다음 두 가지 방식으로 구매할 수 있다.

- 정가 `A[i]`로 구매하고 쿠폰 1개 획득
- 쿠폰 1개를 사용하여 할인 가격 `B[i]`로 구매

처음에는 쿠폰이 하나도 없으며, 모든 상품을 최소 한 번씩 구매해야 한다.

상품은 여러 번 구매할 수 있으므로 필요한 경우 특정 상품을 추가로 구매하여 쿠폰을 얻을 수도 있다.

모든 상품을 최소 한 번씩 구매하기 위한 최소 비용을 구하는 문제이다.

## 핵심 포인트

이 문제는 단순히 할인율이 높은 상품부터 쿠폰을 사용하는 것이 아니라, 쿠폰을 총 몇 개 사용할 것인지 먼저 정한 뒤 그 경우의 최소 비용을 계산하는 것이 핵심이다.

모든 상품을 정가로 구매했을 때의 기본 비용은 `ΣA[i]`이다.

상품 `i`에 쿠폰을 사용하면 `A[i] - B[i]`만큼 비용을 절약할 수 있다.

따라서 쿠폰을 `k`개 사용한다면 `A[i] - B[i]`가 가장 큰 상품 `k`개에 쿠폰을 사용하는 것이 가장 이득이다.

이제 쿠폰 수를 생각해야 한다.

쿠폰을 `k`개 사용하면 할인 구매하는 상품은 `k`개이고, 나머지 `N - k`개 상품은 정가로 구매한다.

정가로 구매한 상품 하나당 쿠폰 하나를 얻으므로 획득하는 쿠폰은 `N - k`개이다.

따라서 추가 구매 없이 쿠폰을 모두 충당하려면

`N - k >= k`

이어야 한다.

즉,

`k <= N / 2`

라면 추가 구매가 필요 없다.

반대로 `k > N / 2`라면 쿠폰이 부족하고, 부족한 쿠폰 수는

`k - (N - k) = 2 * k - N`

개이다.

쿠폰 하나를 추가로 얻기 위해서는 어떤 상품이든 정가로 한 번 더 구매해야 하므로, 가장 싼 정가 상품 `min(A[i])`를 반복 구매하는 것이 최적이다.

따라서 쿠폰을 `k`개 사용할 때의 비용은 다음과 같다.

`ΣA[i] - 할인액 상위 k개의 합 + max(0, 2 * k - N) * min(A[i])`

결국 `k = 0 ~ N`을 모두 확인해서 최솟값을 찾으면 된다.

## 동작 원리

각 상품마다 쿠폰을 사용했을 때 절약할 수 있는 금액을 계산한다.

```text
discount[i] = A[i] - B[i]
```

이후 `discount`를 내림차순으로 정렬한다.

예를 들어 할인액이 다음과 같다고 하자.

```text
5 5 3 1 1
```

쿠폰을 3개 사용한다면 가장 큰 세 값인 `5, 5, 3`에 해당하는 상품에 쿠폰을 사용하는 것이 가장 이득이다.

모든 상품을 정가로 구매했을 때의 비용을 `beforeTotal`이라고 하면

```text
beforeTotal = ΣA[i]
```

이다.

이제 할인액을 큰 순서대로 하나씩 누적하면서 쿠폰 사용 개수 `k`를 증가시킨다.

쿠폰을 `k`개 사용할 때의 총 비용은

```text
beforeTotal
- discountTotal
+ max(0, 2 * k - N) * minPrice
```

로 계산할 수 있다.

여기서

- `discountTotal` : 현재까지 쿠폰을 사용하는 상품들의 할인액 합
- `max(0, 2 * k - N)` : 추가로 필요한 쿠폰 수
- `minPrice` : 쿠폰을 추가로 얻기 위해 반복 구매할 가장 싼 상품의 정가

이다.

예를 들어 상품이 5개이고 쿠폰을 3개 사용한다면

```text
정가 구매 상품 수 = 2
획득하는 쿠폰 수 = 2
필요한 쿠폰 수 = 3
```

이므로 쿠폰 1개가 부족하다.

이는

```text
2 * 3 - 5 = 1
```

로 계산할 수 있다.

이 방식으로 모든 `k`에 대해 비용을 계산하고 최솟값을 갱신하면 된다.

## 전체 코드

```java
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

        int T = sc.nextInt();
        long[] answers = new long[T];

        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();

            int minPrice = Integer.MAX_VALUE;
            List<Price> prices = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int before = sc.nextInt();
                int after = sc.nextInt();

                minPrice = Math.min(minPrice, before);
                prices.add(new Price(before, after));
            }

            prices.sort(
                    (p1, p2) -> Integer.compare(p2.discount, p1.discount)
            );

            answers[i] = getAnswer(n, prices, minPrice);
        }

        for (long answer : answers) {
            System.out.println(answer);
        }
    }

    private static long getAnswer(
            int n,
            List<Price> prices,
            int minPrice
    ) {
        long beforeTotal = 0;

        for (Price p : prices) {
            beforeTotal += p.before;
        }

        long answer = beforeTotal;
        long discountTotal = 0;

        for (int i = 0; i < n; i++) {
            discountTotal += prices.get(i).discount;

            int k = i + 1;

            long extraCoupon =
                    Math.max(0L, 2L * k - n);

            long totalPrice =
                    beforeTotal
                    - discountTotal
                    + extraCoupon * minPrice;

            answer = Math.min(answer, totalPrice);
        }

        return answer;
    }
}
```

## 시간 복잡도

각 상품의 할인액을 계산하는 데 `O(N)`이 필요하다.

이후 할인액을 기준으로 정렬하므로 `O(N log N)`이 필요하다.

정렬 이후에는 한 번 순회하면서 각 쿠폰 사용 개수 `k`에 대한 비용을 계산하므로 `O(N)`이 필요하다.

따라서 전체 시간 복잡도는 `O(N log N)`이다.
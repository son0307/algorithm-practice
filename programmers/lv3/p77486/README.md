## 문제 요약

문제를 요약하여 핵심만 추려보자.

- 칫솔을 판매하여 이익이 발생하면, 판매자는 90%를 가지고 나머지 10%를 자신의 추천인에게 전달한다.
- 10%를 전달받은 추천인도 마찬가지로 그 금액의 90%를 갖고 10%를 자신의 추천인에게 전달한다. 이 과정은 center에 도달할 때까지 반복된다.
- 다만, 10% 계산 시 원 단위에서 절사하며, 10%가 1원 미만이면 전파를 중단하고 자신이 모두 가진다.
- 최종 목표는 전달된 enroll 배열 순서대로 각 판매원의 총 이익을 계산하여 배열로 반환하는 것이다.

---

## 핵심 포인트

이 문제에서는 판매원이 칫솔을 판매하여 이익이 발생하면 해당 이익의 10%를 자신의 추천인에게 전달하는 것이 반복적으로 나타난다. 이를 통해 재귀를 이용하여 문제를 해결할 수 있음을 알 수 있다. 예시를 들어보며 재귀 함수를 우선 구성해보도록 하자.

![image.png](attachment:6fa0bd51-9a12-459e-955e-2b32a38ec649:image.png)

이 경우는 ‘young’ 판매원이 칫솔 12개를 판매한 경우이다. 해당 판매원이 벌어들인 이익은 1,200원이고 여기서부터 10%씩 추천인에게 전달하는 로직이 작동된다. 각 직원이 가질 금액을 계산하면 다음과 같다.

- young → 전달할 금액 : 1200 * 0.1 = 120, 자기가 가질 금액 : 1200 - 120 = 1080
- edward → 전달할 금액 : 120 * 0.1 = 12, 자기가 가질 금액 : 120 - 12 = 108
- mary → 전달할 금액 : 12 * 0.1 = 1, 자기가 가질 금액 : 12 - 1 = 11

이를 재귀함수로 나타내면 다음과 같이 나타낼 수 있다.

```java
public void distribute(String seller, int price) {
    if (price == 0)
        return;

    int tax = (int) (price * 0.1);
    int profit = profitMap.getOrDefault(seller, 0) + (price - tax);

    profitMap.put(seller, profit);
    
    if (!referralMap.get(seller).equals("-"))
        distribute(referralMap.get(seller), tax);
}
```

- 전달된 금액이 0이면 더 전파할 금액이 없는 것이므로 종료
- tax는 전달할 금액, profit은 자기가 가질 금액을 계산하여 각 직원들의 이익금에 추가한다.
- 이후, 부모 노드가 루트 노드가 아닌 경우 재귀 함수를 호출하여 전파를 이어나간다. (현재 문제에서는 루트 노드를 고려하지 않기 때문)

전달된 enroll 배열과 referral 배열을 통해 판매원 - 추천인으로 자료를 정리한 다음 전달된 판매량을 하나씩 순회하며 위 재귀함수를 호출하면 각 판매에 대해 계산이 이루어지게 된다.

```java
Map<String, String> referralMap = new HashMap<>();
Map<String, Integer> profitMap = new HashMap<>();

public Integer[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

    for (int i = 0; i < enroll.length; i++) {
        referralMap.put(enroll[i], referral[i]);
    }

    for (int i = 0; i < seller.length; i++) {
        distribute(seller[i], amount[i] * 100);
    }

    List<Integer> answer = Arrays.stream(enroll)
            .map(e -> profitMap.getOrDefault(e, 0))
            .collect(Collectors.toList());

    return answer.toArray(new Integer[0]);
}
```

- 결과를 반환하기 전에 이익금의 총합들을 enroll 순서에 맞춰 반환해야 하므로 전달된 enroll 배열을 바탕으로 정렬하였다.

### 전체 코드

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
    HashMap<String, String> referralMap = new HashMap<>();
    Map<String, Integer> profitMap = new HashMap<>();

    public Integer[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

        for (int i = 0; i < enroll.length; i++) {
            referralMap.put(enroll[i], referral[i]);
        }

        for (int i = 0; i < seller.length; i++) {
            distribute(seller[i], amount[i] * 100);
        }

        List<Integer> answer = Arrays.stream(enroll)
                .map(e -> profitMap.getOrDefault(e, 0))
                .collect(Collectors.toList());

        return answer.toArray(new Integer[0]);
    }

    public void distribute(String seller, int price) {
        if (price == 0)
            return;

        int tax = (int) (price * 0.1);
        int profit = profitMap.getOrDefault(seller, 0) + (price - tax);

        profitMap.put(seller, profit);

        if (!referralMap.get(seller).equals("-"))
            distribute(referralMap.get(seller), tax);
    }
}
```
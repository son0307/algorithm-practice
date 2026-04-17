## 문제 요약

제한 시간 `limit` 내에 $n$개의 퍼즐을 모두 해결하기 위한 숙련도의 최솟값을 구하는 문제

- **조건**
    - $diff \le level$:  $time\_cur$ 만큼 소요
    - $diff > level$: $(diff-level)\times(time\_cur+time\_prev)+time\_cur$만큼 시간 소요
- **제한 사항**: 퍼즐 개수 300,000개, 제한 시간 $10^{15}$, 최대 난이도 100,000

## 핵심 포인트

이 문제는 숙련도가 커질수록 전체 소요 시간이 감소하는 특성을 이용하여 최적의 값을 찾아내야 하는 문제이다

1. **파라메트릭 서치 :** 가능한 숙련도의 범위(1 ~ 100,000) 내에서 이진 탐색을 수행하여 `limit` 을 만족하는 최소 숙련도를 찾아낸다.
2. **시간 복잡도** : 퍼즐의 개수가 300,000개로, 단순 시뮬레이션을 통해서는 시간 내에 해결할 수 없다. 따라서, 이진 탐색을 이용하는 것이 필수이다.
3. **데이터 타입 주의 :** 제한 시간 `limit` 이 최대 $10^{15}$이므로, 총 소요 시간을 계산하는 변수는 `long` 타입을 사용해 오버플로우를 방지해야 한다.

### 동작 원리

1. **범위 설정 :** 숙련도의 최솟값 `min = 1` 과 최대 난이도 `max = 100,000` 으로 탐색 범위를 설정한다.
2. **중간값 검증** : `level = (min + max) / 2` 일 때, 모든 퍼즐을 해결하는데 소요되는 총 시간을 계산한다.
3. **범위 축소**
    1. 계산된 시간이 `limit` 보다 크면 : 숙련도가 부족한 것이므로 `min = level + 1` 로 높인다.
    2. 계산된 시간이 `limit` 보다 작거나 같으면 : 현재 숙련도로 가능한 것이므로, 더 작은 최솟값이 있는지 확인하기 위해 `max = level` 로 낮춘다.
4. **최종 수렴** : `min` 과 `max` 가 만나는 지점의 값이 제한 시간을 만족하는 최소 숙련도이므로 이 값을 반환한다.

### 전체 코드

```java
class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int min = 1;
        int max = 100000;

        while(min < max) {
            int level = (min + max) / 2;
            long time = times[0];
            for (int i = 1; i < diffs.length; i++) {
                if (diffs[i] > level) {
                    long wrongTimes = diffs[i] - level;
                    time += wrongTimes * (times[i - 1] + times[i]);
                }
                time += times[i];
            }

            if (time > limit)
                min = level + 1;
            else
                max = level;
        }
        
        return min;
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $M$(최대 난이도 100,000)에 대한 이진 탐색 횟수만큼 배열 전체($N$ = 300,000)를 순횐한다.

따라서, 위 알고리즘의 최종 시간 복잡도는 $O(N\log{M})$이다.
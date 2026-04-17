### 문제 요약

- 주어진 보석들 중에서 모든 보석 종류를 포함하는 가장 짧은 범위를 구하는 문제
    - 범위는 중간에 끊어질 수 없으면 연속적으로 이어져야 함.
    - 동일한 길이의 범위가 있으면 시작 숫자가 빠른 것이 더 우선

## 핵심 포인트

이 문제는 조건을 만족하는 범위를 찾아내야 한다는 점에서 투 포인터 방식의 완전 탐색을 이용해야 한다는 것을 알 수 있다. 다만, 범위를 줄여나가는 규칙을 제대로 정의하지 못하면 정답을 도출해낼 수 없다.

투 포인터를 이용하여 완전 탐색을 수행할 때, 범위를 줄여나가는 방식은 2가지가 있다.

- 처음과 마지막 위치에 포인터를 두고, 조건에 따라 증가 혹은 감소시키며 범위를 축소시키는 방법
- 두 포인터를 모두 처음 위치에 두고, 증가 조건을 다르게 설정하여 처음부터 끝까지 탐색하는 방법

첫 번째 방법의 경우, 둘 중 어느 포인터를 움직일 지가 확실히 정해져야 하는데 이번 문제의 경우 이를 확실히 정할 수 없다. (게다가 이 방법은 주로 정렬된 배열에서 수의 합을 찾거나 할 때 사용된다.) 따라서, 두 번째 방법을 사용해야 한다.

이제 두 개의 포인터가 있을 때, 각 포인터의 증가 조건을 설정해야 한다. 문제의 목표는 모든 종류를 포함하고 있는 최소의 범위를 찾는 것이므로, **모든 종류를 포함하고 있을 때까지 범위를 늘리고, 조건을 만족하지 못할 떄까지 범위를 줄인다.** 이후, 현재까지 찾은 범위의 최소 길이와 비교하여 더 작은 경우 갱신해나가면 된다. 따라서, 각 포인터의 증가 조건은 다음과 같이 정의할 수 있다.

- 포인터 1 : 현재 범위가 모든 종류를 포함하지 않는 경우 증가시켜 다음 보석을 범위에 포함시킴 (범위 증가)
- 포인터 2 : 현재 범위가 모든 종류를 포함하는 경우 증가시켜 가장 끝에 있는 보석을 범위에서 제외시킴 (범위 축소)

이와 같은 규칙을 바탕으로 모든 보석을 검사하면 모든 종류의 보석을 포함하는 가장 작은 범위를 찾아낼 수 있다.

### 전체 코드

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class Solution {
    public int[] solution(String[] gems) {
        HashMap<String, Integer> gemsMap = new HashMap<>();
        HashSet<String> gemTypes = new HashSet<>(Arrays.asList(gems));

        int length = Integer.MAX_VALUE;
        int start = 0;

        int pt1 = 0;
        int pt2 = 0;

        while (true) {
		        // 모든 보석 종류를 포함하는 경우 최소 범위를 갱신하고 pt1을 증가시켜 범위를 축소
            if (gemsMap.size() == gemTypes.size()) {
                if (pt2 - pt1 < length) {
                    length = pt2 - pt1;
                    start = pt1;
                }

                gemsMap.put(gems[pt1], gemsMap.get(gems[pt1]) - 1);
                if (gemsMap.get(gems[pt1]) == 0)
                    gemsMap.remove(gems[pt1]);

                pt1++;
            }
            // 위 조건을 만족하지 않으면서 pt2가 범위를 벗어나면 더 이상 조건을 만족할 수 없으므로 종료.
            else if (pt2 == gems.length) {
                break;
            }
            // 모든 보석 종류를 포함하고 있지 않으면 pt2를 증가시켜 범위를 확대
            else {
                gemsMap.put(gems[pt2], gemsMap.getOrDefault(gems[pt2], 0) + 1);
                pt2++;
            }
        }

        return new int[]{start + 1, start + length};
    }
}
```

위 알고리즘의 시간복잡도는 O(N)이다. 배열을 처음부터 끝까지 포인터가 이동하며 딱 1번만 검사하기에 O(N)의 시간만 소요된다.
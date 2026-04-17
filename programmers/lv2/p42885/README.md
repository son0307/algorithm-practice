### 문제 요약

- 무게 제한이 있고, 최대 2명이 탈수 있는 구명 보트를 최대한 적게 이용하여 사람들을 태울 때, 구명보트의 수를 구하는 문제

## 핵심 포인트

가장 적게 구명 보트를 이용하려면 하나의 보트에 2명씩 태우는 것이 가장 바람직하다. 즉, 가장 무거운 사람과 가장 가벼운 사람을 같이 태워 무게 제한에 최대한 맞추는 것이 가장 적절한 방법이다.

이를 위해, 가장 먼저 해야하는 건 배열을 정렬하는 것이다. people을 오름차순으로 정렬한다.

```groovy
Arrays.sort(people);
```

정렬된 리스트에서 가장 가벼운 사람을 나타내는 포인터 point1과  가장 무거운 사람을 나타내는 포인터 point2를 만들고, 아래 규칙에 맞춰 탐색을 진행한다.

- 가장 무거운 사람과 가장 가벼운 사람의 무게의 합이 무게 제한을 넘지 않으면 두 사람 모두 태우고 point1++, point2--를 수행하여 다음 사람들을 보게 한다.
- 두 사람 모두 태울 수 없으면 가장 무거운 사람만 태운다. 이후, point2--를 수행한다.
- 위 과정을 point1이 point2를 지나칠 때까지 위 과정을 반복한다.

위 반복을 수행한 만큼 보트를 사용한 것이므로, 반복 횟수를 반환하도록 하면 된다.

### 전체 코드

```groovy
import java.util.Arrays;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;

        Arrays.sort(people);
        int point1 = 0;
        int point2 = people.length - 1;
        while (point1 <= point2) {
            if (people[point1] + people[point2] <= limit) {
                point1++;
            }
            point2--;
            answer++;
        }

        return answer;
    }
}
```
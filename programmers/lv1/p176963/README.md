### 문제 요약

- 사진 속 인물들이 주어지면 해당 인물들에 대한 추억 점수의 합을 계산하는 문제
    - 추억 점수가 없는 인원에 대해서는 0점을 부여한다.

## 핵심 포인트

이 문제는 추억 점수에 주어진 인물들과 점수를 빠르게 찾아내는 것이 중요하다. 특정 인물의 추억 점수를 찾을 때 단순히 배열을 순회하며 찾는 것은 매우 비효율적인 방법으로, 최악의 경우 yearning 배열을 반복해서 끝까지 순회할 수 있다. 따라서, 여기서는 이름을 Key로 삼고, 추억 점수를 Value로 저장하는 Map 자료구조를 사용해야한다.

name 배열과 yearning 배열을 순회하며 Map에 하나씩 저장해두면 이후에 특정 인물의 추억 점수를 찾을 때 O(1)의 시간으로 매우 빠르게 찾아낼 수 있다. (해시 테이블에 접근하여 실제 주소를 알아내는데 시간이 걸리기는 하지만 이는 매우 데이터가 많을 경우에만 영향을 준다.)

```java
HashMap<String, Integer> score = new HashMap<>();
for (int i = 0; i < name.length; i++)
    score.put(name[i], yearning[i]);
```

인물과 추억 점수를 모두 저장하였으면, photo 배열의 원소들을 하나씩 살펴보며 추억 점수의 총합을 계산해야 한다. 이때 사용되는 것이 HashMap.getOrDefault() 메서드로 해당 메서드는 주어진 Key에 할당된 Value가 있으면 Value를 반환하고 없으면 설정한 기본값을 반환한다. 이를 통해 추억 점수가 없는 대상인 경우 0을 반환하도록 할 수 있다.

```java
for (int i = 0; i < photo.length; i++) {
    String[] p = photo[i];
    int sum = 0;
    for (String s : p)
        sum += score.getOrDefault(s, 0);
    answer[i] = sum;
}
```

### 전체 코드

```java
import java.util.*;

class Solution {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = new int[photo.length];

        HashMap<String, Integer> score = new HashMap<>();
        for (int i = 0; i < name.length; i++)
            score.put(name[i], yearning[i]);

        for (int i = 0; i < photo.length; i++) {
            String[] p = photo[i];
            int sum = 0;
            for (String s : p)
                sum += score.getOrDefault(s, 0);
            answer[i] = sum;
        }

        return answer;
    }
}
```

이 풀이의 시간 복잡도는 O(N + M)이다. 정확히 분석하면 다음과 같다.

- 주어진 인물들과 추억 점수를 바탕으로 HashMap 생성 : O(N)
    - N : `name` 배열의 길이
- photo 배열을 순회하며 추억 점수의 총합 계산 : O(M)
    - M : `photo` 2차원 배열에 있는 모든 인물들의 수
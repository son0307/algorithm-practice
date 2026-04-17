### 문제 요약

- 0 ~ 9 사이의 숫자들이 주어졌을 때 이 중 리스트에 없는 숫자들의 합을 반환

## 핵심 포인트

입력된 숫자들을 Set에 담아두고 0 ~ 9까지 순회하며 Set에 담겨있지 않은 숫자만 골라 합을 구하면 된다.

### 코드

```java
import java.util.HashSet;

class Solution {
    public int solution(int[] numbers) {
        int answer = 0;
        HashSet<Integer> numbersSet = new HashSet<>();
        for (int number : numbers)
            numbersSet.add(number);

        for (int i = 0; i <= 9; i++) {
            if (!numbersSet.contains(i))
                answer += i;
        }

        return answer;
    }
}
```

이 문제는 수학을 이용하면 훨씬 더 간단하게 풀 수 있다. 0 ~ 9의 총합은 45이므로 처음부터 answer을 45로 두고 전달된 리스트에 담긴 숫자들을 하나씩 빼는 방식으로도 해결할 수 있다.

### 코드2

```java
class Solution {
    public int solution(int[] numbers) {
        int answer = 45;
        for (int number : numbers) {
            answer -= number;
        }

        return answer;
    }
}
```
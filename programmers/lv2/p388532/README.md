### 문제 요약

- 1 ~ n 까지의 숫자 중 5개로 이루어진 비밀코드의 경우의 수를 찾아내는 문제
    - 입력한 정수 리스트를 바탕으로 가능한 모든 비밀 코드의 수를 알아내야 한다.
    - 5자리의 비밀 코드는 중복된 숫자를 가지지 않는다.

## 핵심 포인트

이 문제는 문제 설명만 봤을 때는 입력한 정수들과 일치하는 개수를 바탕으로 가능한 조합들을 찾아내고 검산하는 방식으로 문제를 해결해나가야 하는 것처럼 보인다. 하지만, 제한 사항을 보면 입력되는 경우의 수가 적고, 검사 횟수도 비교적 적은 편임을 알 수 있다. 따라서, 브루트 포스 방식으로 문제를 해결해볼 수 있음을 짐작할 수 있다.

그렇다면, 브루트 포스 방식으로 문제를 해결할 때의 최대 연산 횟수가 얼마인지 계산해보도록 하자.

- n개의 숫자 중 5개의 숫자를 고르는 경우
    - n의 최댓값이 30이므로 약 30^5 / 120 = 14만
- 5개의 숫자로 만든 비밀코드가 주어진 일치 개수와 동일한지 검사하는 횟수
    - m의 최댓값이 10이므로 비교 연산 횟수는 10 * 5 = 50
- 총 연산 횟수 = (후보 개수) x (후보 검증) = 14만 * 50 = 약 700만

전체 연산 횟수는 약 700만으로, 제한 시간 내에 충분히 문제를 해결할 수 있다. 그러므로, 완전 탐색 방식으로 접근해야 한다.

그러면 먼저 주어진 n개의 숫자 중 5개를 골라 조합을 만드는 함수를 먼저 만들어보자. 순열이 아닌 조합이기에 visited 배열을 이용하는 등 별도 처리를 통해 중복된 숫자가 선택되지 않도록 유의해야 한다.

```java
void combination(int start, int n, int count, List<Integer> current, int[][] q, int[] ans) {
    if (count == 5) {
        if (check(current, q, ans)) answer++;
        return;
    }

    for (int i = start; i <= n; i++) {
        current.add(i);
        combination(i + 1, n, count + 1, current, q, ans);
        current.remove(current.size() - 1);
    }
}
```

- start : 몇 번째 숫자부터 후보로 삼을 것인지를 결정한다. 이를 통해, 이전에 검사한 숫자들은 후보에서 아예 제외시켜 중복된 숫자가 포함되지 않도록 한다.
- count : 현재까지 몇 개의 숫자를 집합에 포함시켰는지를 나타낸다.
- current : 집합에 포함된 숫자들을 저장한다.

이 함수를 통해 n개의 숫자 중 5개의 숫자를 선택하여 비밀 코드를 생성한다. 이후, 완성된 비밀코드가 주어진 m번의 시도와 동일한 값을 가지는지 검사해야 한다. 다음 함수가 그 역할을 수행한다.

```java
boolean check(List<Integer> current, int[][] q, int[] ans) {
    for (int i = 0; i < q.length; i++) {
        int matchCount = 0;
        for (int j : q[i]) {
            if (current.contains(j)) matchCount++;
        }
        if (matchCount != ans[i]) return false;
    }
    return true;
}
```

각 시도의 입력한 정수 배열을 순회하며 완성된 비밀 코드와 몇 개가 일치하는지를 검사한다. 일치한 개수가 예제로 주어진 개수와 하나라도 다르면 현재 예제에서는 불가능한 비밀 코드이므로 false를 반환하고, 전부 일치하였으면 가능한 비밀 코드이므로 true를 반환한다.

### 전체 코드

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    int answer = 0;

    void combination(int start, int n, int count, List<Integer> current, int[][] q, int[] ans) {
        if (count == 5) {
            if (check(current, q, ans)) answer++;
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            combination(i + 1, n, count + 1, current, q, ans);
            current.remove(current.size() - 1);
        }
    }

    boolean check(List<Integer> current, int[][] q, int[] ans) {
        for (int i = 0; i < q.length; i++) {
            int matchCount = 0;
            for (int j : q[i]) {
                if (current.contains(j)) matchCount++;
            }
            if (matchCount != ans[i]) return false;
        }
        return true;
    }

    public int solution(int n, int[][] q, int[] ans) {
        combination(1, n, 0, new ArrayList<>(), q, ans);
        return answer;
    }
}
```
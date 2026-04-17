## 문제 요약

- 주어진 정수들을 더하거나 빼서 타켓 넘버를 만들 수 있는 경우의 수를 반환
    - 정수들의 순서는 주어진 순서 그대로 이용

## 핵심 포인트

이 문제는 각 숫자마다 더하거나 빼는 선택지를 가지는 이진 트리 형태의 탐색 문제이다. 예를 들어, [4, 1, 2]과 target = 1이 주어졌을 때 다음과 같이 나타나게 된다.

![image.png](attachment:884e1c21-13f6-4d6e-8713-ded33347b04b:image.png)

N개의 숫자에 대해 모든 경우의 수를 탐색해야 하기에 DFS(깊이 우선 탐색) 혹은 BFS를 이용할 수 있다. 이번 문제에서는 DFS를 이용하여 문제를 해결하였다.

DFS를 이용하기 위해 재귀 분기와 중단 조건을 정의하면 다음과 같다.

- 재귀 분기
    - 현재 인덱스의 숫자를 더하는 경우
    - 현재 인덱스의 숫자를 빼는 경우
- 중단 조건
    - 모든 숫자에 대해 계산을 완료했을 때 재귀를 멈춘다.
    - 멈출 때까지의 누적합을 타겟 넘버와 비교하여 일치하면 정답  카운트를 1 증가시킨다.

### 전체 코드

```java
class Solution {
    private int answer = 0;

    private void dfs(int result, int idx, int[] numbers, int target) {
        if (idx == numbers.length) {
            if (result == target)
                answer++;
            return;
        }

        dfs(result + numbers[idx], idx + 1, numbers, target);
        dfs(result - numbers[idx], idx + 1, numbers, target);
    }

    public int solution(int[] numbers, int target) {
        dfs(0, 0, numbers, target);
        return answer;
    }
}
```

이 코드의 시간 복잡도는 $O(N^2)$이다. 각 숫자마다 더하기 또는 빼기 2가지 경우의 수가 존재하므로, 리프 노드의 개수는 $2^N$개가 된다. 문제에서 주어지는 숫자의 개수는 최대 20개으로, 최대 약 백만개의 숫자에 대해 검사하게 된다. 따라서, 위 코드로 제한 시간 내에서 충분히 해결할 수 있다.
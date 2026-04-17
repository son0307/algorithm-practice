## 문제 요약

- **목표** : N개의 수열과 N-1개의 연산자(+, -, ×, ÷)가 주어질 때, 연산자를 배치하여 얻을 수 있는 수식 결과의 최댓값과 최솟값을 구한다.
- **조건** :
    1. 수의 순서는 변경할 수 없다.
    2. 연산자 우선순위를 무시하고 무조건 앞에서부터(왼쪽에서 오른쪽으로) 연산한다.
    3. 나눗셈은 정수 나눗셈으로 몫만 취하며, 음수를 나눌 때는 양수로 바꾸어 몫을 취한 뒤 다시 음수로 바꾼다.
- **출력 항목** : 계산 결과의 최댓값과 최솟값

## 핵심 포인트

해당 문제의 입력 조건을 살펴보면 수의 개수 N이 최대 11로 매우 작다. 따라서, 가장 먼저 떠올릴 수 있는 알고리즘이 바로 **브루트포스 알고리즘**이다. 그 중에서도 가능한 모든 경우의 수를 탐색해야 하므로 **백트래킹과 DFS**를 이용해야함을 알아낼 수 있다.

- 문제에서 요구한 ‘음수 나눗셈’ 방식은 Java의 기본 정수 나눗셈 동작 방식과 완전히 동일하다. 따라서 별도의 절댓값 처리 없이 바로 나누기를 수행하도 문제없다.

### 동작 원리

1. **변수 및 배열 초기화** : N개의 숫자 배열 `A`, 4가지 연산자 개수를 담은 배열 `operators`, 최댓값 갱신을 위한 `max`(-21억), 최솟값 갱신을 위한 `min`(21억)을 준비한다.
2. **DFS 탐색 시작**: 첫 번째 숫자(`A[0]`)를 누적 결과로 삼고, 두 번째 숫자(인덱스 1)부터 재귀 탐색을 시작한다.
3. **연산자 선택 및 재귀 (Recursive Step)**:
   사용 가능한 연산자가 남아있다면(`operators[i] > 0`), 해당 연산자를 하나 차감한다. 현재 누적 결과값에 해당 연산자를 적용하여 다음 숫자와 연산한 결과를 들고 다음 깊이(`count + 1`)로 재귀 호출한다.
4. **상태 복구 (Backtrack)**: 재귀 호출이 종료되어 돌아오면, 다른 경우의 수를 탐색하기 위해 차감했던 연산자를 다시 복구(`operators[i]++`)한다.
5. **기저 조건 (Base Case)**: 모든 숫자 사이사이에 연산자를 다 끼워 넣어 `count`가 N에 도달하면, 완성된 수식의 결과(`res`)를 기존의 `max`, `min`과 비교하여 갱신하고 종료(return)한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    public void dfs(int n, int[] A, int[] operators, int count, int res) {
        if (count == n) {
            max = Math.max(max, res);
            min = Math.min(min, res);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operators[i] > 0) {
                operators[i]--;
                switch (i) {
                    case 0: dfs(n, A, operators, count + 1, res + A[count]); break;
                    case 1: dfs(n, A, operators, count + 1, res - A[count]); break;
                    case 2: dfs(n, A, operators, count + 1, res * A[count]); break;
                    case 3: dfs(n, A, operators, count + 1, res / A[count]); break;
                }
                operators[i]++;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int[] operators = new int[4];
        for (int i = 0; i < 4; i++) {
            operators[i] = Integer.parseInt(st.nextToken());
        }

        Main m = new Main();
        m.dfs(n, A, operators, 1, A[0]);
        System.out.println(m.max);
        System.out.println(m.min);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- N-1개의 연산자를 나열하는 모든 경우의 수를 탐색하는 완전 탐색(백트래킹) 방식으로, 최악의 경우 시간 복잡도는 $O(N!)$이다.
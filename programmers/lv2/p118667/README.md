## 문제 요약

길이가 같은 두 정수 큐 `queue1`, `queue2`가 주어진다. 한 번의 작업으로 한 큐의 맨 앞 원소를 꺼내 다른 큐의 맨 뒤에 넣을 수 있을 때, 두 큐의 원소 합을 같게 만들기 위한 최소 작업 횟수를 구하는 문제이다.

- 한 번의 작업은 `poll()`과 `add()`를 한 쌍으로 수행한다.
- 두 큐의 합을 같게 만들 수 없다면 `-1`을 반환한다.

## 핵심 포인트

두 큐의 합을 각각 `total1`, `total2`라고 하자. 원소는 모두 양수이므로 현재 합이 더 큰 큐의 맨 앞 원소를 작은 큐로 옮겨야 두 합의 차이를 줄일 수 있다.

이 풀이에서는 `Queue<Integer>` 인터페이스와 `ArrayDeque` 구현체를 사용해 실제 큐의 이동 과정을 그대로 구현한다.

또한 큐의 모든 원소가 `int` 범위에 있더라도 여러 원소를 더한 합은 `int` 범위를 넘을 수 있다. 따라서 두 큐의 합은 `long` 타입으로 관리한다.

### 1) 그리디 접근

매 작업마다 두 큐의 현재 합을 비교한다.

- `total1 > total2`이면 `q1`의 맨 앞 원소를 `q2`로 옮긴다.
- `total1 < total2`이면 `q2`의 맨 앞 원소를 `q1`으로 옮긴다.
- `total1 == total2`가 되면 반복을 종료하고 지금까지의 작업 횟수를 반환한다.

예를 들어 `q1`의 합이 더 큰 경우에는 다음과 같이 처리한다.

```java
int n = q1.poll();
total1 -= n;
q2.add(n);
total2 += n;
```

원소가 모두 양수이므로 합이 더 작은 큐에서 큰 큐로 원소를 옮기면 두 합의 차이가 더 커진다. 따라서 현재 합이 큰 큐에서 작은 큐로 옮기는 선택이 필요하다.

### 2) 전체 합의 홀짝 예외 처리

두 큐의 합을 같게 만들려면 전체 원소의 합을 정확히 절반으로 나눌 수 있어야 한다. 전체 합이 홀수라면 두 큐가 같은 정수 합을 가질 수 없으므로 바로 `-1`을 반환한다.

현재 코드는 두 합의 홀짝이 서로 다른지를 다음과 같이 확인한다.

```java
if ((total1 % 2) != (total2 % 2)) return -1;
```

두 수의 홀짝이 다르면 `total1 + total2`가 홀수이므로 두 큐의 합을 같게 만들 수 없다.

반대로 두 합의 홀짝이 같으면 전체 합은 짝수이므로, 실제로 목표 상태에 도달할 수 있는지는 이후 큐 이동 과정에서 판단한다.

### 3) 무한 반복 방지를 위한 최대 작업 횟수 제한

합을 같게 만들 수 없는 입력에서는 원소를 계속 옮겨도 목표 상태에 도달하지 못할 수 있다. 이를 방지하기 위해 현재 구현은 작업 횟수에 다음과 같은 제한을 둔다.

```java
if (answer > (queue1.length + queue2.length) * 4) return -1;
```

두 큐를 이어 붙인 전체 원소의 순서를 기준으로 보면, 원소 이동은 두 큐의 앞쪽 경계를 이동시키는 과정으로 해석할 수 있다. 위 조건은 전체 원소 개수의 4배를 최대 작업 횟수로 잡은 것으로, 가능한 경계 이동 범위보다 넉넉하게 둔 안전한 상한이다.

따라서 이 횟수를 초과할 때까지 합이 같아지지 않았다면 더 이상의 반복을 중단하고 `-1`을 반환한다.

또한 이동 도중 어느 한 큐가 비면 해당 큐에서는 더 이상 원소를 꺼낼 수 없다. 현재 구현은 다음 검사로 빈 큐에서 `poll()`을 호출하는 상황을 막고 `-1`을 반환한다.

```java
if (q1.isEmpty() || q2.isEmpty())
    return -1;
```

## 동작 원리

1. `queue1`, `queue2`의 원소를 각각 `ArrayDeque` 기반의 `q1`, `q2`에 저장한다.
2. 각 큐의 합을 `long` 타입의 `total1`, `total2`에 계산한다.
3. 두 합의 홀짝이 다르면 전체 합이 홀수이므로 `-1`을 반환한다.
4. 두 합이 다르면 현재 합이 더 큰 큐의 맨 앞 원소를 꺼내 작은 큐의 맨 뒤에 넣는다.
5. 이동한 원소만큼 `total1`, `total2`를 즉시 갱신하고 `answer`를 1 증가시킨다.
6. 작업 횟수가 `(queue1.length + queue2.length) * 4`를 초과하거나 어느 한 큐가 비면 `-1`을 반환한다.
7. `total1 == total2`가 되면 반복을 종료하고 최소 작업 횟수인 `answer`를 반환한다.

각 작업에서 큐 전체의 합을 다시 계산하지 않고 이동한 원소의 값만 더하고 빼기 때문에 한 번의 이동은 `O(1)`에 처리된다. `ArrayDeque`의 `add()`와 `poll()` 역시 평균적으로 `O(1)`에 동작한다.

## 전체 코드

```java
import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;

        long total1 = 0L;
        long total2 = 0L;
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();

        for (int i = 0; i < queue1.length; i++) {
            q1.add(queue1[i]);
            q2.add(queue2[i]);
            total1 += queue1[i];
            total2 += queue2[i];
        }

        if ((total1 % 2) != (total2 % 2)) return -1;

        while (total1 != total2) {
            if (answer > (queue1.length + queue2.length) * 4) return -1;

            if (q1.isEmpty() || q2.isEmpty())
                return -1;

            if (total1 > total2) {
                int n = q1.poll();
                total1 -= n;
                q2.add(n);
                total2 += n;
            } else {
                int n = q2.poll();
                total2 -= n;
                q1.add(n);
                total1 += n;
            }

            answer++;
        }

        return answer;
    }
}
```

## 시간복잡도

각 큐의 길이를 `N`이라고 하면 처음에 모든 원소를 큐에 넣고 합을 계산하는 데 `O(N)`이 필요하다.

이후 반복문은 `(queue1.length + queue2.length) * 4`를 넉넉한 상한으로 두고 있으며, 각 반복에서 `poll()`, `add()`, 합 갱신만 수행한다. 따라서 전체 시간복잡도는 상수 배를 제외하면 다음과 같은 선형 수준이다.

```text
O(N)
```
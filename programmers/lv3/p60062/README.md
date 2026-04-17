### 문제 요약

- 외벽의 길이와 취약 지점들의 위치, 친구들이 한 번에 이동할 수 있는 거리가 주어질 때, 모든 취약 지점을 살피기 위해 필요한 친구 수의 최솟값을 계산하여 반환하는 문제
    - 친구들의 위치는 자유롭게 정할 수 있고, 이에 따라 모든 취약 지점을 방문하는데 필요한 친구의 수가 달라진다.
- 친구들은 시계 방향, 반시계 방향 모두 이동할 수 있다.
    - 이는 함정으로, 친구들을 한 방향으로만 움직일 수 있게 해도 시작 위치만 변경하면 동일한 범위를 탐색할 수 있다.

## 핵심 포인트

이번 문제와 같이 데이터들이 연속적으로 이어진 원형 데이터가 주어지고 이들을 탐색해야 할 때, 모듈러 연산을 이용하지 않고 간편하게 확인할 수 있는 방법이 있다. 바로, 데이터의 길이를 2배로 늘리고 기존 데이터들을 다시 한번 추가하면 된다.

예를 들면, 1번째 예시의 취약점 배열 [1, 5, 6, 10] 탐색 시 시계 방향으로 탐색 도중 12를 넘으면 모듈러 연산을 통해 표현해야 하고 점 사이의 거리를 구하는데도 어려움이 생긴다. 하지만, 배열을 한 번 복사하여 [1, 5, 6, 10, 13, 17, 18, 22]로 만들면 취약점 사이의 거리를 계산하기 수월해진다.

```java
int[] weakAppend = new int[weak.length * 2];
for (int i = 0; i < weakAppend.length; i++) {
    if (i < weak.length) weakAppend[i] = weak[i];
    else weakAppend[i] = weak[i - weak.length] + n;
}
```

위와 같이 원형 데이터를 선형으로 변환한 뒤에는 친구들을 배치하는 최적의 순서를 찾아야 한다. 친구마다 이동 가능한 거리가 다르고, 배치 순서에 따라 커버할 수 있는 취약점의 범위가 달라지기 때문에 **모든 경우의 수를 확인하는 완전 탐색(Brute Force)** 접근이 필요하다.

특히 문제의 제한 조건을 살펴보면 취약점(`weak`)의 개수는 최대 15개, 친구(`dist`)는 최대 8명으로 **입력의 크기가 매우 작다.** 따라서 가능한 모든 친구의 배치 순열(Permutation)을 확인하더라도 시간 복잡도 내에서 충분히 해결 가능하다.

따라서, 주어진 친구들의 이동 거리를 바탕으로 놓을 수 있는 모든 경우(순열)을 탐색하였다.

```java
private static List<List<Integer>> permutation = new ArrayList<List<Integer>>();

void makePermutation(int cnt, int[] dist, boolean[] visited, List<Integer> current) {
    if (current.size() == dist.length) {
        permutation.add(new ArrayList<>(current));
        return;
    }

    for (int i = 0; i < visited.length; i++) {
        if (!visited[i]) {
            visited[i] = true;
            current.add(dist[i]);
            makePermutation(cnt + 1, dist, visited, current);
            visited[i] = false;
            current.remove(current.size() - 1);
        }
    }
}
```

친구들을 위치시킬 수 있는 모든 경우를 탐색하였으면, 각 경우마다 몇 명의 친구들로 모든 취약점을 살필 수 있을지 구해야한다. 이 과정은 다음과 같이 해결할 수 있다.

- 첫 번째 친구를 첫 번째 취약 지점에 세우고 점검할 수 있는 취약점들을 커버하고, 커버되지 않은 다음 취약점으로 넘어간다.
- 모든 취약 지점을 커버할 때까지 반복
- 모두 커버되었으면 위치시킨 친구들의 수를 카운트하여 최솟값을 갱신
- 위 과정을 첫 번째 취약 지점을 하나씩 변경해가며 가능한 모든 경우를 탐색한다.

위 과정을 친구들을 위치시키는 모든 경우의 수에 대해 수행하도록 하면 완전 수행이 이루어지고 최솟값을 구할 수 있다.

이렇게 문제를 해결하는 경우 시간 복잡도는 O(W * P) (W는 취약점의 개수, P는 친구들을 위치시키는 순열 개수)가 된다.

### 전체 코드

```java
import java.util.*;

class Solution {
    private static List<List<Integer>> permutation = new ArrayList<List<Integer>>();
    private int answer = Integer.MAX_VALUE;

    void makePermutation(int cnt, int[] dist, boolean[] visited, List<Integer> current) {
        if (current.size() == dist.length) {
            permutation.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                current.add(dist[i]);
                makePermutation(cnt + 1, dist, visited, current);
                visited[i] = false;
                current.remove(current.size() - 1);
            }
        }
    }

    public int solution(int n, int[] weak, int[] dist) {
        int[] weakAppend = new int[weak.length * 2];
        for (int i = 0; i < weakAppend.length; i++) {
            if (i < weak.length) weakAppend[i] = weak[i];
            else weakAppend[i] = weak[i - weak.length] + n;
        }

        boolean[] visited = new boolean[dist.length];
        List<Integer> tempArray = new ArrayList<>();
        makePermutation(0, dist, visited, tempArray);

        for(List<Integer> friends:permutation) {
            for (int start = 0; start < weak.length; start++) {
                int cursor = start;
                int friendCount = 0;

                for(int friend:friends) {
                    friendCount++;
                    int maxCover = weakAppend[cursor] + friend;
                    while (weakAppend[cursor] <= maxCover && cursor < start + weak.length) {
                        cursor++;
                    }
                    if (cursor >= start + weak.length) {
                        answer = Math.min(answer, friendCount);
                        break;
                    }
                }
            }
        }

        if (answer == Integer.MAX_VALUE) return -1;
        return answer;
    }
}
```
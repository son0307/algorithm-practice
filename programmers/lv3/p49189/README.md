## 문제 요약

- 1번 노드로부터 가장 멀리 떨어진 노드의 갯수를 반환
    - 노드를 연결하는 간선의 가중치는 1로 고정
    - 간선은 양방향으로 연결된다.

## 핵심 포인트

주어진 그래프에서 정점 사이의 거리를 구하는 방법은 여러가지가 있다. 흔히 떠올릴 수 있는 것이 DFS, BFS, 다익스트라 등이 있는데, 여기서는 BFS를 사용하는 것이 가장 적합하다. 그 이유는 다음과 같다.

- BFS
    - 가중치가 1이기 때문에 최단 경로를 구하는 방식이 시작 노드를 기준으로 Level 별로 탐색하는 BFS의 동작 방식과 일치한다.
- DFS
    - 특정 노드까지의 경로를 찾더라도 해당 경로가 최단 경로임을 보장할 수 없다. 따라서 모든 경로를 탐색하고 그 중 최소 경로를 찾아내야 한다. 이로 인해 시간이 오래 걸릴 수 있다.
- 다익스트라
    - 다익스트라는 주로 간선의 가중치가 서로 다를 떄 주로 사용된다. 또한, 다익스트라에서는 Priority Queue를 사용하는데 이는 원소를 넣고 뺄 때 $O(logN)$만큼 소요되기에 단순 큐를 사용하는 것보다 느리다.

위와 같은 이유들로 BFS를 이용하여 문제를 해결하는 것이 가장 적합하다.

### 전체 코드

```java
import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        int maxDistance = 0;

				// 그래프 구성
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edge) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        int[] distance = new int[n+1];
        boolean[] visited = new boolean[n+1];

        Queue<Integer> q = new ArrayDeque<>();

        q.add(1);
        visited[1] = true;

				// BFS 순회
        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : graph.get(cur)) {
                if (!visited[next]) {
                    visited[next] = true;
                    distance[next] = distance[cur] + 1;
                    q.add(next);

                    maxDistance = Math.max(maxDistance, distance[next]);
                }
            }
        }

				// 최대 거리와 동일한 거리를 가진 노드 수 카운트
        int answer = 0;
        for (int d: distance)
            if (d == maxDistance)
                answer++;

        return answer;
    }
}
```

위 알고리즘의 시간 복잡도는 $O(E + V)$(E : 간선 수, V : 정점 수)이다. 하지만, 실질적으로는 주어진 정점들 중 이미 방문한 정점들은 건너뛰기에 더 빠르게 작동한다.
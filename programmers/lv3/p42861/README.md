## 문제 요약

- 최소의 비용으로 n개의 섬을 연결하는 다리를 건설하는 문제
    - 다리를 여러번 건너더라도 도달할 수만 있으면 연결된 것으로 봄
    - 주어진 다리들은 양방향으로 통행할 수 있다.

## 핵심 포인트

이 문제의 목표는 주어진 그래프에서 모든 정점을 최소의 비용으로 연결하는 트리. 즉, **최소 신장 트리(MST, Minimum Spanning Tree)**를 찾는 것이다.

MST를 구하는 대표적인 알고리즘 중 **Kruskal 알고리즘**을 사용하여 해결할 수 있다.

- 그리디 : 주어진 간선들 중 비용이 작은 것부터 선택
- Union-Find : 간선을 선택하여 트리에 추가했을 때 사이클이 발생하면 해당 간선은 스킵
    - 두 섬의 부모(find 연산의 결과)가 다르면 연결되지 않은 것이므로 두 섬을 연결한다.(union 연산 수행)
    - 부모가 같다면 이미 연결된 것이므로 무시한다.
- 최적화 : N개의 정점이 있을 때, MST의 간선 수는 N-1개이다. 따라서, 탐색 중 선택한 간선의 수가 N-1개가 되면 즉시 종료하여 불필요한 연산을 줄인다.

### 전체 코드

```java
import java.util.Arrays;

class Solution {
    private int[] parent;

    private int find(int a) {
        if (parent[a] == a)
            return a;

        return parent[a] = find(parent[a]);
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB)
            parent[rootA] = rootB;
    }

    public int solution(int n, int[][] costs) {
        int answer = 0;

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        Arrays.sort(costs, (c1, c2) -> c1[2] - c2[2]);
        int edgeCount = 0;

        for (int[] cost : costs) {
            if (find(cost[0]) != find(cost[1])){
                union(cost[0], cost[1]);
                answer += cost[2];
                edgeCount++;

                if (edgeCount == n - 1)
                    break;
            }
        }

        return answer;
    }
}
```

위 알고리즘의 시간 복잡도는 $O(E \log E)$이다. (E는 간선의 개수). 자세한 사항은 다음과 같다.

- 주어진 간선들을 비용순으로 오름차순으로 정렬 : $O(E \log E)$
- Union-Find 연산 : 경로 압축을 적용하여 상수 시간에 가깝게 처리된다. 전체 간선의 수에 대해 수행해도 $O(E)$의 시간 복잡도를 가진다.
- 따라서 전체 성능은 정렬 속도에 의해 결정된다.

### 다른 풀이 : Prim 알고리즘 이용

MST를 구할 수 있는 다른 알고리즘은 Prim 알고리즘이 있다. 이는 임의의 시작점에서 출발하여, 현재 신장 트리 집합과 연결된 간선 중 가장 비용이 적은 것을 선택하여 확장해 나간다. 또한, 방문하지 않은 정점을 선택하는 방식으로 트리를 확장해나가기에 구조적으로 사이클이 발생하지 않는다.

- 전체 코드

    ```java
    import java.util.ArrayList;
    import java.util.List;
    import java.util.PriorityQueue;
    
    class Solution {
        static class Edge {
            int node;
            int cost;
    
            public Edge(int node, int cost) {
                this.node = node;
                this.cost = cost;
            }
        }
    
        public int solution(int n, int[][] costs) {
            int answer = 0;
            int currentNodes = 0;
    
            List<List<Edge>> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                edges.add(new ArrayList<>());
            }
    
            for (int[] cost : costs) {
                edges.get(cost[0]).add(new Edge(cost[1], cost[2]));
                edges.get(cost[1]).add(new Edge(cost[0], cost[2]));
            }
    
            boolean[] visited = new boolean[n];
            PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.cost - e2.cost);
    
            pq.offer(new Edge(0, 0));
    
            while(!pq.isEmpty()) {
                Edge current = pq.poll();
    
                if (!visited[current.node]) {
                    visited[current.node] = true;
                    answer += current.cost;
                    currentNodes++;
    
                    if (currentNodes == n) {
                        break;
                    }
    
                    for (Edge next : edges.get(current.node)) {
                        if (!visited[next.node]) {
                            pq.offer(next);
                        }
                    }
                }
            }
    
            return answer;
        }
    }
    ```

  이 코드의 시간 복잡도는 $O(E \log V)$이다. 이는 우선순위 큐에 간선들이 들어가고 나오는 과정에서 소요되는 시간이다. Kruskal 알고리즘은 간선 수가 적은 희소 그래프일 때 유리하고, Prim 알고리즘은 간선이 매우 많은 밀집 그래프일 때 유리하다.
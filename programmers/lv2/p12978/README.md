### 문제 요약

- 주어진 그래프에서 1번 마을을 기준으로 주어진 K 이하의 거리로 도달할 수 있는 노드의 수를 구하는 문제
    - 주어진 그래프는 무방향 그래프이므로 a → b, b → a 이동이 모두 가능하다.
    - 1번 마을은 항상 정답에 포함된다.

---

## 핵심 포인트

이 문제는 그래프를 탐색하는 문제로, 그래프를 탐색하는 대표적인 알고리즘들을 적용할 수 있다. 다익스트라 알고리즘, 벨만-포드 알고리즘, 플로이드-워셜 알고리즘이 그 예시들이다.

이 알고리즘들말고도 DFS를 이용하여 탐색을 수행할 수 있다. 탐색을 수행하는 중 주어진 K를 초과하는 경우 되돌아오는 백트래킹을 이용하면 된다.

이번 문제에서는 다익스트라 알고리즘과 DFS를 이용하여 문제를 해결해보도록 하겠다.

### 다익스트라 활용

다익스트라 알고리즘은 한 정점으로부터 다른 모든 정점까지의 최소 거리를 구하는데 사용되는 알고리즘이다. 단, 간선들의 가중치가 모두 양수인 경우에만 사용 가능하다.

간단히 과정을 설명하면 아래와 같이 수행된다.

1. 인접 리스트를 활용하여 그래프를 구현
2. 최단 거리 배열(distance) 초기화, 시작 노드는 거리를 0으로 설정하고 나머지는 무한대로 설
3. 현재 탐색한 노드와 인접한 노드들 중 distance가 가장 작은 노드를 선택 후 최단 거리 배열 업데이트
4. 모든 노드가 방문되어 탐색될 때까지 반복
- 전체코드

    ```java
    import java.util.*;
    
    class Node implements Comparable<Node> {
        int num;
        int distance;
    
        Node(int num, int distance) {
            this.num = num;
            this.distance = distance;
        }
    
        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }
    
    class Solution {
        static LinkedList<Node>[] graph;
        static int[] distance;
    
        static void dijkstra(int start, int N) {
            boolean[] visited = new boolean[N+1];
    
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[start] = 0;
    
            PriorityQueue<Node> queue = new PriorityQueue<>();
            queue.add(new Node(start, 0));
    
            while(!queue.isEmpty()) {
                Node node = queue.poll();
                if(!visited[node.num]) {
                    visited[node.num] = true;
    
                    for(Node n : graph[node.num]) {
                        if(!visited[n.num] && distance[node.num] + n.distance < distance[n.num]) {
                            distance[n.num] = distance[node.num] + n.distance;
                            queue.add(new Node(n.num, distance[n.num]));
                        }
                    }
                }
            }
        }
    
        public int solution(int N, int[][] roads, int K){
            graph = new LinkedList[N+1];
            distance = new int[N+1];
    
            for (int i = 1; i <= N; i++) {
                graph[i] = new LinkedList<Node>();
            }
    
            for (int[] road : roads) {
                int u = road[0];
                int v = road[1];
                int w = road[2];
                graph[u].add(new Node(v, w));
                graph[v].add(new Node(u, w));
            }
    
            dijkstra(1, N);
    
            int answer = 0;
            for (int i = 1; i <= N; i++) {
                if (distance[i] <= K) answer++;
            }
            return answer;
        }
    }
    ```


### DFS 활용

DFS를 통해 그래프를 탐색하는 도중 다음 노드까지의 거리가 K를 넘어서게 되면 탐색하지 않는 방식으로도 문제를 해결할 수 있다.

- 전체 코드

    ```java
    import java.util.*;
    
    class Solution {
        HashSet<Integer> set = new HashSet<>();
    
        static class Node {
            int num;
            int distance;
    
            Node(int num, int distance) {
                this.num = num;
                this.distance = distance;
            }
        }
    
        public int solution(int N, int[][] roads, int K) {
            boolean[] visited = new boolean[N+1];
            List<List<Node>> map = new ArrayList<>();
            for (int i = 0; i < N + 1; i++) {
                map.add(new ArrayList<>());
            }
    
            for (int[] road : roads) {
                map.get(road[0]).add(new Node(road[1], road[2]));
                map.get(road[1]).add(new Node(road[0], road[2]));
            }
    
            dfs(map, visited, 1, 0, K);
    
            System.out.println("set = " + set);
            return set.size();
        }
    
        public void dfs(List<List<Node>> map, boolean[] visited, int curNum, int curDistance, int maxDistance) {
            if (visited[curNum]) return;
    
            visited[curNum] = true;
            set.add(curNum);
            List<Node> adjacentNodes = map.get(curNum);
            for (Node node : adjacentNodes) {
                if (!visited[node.num] && curDistance + node.distance <= maxDistance) {
                    dfs(map, visited, node.num, curDistance + node.distance, maxDistance);
                }
            }
            visited[curNum] = false;
        }
    }
    ```
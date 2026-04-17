### 문제 요약

- 하나의 트리를 간선 1개를 삭제하여 두 개의 트리로 만들었을 때, 두 트리의 노드 수 차이의 최솟값을 구하는 문제
    - 입력되는 형태는 무조건 트리 형태로 고정 → 간선 하나가 사라지면 무조건 트리 2개로 나뉘어짐

---

## 핵심 포인트

두 개의 트리로 나뉘어졌을 때, 둘 중 한 트리의 노드 개수만 구하면 다른 트리의 노드 또한 구할 수 있다. 그러므로, 그래프 탐색 알고리즘을 통해 노드 개수를 구해내는 것이 필요하다. 즉, 다음과 같은 순서로 처리할 수 있다.

1. 하나의 트리를 간선 하나를 삭제하여 두 개의 트리로 구분
2. DFS를 이용하여 둘 중 한 트리의 노드 개수를 카운트
3. 두 트리의 노드 개수를 구하고 그 차이 값을 계산
4. 모든 경우를 탐색할 때까지 반복하며 최솟값을 찾는다.

### 전체 코드

```java
public int dfs(int curNode, int blockedNode) {
    visited[curNode] = true;
    int cur = 1;

    for (int adjNode : adjList[curNode]) {
        if (adjNode != blockedNode && !visited[adjNode]) {
            cur += dfs(adjNode, blockedNode);
        }
    }

    return cur;
}
```

주어진 끊을 간선의 양 쪽 노드를 기준으로 탐색을 시작한다. 여기서 curNode는 끊을 간선의 두 노드 중 하나이고, blockedNode는 해당 간선의 다른 노드이다. 인접한 노드들 중 blockedNode를 제외하고 탐색을 수행한다. DFS를 통해 트리를 탐색해나가며 연결되어 있는 노드들의 수를 카운트한다.

```java
public int solution(int n, int[][] wires) {
    int minDiff = Integer.MAX_VALUE;

    adjList = new ArrayList[n+1];
    for (int i = 0; i <= n; i++) {
        adjList[i] = new ArrayList<>();
    }

    for (int[] wire : wires) {
        adjList[wire[0]].add(wire[1]);
        adjList[wire[1]].add(wire[0]);
    }

    for (int[] wire : wires) {
        int v1 = wire[0];
        int v2 = wire[1];

        visited = new boolean[n+1];
        int subTree1Size = dfs(v1, v2);
        int subTree2Size = n - subTree1Size;

        minDiff = Math.min(minDiff, Math.abs(subTree1Size - subTree2Size));
    }

    return minDiff;
}
```

주어진 그래프를 초기화하고 DFS를 시작하는 부분이다. 주어진 그래프는 무방향 그래프이므로 인접리스트에 둘 다 넣어줘야 한다. 이후, 주어진 간선들을 하나씩 선택하여 제외한 상황들을 탐색하며 두 서브트리의 노드 수 차이를 계산한다. 현재까지의 최솟값보다 더 작은 값이 나오면 최솟값을 갱신한다. 모든 경우를 탐색하고 나면 minDiff에 저장된 최솟값을 반환한다.
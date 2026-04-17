### 문제 요약

- 양과 늑대로 구성된 트리를 탐색하는 문제. 단, 아래 조건에 맞춰 탐색을 수행해야 함.
    - 탐색한 루트에 포함된 늑대의 수가 양의 수 이상이 될 수 없음
    - 무조건 방문한 노드의 자식으로 내려가면서 탐색하는게 아님. 다른 루트로도 탐색이 가능
    - 위 조건을 만족하면서 최대한으로 모을 수 있는 양의 수를 계산

---

## 핵심 포인트

해당 문제에서 최대한으로 모을 수 있는 양의 수는 노드들을 어떤 순서로 방문하느냐에 따라 달라진다. 즉, 순회 가능한 여러 경우의 수들 중에서 최적의 경우를 찾아내는 것이 필요한 문제이므로 이 문제를 해결하는데 BFS를 사용할 수 있다.

이제 BFS를 어떻게 사용할 것인지를 구상해야 한다. 문제를 분석하면 현재까지 탐색한 루트에서 계속 아래로 내려가야 하는 것이 아닌 다른 방향으로도 탐색이 가능하다. 이 말은 현재까지 탐색한 노드들과 연결된 다른 노드들을 전부 탐색 범위에 둬야 한다는 의미이다.

따라서, BFS에 사용될 큐에는 현재까지 탐색한 노드들에 대한 상태와 방문 가능한 노드들에 대한 정보를 저장해야 한다. 방문 가능한 노드가 양과 늑대의 수에 대한 조건에 맞으면 큐에 넣어주면서 탐색을 지속하면 된다.

- 인접한 노드에 늑대가 존재하는 경우
    - 늑대의 수가 1 증가해도 양의 수 미만인 경우 상태 정보를 탐색 큐에 추가
- 인접한 노드에 양이 존재하는 경우
    - 양의 수를 1 증가시키고 상태 정보를 탐색 큐에 추가

예시를 바탕으로 어떤 식으로 탐색이 이뤄지는지 알아보자.

1. 탐색 준비

![image.png](attachment:347da665-f51a-485a-bca2-efa913a7e288:3f20ec10-7037-49aa-b597-771e0ac30c20.png)

루트 노드는 무조건 양이므로 큐에 미리 넣어놓는다. 큐에 저장하는 데이터는 마지막에 탐색한 노드의 번호와 현재까지의 양과 늑대의 수, 인접한 노드들의 목록을 저장한다.

1. 첫번째 탐색

![image.png](attachment:5591dbea-2312-4418-9ea2-15796e068bef:f88d6567-2e65-4b21-ae1b-fed70cb8f283.png)

큐에서 데이터를 하나 꺼내고, 데이터에 저장된 인접한 노드들에 대해 탐색을 수행한다. 8로 가게 되면 늑대의 수가 양의 수와 같아지므로 조건을 만족하지 못한다. 그러므로 1번 노드에 대한 정보를 큐에 추가한다.

1. 두번째 탐색

![image.png](attachment:c158ca72-89e8-41ca-a850-04f9ad29ed26:5481872a-32bf-4bc8-8971-fad37c8557f2.png)

큐에서 1번 노드에 대해 저장된 데이터를 꺼내 인접한 노드들에 대해 탐색을 수행한다. 2, 4, 8번 노드들이 전부 늑대이지만 양이 2마리, 늑대 1마리라 조건에 취합한다. 따라서 노드에 대한 데이터를 큐에 추가한다.

1. 세번째 탐색

![image.png](attachment:3ce4f555-8feb-4ba1-93a9-54d083bd9c43:9254d92d-1e1c-4fb9-bc8f-b99c1401dc5d.png)

이전 탐색에서 큐에 추가되었던 데이터들을 하나씩 뽑아 인접 노드들에 대해 탐색을 수행한다.

- 2번 노드 → 4, 8이 인접 노드, 이동 시 늑대의 수가 양의 수와 동일해져 조건 위반
- 4번 노드 → 2, 3, 6, 8이 인접 노드, 이동 시 늑대의 수가 양의 수와 동일해져 조건 위반
- 8번 노드 → 2, 4, 7, 9가 인접 노드, 2, 4는 이동 시 늑대의 수가 양의 수와 동일해져 조건 위반, 7, 9는 조건에 취합되므로 데이터를 큐에 추가한다.

이러한 과정을 큐가 빌 때까지 반복하면 탐색이 완료되고, 탐색을 할 때마다 최대 양의 개수를 계산하도록 하여 최대 양의 수를 계산할 수 있다.

### 전체 코드

```java
private static class Node {
    int num, sheep, wolf;
    HashSet<Integer> adjacent;

    public Node(int num, int sheep, int wolf, HashSet<Integer> adjacent) {
        this.num = num;
        this.sheep = sheep;
        this.wolf = wolf;
        this.adjacent = adjacent;
    }
}

public int solution(int[] info, int[][] edges) {
    int answer = 0;

    ArrayList<Integer>[] tree = new ArrayList[info.length];
    for (int i = 0; i < tree.length; i++) {
        tree[i] = new ArrayList<>();
    }

    for (int[] edge : edges) {
        tree[edge[0]].add(edge[1]);
    }

    Queue<Node> queue = new LinkedList<>();
    queue.add(new Node(0, 1, 0, new HashSet<>()));

    while (!queue.isEmpty()) {
        Node node = queue.poll();
        answer = Math.max(answer, node.sheep);
        node.adjacent.addAll(tree[node.num]);

        for (Integer next : node.adjacent) {
            HashSet<Integer> adj = new HashSet<>(node.adjacent);
            adj.remove(next);

            if (info[next] == 0) {
                queue.add(new Node(next, node.sheep + 1, node.wolf, adj));
            } else {
                if (answer >= node.wolf + 1) {
                    queue.add(new Node(next, node.sheep, node.wolf + 1, adj));
                }
            }
        }
    }

    return answer;
}
```
### 문제 요약

- 주어진 승패 결과를 바탕으로 순위를 정확하게 결정할 수 있는 선수의 수를 구하는 문제
    - [A, B]로 주어진 결과는 A가 B를 상대로 항상 승리한다는 걸 의미한다.
    - [A, B], [B, C]로 주어졌을 때, [A, C]는 항상 성립한다.

## 핵심 포인트

선수의 순위를 결정하려면 어떤 선수에게 이기고 지는지를 알아야 한다. 주어진 조건에서 실력에 따라 어떤 선수가 이기는지를 이미 결정해주었기에 이를 바탕으로 어떤 선수가 누구에게 이기고 지는지를 구할 수 있다. 한 선수에 대해 이기고 지는 선수의 합이 n - 1개가 되면 모든 경우에 대한 경우를 구한 것이므로 순위를 결정할 수 있다.

주어진 입출력 예를 그래프로 표현하여 생각해보도록 하자.

![image.png](attachment:bce8b855-78c3-4fa3-9b85-81337a04c39b:image.png)

이 그림에서 3번에 대해 설명하면, 4번한테 지고, 2번한테 이기도록 연결되어 있다. 이 때, 2번은 이미 5번을 상대로 이기는 판정이므로 3번 역시 5번에게 승리하게 되는 것이다. 따라서, 3번 선수는 2번, 5번 선수에게 이기고, 4번 선수에게 진다. 1번과의 결과가 없기 때문에 아직 순위를 결정할 수는 없다.

2번과 5번은 모든 선수에 대해 승패가 나뉘어지므로 이 선수들은 순위를 결정할 수 있다. 따라서, 위 예제에서는 답이 2인 것이다.

이를 통해 이 문제는 다음과 같은 순서로 해결할 수 있음을 알 수 있다.

1. 주어진 예제를 방향 그래프의 형태로 나타낸다.
2. 각 선수를 순회하며 다음과 같은 연산을 수행한다.
    1. 선수가 이기는 방향으로 DFS를 시작하여 승리하는 선수의 수를 계산한다.
    2. 선수가 지는 방향으로 DFS를 시작하여 패배하는 선수의 수를 계산한다.
    3. 이 둘의 합이 n - 1과 동일하면 모든 선수와의 결과가 나온 것이므로 count를 1 올린다.

위 순서로 코드를 작성해보도록 하자.

### 전체 코드

```java
private int dfsForward(int a, boolean[][] graph, boolean[] visited) {
    int count = 1;

    for (int i = 0; i < graph.length; i++) {
        if (!visited[i] && graph[a][i]) {
            visited[i] = true;
            count += dfsForward(i, graph, visited);
        }
    }

    return count;
}
```

재귀 함수를 이용하여 DFS 탐색을 수행하는 함수이다. 주어진 방향그래프를 순서에 따라 탐색하며, 그 수를 카운트하여 반환한다.

```java
private int dfsBackward(int a, boolean[][] graph, boolean[] visited) {
    int count = 1;

    for (int i = 0; i < graph.length; i++) {
        if (!visited[i] && graph[i][a]) {
            visited[i] = true;
            count += dfsBackward(i, graph, visited);
        }
    }

    return count;
}
```

이는 위와 반대로 탐색을 수행하는 함수이다. 주어진 방향그래프를 반대로 탐색하며 탐색 시작 기준이 되는 선수가 패배하는 선수들의 수를 카운트하여 반환한다.

```java
public int solution(int n, int[][] results) {
    int answer = 0;

    boolean[][] graph = new boolean[n][n];
    for (int[] result : results) {
        int x = result[0] - 1;
        int y = result[1] - 1;
        graph[x][y] = true;
    }

    for (int i = 0; i < n; i++) {
        boolean[] visited = new boolean[n];
        int winCount = dfsForward(i, graph, visited) - 1;
        int loseCount = dfsBackward(i, graph, visited) - 1;
        if (winCount + loseCount == n - 1) answer++;
    }

    return answer;
}
```

메인 함수에서는 초기화를 담당하고 위에서 작성한 함수들을 호출하여 정답을 구하는 역할을 담당한다. 주어지는 예제들이 1부터 시작이기에 이를 배열의 인덱스에 맞춰 변환한 다음 방향 그래프를 배열의 형태로 묘사한다.
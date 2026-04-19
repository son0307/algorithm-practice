## 문제 요약

- **목표**: 1번 지점에서 출발해 N개의 모든 지점을 정확히 한 번씩만 방문하고, 다시 1번 지점으로 돌아오는 최소 비용을 구한다.
- **조건**:
    1. 지점 간 이동 비용은 행렬(`distance`) 형태로 주어진다.
    2. 이동 비용이 0인 경우는 길이 없다는 뜻이므로 이동할 수 없다.
    3. N은 최대 10이다.
- **출력 항목**: 모든 지점을 순회하고 돌아오는 최소 비용.

## 핵심 포인트

- **백트래킹 (순열 탐색)**: 0번을 제외한 나머지 N-1개의 지점을 방문하는 모든 순서(경우의 수)를 탐색해야 하므로 DFS를 활용한 백트래킹을 활용할 수 있다.
- **가지치기 (Branch and Bound)**: DFS 탐색 도중, **현재까지 누적된 비용이 이미 내가 알고 있는 최소 비용(min)을 넘어섰다면 더 이상 탐색할 필요가 없다.** 이를 적용하여 필요없는 탐색을 줄일 수 있다.

### 동작 원리

- **초기화**: 0번 지점에서 출발하기 위해 `visited[0] = true`로 설정하고 탐색을 시작한다.
- **DFS 탐색 (경로 생성)**:
    - 현재 노드(`i`)에서 갈 수 있는 다음 노드(`j`)를 찾는다.
    - 아직 방문하지 않았고(`!visited[j]`), 가는 길이 존재한다면(`distance[i][j] != 0`), 방문 처리 후 누적 비용에 현재 이동 비용을 더해 재귀 호출을 한다.
    - 재귀 호출이 끝나면 다른 경로 탐색을 위해 방문을 취소한다(`visited[j] = false`).
- **최적화 검사 (가지치기)**: 탐색 도중 누적 비용(`sum`)이 이미 갱신된 최소 비용(`min`) 이상이 되면 즉시 탐색을 멈추고 돌아간다.
- **기저 조건 (사이클 완성)**:
    - 0번을 제외한 모든 노드를 방문했다면(`cnt == n - 1`), 마지막으로 방문한 노드(`i`)에서 다시 출발점(0번)으로 돌아가는 길이 있는지 확인한다.
    - 길이 있다면 전체 비용(`sum + distance[i][0]`)을 계산하고 최소 비용(`min`)을 갱신한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static boolean[] visited;
    static int[][] distance;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        visited = new boolean[n];
        distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited[0] = true;
        dfs(0, 0, 0);

        System.out.println(min);
    }

    static void dfs(int cnt, int i, int sum) {
        // 현재까지의 비용이 최솟값 이상이 되면 더 탐색할 필요 없음
        if (sum >= min) return;

        // 0번을 제외한 모든 노드 방문 시 최솟값 갱신
        if (cnt == n-1) {
            // 마지막 노드에서 0번 노드로 돌아가는 길 체크
            if (distance[i][0] == 0) return;

            min = Math.min(min, sum + distance[i][0]);
            return;
        }

        for (int j = 0; j < n; j++) {
            // 방문 X, 이동 가능한 경로 존재한 경우
            if (!visited[j] && distance[i][j] != 0) {
                visited[j] = true;
                dfs(cnt + 1, j, sum + distance[i][j]);
                visited[j] = false;
            }
        }
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 외판원 순회 문제는 모든 노드를 방문하는 순열을 구하는 문제이므로 최악의 경우 시간 복잡도는 팩토리얼 스케일을 따른다. 다만, 여기서는 0번 노드를 시작점을 고정해두었기 때문에 `N!`에서 `(N-1)!`로 줄어든다.

최종적으로 위 알고리즘의 시간 복잡도는 $O((N-1)!)$이다.
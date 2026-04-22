## 문제 요약

- **목표** : $N \times N$ 크기의 격자에서 $K$개의 도시를 시작점으로 골랐을 때, 조건에 맞춰 이동할 수 있는 서로 다른 도시의 최대 개수를 구한다.
- **조건**
    1. 인접한 상하좌우로만 이동 가능하다.
    2. 두 도시 간의 높이 차이가 $U$ 이상 $D$ 이하일 때만 이동할 수 있다.
- **출력 항목** : $K$개의 도시를 적절히 골랐을 때 도달 가능한 도시의 최대 개수

## 핵심 포인트

- **다중 시작점 BFS (Multi-source BFS)**: 선택된 $K$개의 도시를 큐(Queue)에 한 번에 모두 넣고 BFS를 돌려서 도달 가능한 영역을 구한다.
- **1차원 인덱싱을 활용한 조합(Combination)**: 격자 위에서 중복 없이 $K$개의 점을 고를 때, 2중 `for`문으로 2차원 좌표를 다루면 별도의 처리를 요구한다. $N \times N$ 격자를 $0$부터 $N^2-1$까지의 1차원 배열로 생각하고 고르면 훨씬 간단하게 해결할 수 있다.

### 동작 원리

1. **변수 및 맵 초기화**: 높이 정보, $K, U, D$ 등을 입력받는다.
2. **시작점 고르기 (백트래킹)**:
    - `dfs(idx, cnt)` 함수를 호출합니다. 여기서 `idx`는 $0 \sim N^2-1$의 1차원 인덱스이다.
    - 1차원 `idx`를 $N$으로 나눈 몫이 `row`, 나머지가 `col`이 된다. 해당 좌표를 선택(`selected.add`)하고 다음 인덱스(`i + 1`)로 재귀 호출한다.
    - 탐색이 끝나면 선택을 취소(`selected.remove`)하여 백트래킹 탐색한다.
3. **도달 가능 영역 계산 (Multi-source BFS)**:
    - 정확히 $K$개를 골랐다면(`cnt == K`), 해당 좌표들을 큐에 넣고 BFS를 실행한다.
    - 상하좌우를 탐색하며 높이 차이가 $U \le \text{diff} \le D$ 조건을 만족할 때만 방문하고 큐에 넣는다.
    - 방문한 도시의 개수를 세어 기존의 최댓값(`max`)과 비교해 갱신한다.

### 전체 코드

```java
package codetree.bfs01.weAreTheOne;

import java.util.*;
import java.io.*;

public class Main {
    static int n, k, u, d;
    static int max = Integer.MIN_VALUE;
    static int[][] map;
    static List<int[]> selected = new ArrayList<>();

    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        u = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);

        System.out.println(max);
    }

    static void dfs(int idx, int cnt) {
        if (cnt == k) {
            max = Math.max(max, bfs());
            return;
        }

        for (int i = idx; i < n*n; i++) {
            int row = i / n;
            int col = i % n;

            selected.add(new int[]{row, col});
            dfs(i + 1, cnt + 1);
            selected.remove(selected.size() - 1);
        }
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        int count = 0;

        for (int[] s : selected) {
            q.add(s);
            visited[s[0]][s[1]] = true;
            count++;
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                if (visited[ny][nx]) continue;

                int diff = Math.abs(map[ny][nx] - map[cur[0]][cur[1]]);
                if (diff >= u && diff <= d) {
                    visited[ny][nx] = true;
                    count++;
                    q.add(new int[]{ny, nx});
                }
            }
        }

        return count;
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 격자 내에서 $K$개의 도시를 고르는 경우의 수 : $O(_{N^2}C_K)$
- 각각의 조합마다 BFS를 통해 맵 전체를 순회 : $O(N^2)$

결과적으로 위 알고리즘의 시간 복잡도는 $O(_{N^2}C_K \times N^2)$이 된다.

위 문제에서 최악의 경우의 연산 횟수는 $41,664(_{64}C_3) \times 64 \approx 266만$ 으로, 제한 시간 내에 충분히 해결할 수 있다.
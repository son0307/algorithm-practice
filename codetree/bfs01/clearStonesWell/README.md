## 문제 요약

- **목표**: $N \times N$ 격자에서 주어진 돌(1) 중 $M$개를 치웠을 때, $K$개의 시작점에서 출발하여 도달할 수 있는 칸(0)의 최대 개수를 구한다.
- **조건**:
    1. 시작점은 여러 개($K$개)이며, 상하좌우 인접한 빈 칸으로만 이동 가능하다.
    2. 전체 돌의 개수는 최대 8개이며, 이 중 정확히 $M$개를 골라서 치워야 한다.
- **출력 항목**: $M$개의 돌을 치웠을 때 도달 가능한 최대 칸 수

## 핵심 포인트

- **다중 시작점 BFS (Multi-source BFS)**: 시작점이 여러 개일 때, 각각의 시작점에서 따로 BFS를 돌릴 필요 없이 처음에 모든 시작점을 한 번에 큐(Queue)에 넣고 BFS를 1번만 돌리면 훨씬 효율적으로 탐색할 수 있다.
- **조합 (Combination) 최적화**: 격자 전체를 순회하며 돌을 찾는 것보다, **처음 입력받을 때 돌의 좌표만 따로 리스트에 모아두고** 그 리스트 안에서 $M$개를 고르는 방식을 통해 효율적으로 탐색이 가능하다.

### 동작 원리

1. **데이터 수집**: 격자를 입력받으면서 값이 1인 칸(돌의 좌표를 `stones` 리스트에 저장합니다. $K$개의 시작점도 `startPoints` 리스트에 저장해 둡니다
2. **돌 치우기 (백트래킹)**:
    - `stones` 리스트를 0번 인덱스부터 탐색하며, 현재 돌을 '치우는 경우'와 '치우지 않는 경우' 두 갈래로 나눕니다.
    - 돌을 치울 때는 맵의 값을 0으로 바꾸고, 백트래킹으로 돌아오면 다시 1로 복구합니다.
3. **최대 영역 계산 (Multi-source BFS)**:
    - 정확히 $M$개의 돌을 치웠다면 BFS를 실행합니다.
    - $K$개의 시작점을 큐에 넣으면서 `count`를 증가시킵니다.
    - 큐에서 좌표를 하나씩 꺼내며 상하좌우를 탐색하고, 갈 수 있는 빈 칸(0)을 발견하면 큐에 넣고 `count`를 1 증가시킵니다.
    - BFS가 종료된 직후의 `count` 값과 `max` 값을 비교하여 최댓값을 갱신합니다.

### 전체 코드

```java
package codetree.bfs01.clearStonesWell;

import java.io.*;
import java.util.*;

public class Main {
    static int n, m, k;
    static int[][] map;
    static List<int[]> startPoints = new ArrayList<>();
    static List<int[]> stones = new ArrayList<>();
    static int maxArea = 0;

    static int[] dy = {0, 0, 1, -1};
    static int[] dx = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    stones.add(new int[]{i, j});
                }
            }
        }

        // 시작 점을 입력받아 위치 저장
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            startPoints.add(new int[]{r, c});
        }

        dfs(0, 0);

        System.out.println(maxArea);
    }

    public static void dfs(int idx, int removeCount) {
        // 치운 돌 개수가 m개가 되면 BFS를 통해 방문 가능한 칸 최댓값 계산
        if (removeCount == m) {
            maxArea = Math.max(maxArea, bfs());
            return;
        }

        // 더 이상 치울 돌이 없으면 종료
        if (idx == stones.size()) return;

        // 현재 인덱스의 돌을 치우는 경우
        int[] stone = stones.get(idx);
        map[stone[0]][stone[1]] = 0;
        dfs(idx + 1, removeCount + 1);
        map[stone[0]][stone[1]] = 1;

        // 현재 인덱스의 돌을 치우지 않는 경우
        dfs(idx + 1, removeCount);
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        int count = 0;

        // 시작점 방문 처리 후 큐에 삽입
        for (int[] startPoint : startPoints) {
            int y = startPoint[0];
            int x = startPoint[1];
            if (!visited[y][x]) {
                visited[y][x] = true;
                q.add(new int[]{y, x});
                count++;
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                // 맵을 벗어났거나, 이미 방문했거나, 돌이 있으면 스킵
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                if (map[ny][nx] == 1 || visited[ny][nx]) continue;

                visited[ny][nx] = true;
                q.add(new int[]{ny, nx});
                count++;
            }
        }

        return count;
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $S$개의 돌 중 $M$개의 돌을 고르는데 소요되는 시간 : $O(_SC_m)$
- 모든 돌을 고르고 난 후 BFS를 통해 모든 격자를 검사하는데 걸리는 시간 : $O(N^2)$

따라서 위 알고리즘의 시간 복잡도는 $O(_sC_m \times N^2)$이다.
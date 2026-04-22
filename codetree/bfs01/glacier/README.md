## 문제 요약

- **목표**: 빙하로 둘러싸이지 않은 물에 닿은 빙하가 1초에 한 겹씩 녹을 때, 빙하가 전부 녹는 데 걸리는 시간과 마지막으로 녹은 빙하의 크기를 구한다.
- **조건**:
    1. 격자의 가장자리는 항상 물(0)이다.
    2. 빙하(1)로 완전히 둘러싸인 물(0)은 빙하를 녹일 수 없다.
    3. 빙하는 상하좌우로 외부 물과 닿아있을 때 녹는다.
- **출력 항목**: 전부 녹는 데 걸린 총 시간, 마지막 턴에 녹은 빙하의 조각 수

## 핵심 포인트

- **외부와 내부의 분리**: 빙하로 둘러쌓인 물과 바깥쪽 물을 구별하기 위해, 무조건 바깥쪽임이 보장된 `(0, 0)`에서 시작하여 닿을 수 있는 물만 바깥쪽 물로 취급한다.
- **한 겹만 녹이기 (Layer-by-layer)**: BFS 탐색 중 빙하(1)를 만나면 이를 물(0)로 바꾸되, **해당 좌표를 큐에 넣지 않아야** 그 너머의 빙하가 같은 턴에 녹는 것을 방지할 수 있다.
- **탐색 범위의 최적화**: 매초 `(0, 0)`부터 시작해 넓은 바다를 다시 헤엄쳐 빙하를 찾으러 가는 것은 이미 이전 턴에 확인한 칸을 중복으로 확인하여 낭비가 발생한다. **방금 녹아서 물이 된 그 자리**가 바로 다음 턴의 시작점이 되면 탐색을 최소화할 수 있다.

### 동작 원리

- **변수 및 큐 초기화**: 맵 전체를 관리하는 방문 배열 `visited`를 생성합니다. 바다를 탐색할 `waterQ`에 `(0, 0)`을 넣고 방문 처리한다.
- **시뮬레이션 루프 (`while`)**:
    - 매 턴마다 새롭게 녹을 빙하의 위치를 담을 `meletedQ`를 준비한다.
    - `waterQ`를 돌며 상하좌우를 탐색한다.
    - 빈 공간(0)이면 `waterQ`에 넣어 계속 바다를 넓히며 탐색한다.
    - 빙하(1)를 만나면 0으로 녹이고, `meltedCount`를 증가시킨 뒤, 그 자리를 `meletedQ`에 넣는다.
- **종료 및 갱신 조건**:
    - `meltedCount`가 0이라면, 더 이상 녹을 빙하가 없다는 뜻이므로 루프를 종료한다.
    - 빙하가 녹았다면 시간(`time`)을 1 올리고, `lastMelted`를 갱신한 뒤, `waterQ = meltedQ`로 교체하여 다음 턴을 준비한다.

### 전체 코드

```java
package codetree.bfs01.glacier;

import java.util.*;
import java.io.*;

public class Main {
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> waterQ = new ArrayDeque<>();

        // (0, 0) 시작점 설정
        waterQ.add(new int[]{0, 0});
        visited[0][0] = true;

        int time = 0;
        int lastMelted = 0;

        while(true) {
            int meltedCount = 0;
            // 이번 턴에 녹은 빙하들의 좌표 저장
            Queue<int[]> meltedQ = new ArrayDeque<>();

            while(!waterQ.isEmpty()) {
                int[] cur = waterQ.poll();

                for (int i = 0; i < 4; i++) {
                    int ny = cur[0] + dy[i];
                    int nx = cur[1] + dx[i];

                    if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                    if (visited[ny][nx]) continue;

                    visited[ny][nx] = true;

                    if (map[ny][nx] == 1) {
                        // 빙하를 만나면 녹이고, 녹은 빙하 좌표 저장
                        meltedCount++;
                        map[ny][nx] = 0;
                        meltedQ.add(new int[]{ny, nx});
                    } else {
                        // 물을 만나면 현재 턴에 계속해서 탐색
                        waterQ.add(new int[]{ny, nx});
                    }
                }
            }

            // 이번 턴에 녹은 빙하가 하나도 없다면 종료
            if (meltedCount == 0) {
                System.out.println(time + " " + lastMelted);
                return;
            }

            lastMelted = meltedCount;
            time++;
            // 다음 턴의 탐색은 이번 턴에서 녹은 빙하들의 위치들부터 탐색 시작
            waterQ = meltedQ;
        }
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 위 알고리즘에서는 맵의 각 칸이 `visited` 체크를 통해 전체 시뮬레이션 동안 단 한 번만 큐에 들어가고 나온다.

따라서 위 알고리즘의 시간 복잡도는 $O(N \times M)$이 된다.
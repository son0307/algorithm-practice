## 문제 요약

- **목표**: $N \times N$ 격자에서 특정 중심점을 기준으로 크기가 $K$인 마름모 모양으로 채굴을 진행할 때, 손해를 보지 않으면서 가장 많이 캘 수 있는 금의 개수를 구한다.
- **조건**:
    1. 마름모 크기 $K$에 따른 채굴 비용은 $K^2 + (K+1)^2$ 이다.
    2. 총수익은 마름모 안의 금 개수 $\times M$ 이며, 총수익 $\ge$ 채굴 비용일 때만 손해를 보지 않은 것으로 간주한다.
    3. 격자 밖으로 마름모가 나가도 상관없지만 밖에는 금이 없다.
- **출력 항목**: 손익분기점을 넘기는 선에서 캘 수 있는 최대 금의 개수.

## 핵심 포인트 (BFS)

- **완전 탐색 (Brute-Force)**: 마름모의 중심점이 될 수 있는 모든 좌표 $(i, j)$와, 가능한 모든 마름모의 크기 $K$에 대해 전부 탐색을 진행해야 합니다.
- **BFS를 통한 영역 확장**: 특정 중심점에서 상하좌우로 1칸씩 퍼져나가는 과정은 가중치가 없는 최단 경로 탐색과 같으므로 **BFS(너비 우선 탐색)**를 사용하여 거리가 $K$ 이하인 영역을 정확히 덮을 수 있습니다.

### 동작 원리

- **탐색 범위 설정**: 격자의 모든 칸 $(i, j)$를 중심점으로 잡고, 마름모의 크기 $K$를 0부터 $K$까지 늘려가며 완전 탐색을 진행한다.
- **비용 계산**: 현재 $K$에 대한 채굴 비용(`K*K + (K+1)*(K+1)`)을 미리 계산해둔다.
- **BFS 탐색**:
    - 중심점 $(i, j)$를 큐에 넣고 거리를 0으로 설정한다.
    - 큐에서 좌표를 꺼내 상하좌우로 이동하며, 새로운 거리가 $K$ 이하일 때만 큐에 넣고 방문 처리한다.
    - 방문한 칸에 금(1)이 있다면 `goldCnt`를 증가시킨다.
- **수익 검증 및 갱신**: BFS가 끝나면 수집한 금을 돈으로 환산(`goldCnt * M`)하여 채굴 비용보다 크거나 같은지 확인하고, 조건을 만족하면 최댓값(`max`)을 갱신한다.

### 전체 코드

```java
package codetree.simulation01.goldMining;

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
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        for (int k = 0; k <= n; k++) {
            int price = k*k + (k+1)*(k+1);

            // 각 좌표를 중심점으로 삼아 거리가 K 이하인 마름모 영역 탐색
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int goldCnt = map[i][j];
                    boolean[][] visited = new boolean[n][n];
                    Queue<int[]> q = new ArrayDeque<>();

                    visited[i][j] = true;
                    q.add(new int[]{i, j, 0});
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int y = cur[0];
                        int x = cur[1];
                        int d = cur[2];

                        for (int l = 0; l < 4; l++) {
                            int ny = y + dy[l];
                            int nx = x + dx[l];
                            int nd = d + 1;

                            if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                            if (visited[ny][nx] || nd > k) continue;

                            goldCnt += map[ny][nx];
                            visited[ny][nx] = true;
                            q.add(new int[]{ny, nx, nd});
                        }
                    }

                    if (goldCnt * m >= price) {
                        max = Math.max(max, goldCnt);
                    }
                }
            }
        }

        System.out.println(max);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 배열의 각 점을 중심점으로 선택 : $O(N^2)$
- 마름모 크기를 확장 : $O(N)$
- 각 상황마다 BFS를 돌며 최대 $O(N^2)$의 칸을 큐에 넣고 뺀다.

따라서 위 알고리즘의 시간 복잡도는 $O(N^5)$이다. 시간 복잡도가 굉장히 크지만 입력되는 $N$의 최댓값이 20이므로 제한 시간 내에 통과할 수 있다.

---

## 핵심 포인트 (누적합)

- **맨해튼 거리**: 중심 좌표가 $(i, j)$일 때, $K$번 이동해서 갈 수 있는 마름모 영역은 정확히 **중심점과의 맨해튼 거리가 $K$ 이하인 모든 칸**을 나타낸다. 즉, $|r - i| + |c - j| \le K$ 공식 하나로 판별해낼 수 있다.

### 동작 원리

1. **중심점 설정**: 격자의 모든 칸 $(i, j)$를 한 번씩 중심점으로 삼는다.
2. **금과의 거리 계산**: 중심점 $(i, j)$에서 맵의 모든 칸 $(r, c)$를 스캔하며, 금(1)이 있다면 맨해튼 거리 `dist = Math.abs(r - i) + Math.abs(c - j)`를 계산한다.
3. **거리별 금 개수 저장**: `goldAtDist[dist]` 배열을 1 증가시켜, 해당 거리에 금이 몇 개 있는지 저장해둔다.
4. **마름모 확장 시뮬레이션**:
    - 중심점이 고정된 상태에서 $K$를 0부터 $2(N-1)$까지 1씩 늘려가며 탐색한다.
    - $K$가 커질 때마다 `goldAtDist[K]`만큼만 현재 확보한 금(`currentGold`)의 누적합을 구한다.
    - 현재 $K$의 비용($K^2 + (K+1)^2$)과 수익(`currentGold * M`)을 비교하여 손해가 아니라면 최댓값(`maxGold`)을 갱신한다.

### 전체 코드

```java
package codetree.simulation01.goldMining;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 중심점으로부터 거리별 금 개수 저장
                int maxDist = 2 * (n-1);
                int[] goldAtDist = new int[maxDist + 1];

                // 맵을 순회하며 금의 거리 계산
                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {
                        if (map[r][c] == 1) {
                            int dist = Math.abs(r - i) + Math.abs(c - j);
                            goldAtDist[dist]++;
                        }
                    }
                }

                // 마름모 크기를 늘리면서 검사
                int currentGold = 0;
                for (int k = 0; k <= maxDist; k++) {
                    currentGold += goldAtDist[k];

                    int cost = k * k + (k+1) * (k+1);
                    int profit = currentGold * m;

                    if (profit >= cost)
                        max = Math.max(max, currentGold);
                }
            }
        }

        System.out.println(max);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 배열의 각 점을 중심점으로 선택 : $O(N^2)$
- 맵 전체를 순회하며 금의 거리를 한 번씩 계산한다. : $O(N^2)$

따라서 위 알고리즘의 시간 복잡도는 $O(N^4)$이다.
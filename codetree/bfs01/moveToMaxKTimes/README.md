## 문제 요약

- **목표**: 주어진 $N \times N$ 격자에서 특정 위치에서 시작하여 조건에 맞는 최적의 칸으로 이동하는 과정을 최대 K번 반복한 후의 최종 위치를 구한다.
- **조건**
    1. **탐색 조건:** 상하좌우 인접한 칸을 통해 탐색 시작 위치의 값보다 **작은** 곳으로만 이동 가능하다.
    2. **이동 우선순위**: 도달 가능한 모든 칸 중에서 1) 적혀있는 숫자가 가장 큰 곳, 2) 행 번호가 가장 작은 곳, 3) 열 번호가 가장 작은 곳 순서로 우선순위를 따져 단 한 곳으로만 이동한다.
    3. 더 이상 이동할 곳이 없으면 K번을 다 채우지 못해도 즉시 종료한다.
- **출력 항목:** 이동이 종료된 후의 최종 행, 열 위치 (1-based index)

## 핵심 포인트

- **BFS를 통한 도달 가능성(Reachability) 탐색**: 한 번의 이동(Turn)마다 시작점에서 출발하는 새로운 BFS를 돌려, 갈 수 있는 모든 후보 칸을 찾아야 한다.
- **우선순위 정렬 기준 (Tie-breaker)**: 후보 칸들 중 최적의 1칸을 뽑아내는 기준이 3단계로 얽혀 있습니다. 이를 중첩 `if`문 혹은 `Comparable` 인터페이스를 구현한 클래스를 활용하여 해결할 수 있다.
- **상태 갱신**: 한 번의 이동이 완료되면, 그 목적지가 다음 턴의 새로운 '시작점'이 되도록 변수들을 정확히 갱신해야 한다.

### 동작 원리

1. **변수 초기화**: 격자 정보와 시작 위치, 이동 횟수 K를 입력받는다. (이때 좌표는 배열 다루기 편하도록 0-based index로 맞춘다).
2. **시뮬레이션 반복 (최대 K번)**:
    - **BFS 큐 초기화**: 시작점을 큐에 넣고, 방문 배열을 초기화
    - **후보 탐색**: 큐에서 칸을 꺼내 상하좌우를 살펴 갈 수 있는 칸(아직 방문 안 했고, **시작점의 값보다 작은 곳**)이라면 큐에 넣고 방문 처리
    - **최적 목적지 갱신**: 꺼낸 칸이 시작점이 아니라면, 현재까지의 최고 후보(`bestCell`)와 비교하여 더 우선순위가 높으면 새로 갱신
3. **이동 및 조기 종료 판별**:
    - BFS가 끝난 후, 최고 후보가 한 번도 갱신되지 않았다면(이동할 곳이 없다면) 즉시 루프를 탈출(`break`)
    - 이동할 곳이 있다면, 시작점의 좌표와 값을 해당 후보의 정보로 갱신하고 다음 턴 준비
4. **결과 출력**: 루프 종료 후 최종 좌표에 1을 더해 1-based index로 출력

### 전체 코드

```java
package codetree.bfs01.moveToMaxKTimes;

import java.io.*;
import java.util.*;

class Cell implements Comparable<Cell> {
    int r, c, val;

    public Cell(int r, int c, int val) {
        this.r = r;
        this.c = c;
        this.val = val;
    }

    @Override
    public int compareTo(Cell o) {
        // 1순위. 적혀있는 숫자가 클수록 (내림차순)
        if (this.val != o.val) return o.val - this.val;
        // 2순위. 행 번호가 작을수록 (오름차순)
        if (this.r != o.r) return this.r - o.r;
        // 3순위. 열 번호가 작을수록 (오름차순)
        return this.c - o.c;
    }
}

public class Main {
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int startR = Integer.parseInt(st.nextToken()) - 1;
        int startC = Integer.parseInt(st.nextToken()) - 1;

        for(int count = 0; count < k; count++) {
            Queue<Cell> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][n];

            Cell bestCell = null;   // 이번 턴에 이동할 최적의 칸
            int startVal = map[startR][startC];     // 탐색 시작점

            q.add(new Cell(startR, startC, startVal));
            visited[startR][startC] = true;

            while (!q.isEmpty()) {
                Cell cur = q.poll();

                // 시작점이 아니면 최적의 후보 갱신
                if (cur.r != startR || cur.c != startC) {
                    if (bestCell == null || cur.compareTo(bestCell) < 0) {
                        bestCell = cur;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    int nextR = cur.r + dr[i];
                    int nextC = cur.c + dc[i];
                    if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n) continue;

                    if (!visited[nextR][nextC] && map[nextR][nextC] < startVal) {
                        visited[nextR][nextC] = true;
                        q.add(new Cell(nextR, nextC, map[nextR][nextC]));
                    }
                }
            }

            // 이동할 수 있는 칸이 없으면 시뮬레이션 종료
            if (bestCell == null) break;

            // 이동 처리 (다음 턴의 시작점)
            startR = bestCell.r;
            startC = bestCell.c;
        }

        System.out.println((startR+1) + " " + (startC+1));
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 한 번의 이동마다 BFS를 수행하기에 최악의 경우 격자 전체($N\times N$)를 탐색한다.
- 이러한 탐색을 최대 K번 수행한다.

따라서 위 알고리즘의 시간 복잡도는 $O(K \times N^2)$이다.
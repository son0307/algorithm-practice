## 문제 요약

- **목표: $N \times N$**바다에서 $M$마리의 아기 바다거북이 화산 폭발을 피해 안식처$(N-1, N-1)$로 이동하는데 걸리는 시간 구하기
- **조건:**
    1. **거북이 이동:** 목적지까지의 최단 경로를 탐색하며, 산호초와 다른 거북은 장애물로 간주한다. 최단 경로가 여러 개인 경우, **우, 하, 좌, 상 순서로 이동**한다.
    2. **화산 압력:** 매 턴 모든 화산의 마그마 압력이 10씩 증가한다.
    3. **분출 및 연쇄 반응:** (현재 압력 + 외부에서 온 열기)가 임계치 이상이면 화산이 폭발한다. 열기는 상하좌우로 뻗어가며 1칸마다 절반(내림)으로 깎인다. 이 열기로 인해 다른 화산이 연쇄 폭발할 수 있다.
    4. **화석화 및 초기화:** 폭발 종료 후 열기가 20 이상인 칸의 거북이는 화석이 되며, 폭발한 화산의 압력은 0으로, 맵의 열기 정보는 모두 초기화된다.
- **출력 항목:** 각 거북이가 안식처에 도착한 시간. 도착하지 못했거나 죽었다면 -1을 출력.

## 핵심 포인트

- **역방향 BFS:** 이 문제를 거북이 → 안식처 방향으로 BFS를 수행하면 이동 방향 우선 순위로 인해 처리가 매우 복잡해진다. 반면, 안식처를 시작 지점으로 삼아 모든 구역으로 BFS를 수행하면서 **목적지까지의 거리**를 남겨두면 거북이 입장에서는 **우, 하, 좌, 상 순서대로 주변 4칸을 둘러보고 숫자가 1 작은 곳으로 이동**하기만 하면 된다.
- **순차적 탐색 상태 동기화:** 거북이들이 이동할 때 앞 차례의 거북이가 이동 결과가 다음 거북이들의 이동 경로에 영향을 끼치게 된다. 따라서, 거북이들의 이동 결과가 실시간으로 반영된다.
- **큐 기반의 연쇄 반응 처리:** 폭발 조건을 만족한 화산을 큐에 먼저 넣어두고, 하나씩 꺼내가면서 열기를 퍼뜨린 뒤 새로 조건을 만족한 화산들을 다시 큐에 넣는 방식으로 해결하는 것이 가장 안전하다.

### 동작 원리

1. **거북이 이동**
    1. 살아있는 거북이를 대상으로 ID 순서대로 처리한다.
    2. 목적지$(N-1, N-1)$에서 시작하는 BFS를 돌려 맵 전체에 목적지까지의 거리를 나타내는 이정표(`distanceMap`)을 만든다. (이 때, 산호초와 다른 거북이들은 지나갈 수 없는 장애물로 처리한다.)
    3. 거북이는 현재 위치에서 4방향을 (우, 하, 좌, 상) 순서로 살피며, 거리가 정확히 1 줄어드는 칸으로 이동한다. 목적지에 도착하면 맵에서 지우고 도착 시간을 기록한다.
2. **마그마 압력 증가:** 모든 화산의 `pressure`을 10 증가시킨다.
3. **분출 및 연쇄 폭발**
    1. `pressure >= capacity`인 화산들을 큐에 넣고 폭발 여부(`exploded`)를 `true`로 체크한다.
    2. 큐에서 화산을 꺼내 상하좌우 십자 방향으로 열기를 퍼뜨리며 `heatMap`에 누적시킨다. (산호초를 만나면 전파 중지)
    3. 모든 화산을 다시 순회하며, (기존 압력 + 현재 칸에 누적된 외부 열기)가 임계치를 넘었는데 아직 폭발하지 않은 화산이 있으면 이들을 큐에 넣어 연쇄 폭발을 처리한다.
4. **화석화 및 초기화**
    1. 완성된 `heatMap`을 기반으로, 거북이가 위치한 칸의 열기가 20 이상이면 `alive = false`로 만들어 화석화시킨다. 화석화된 거북이는 맵에 장애물로 남게 된다.
    2. 이번 턴에 폭발한 화산들의 압력만 0으로 초기화한다.

### 전체 코드

```java
package codetree.p202601;

import java.io.*;
import java.util.*;

class Turtle {
    int c;
    int r;
    boolean alive;

    public Turtle(int r, int c) {
        this.r = r;
        this.c = c;
        this.alive = true;
    }
}

class Volcano {
    int num;
    int c;
    int r;
    int capacity;
    int pressure;

    public Volcano(int num, int r, int c, int capacity) {
        this.num = num;
        this.c = c;
        this.r = r;
        this.capacity = capacity;
        this.pressure = 0;
    }
}

public class Main {
    static int[] dR = {0, 1, 0, -1};
    static int[] dC = {1, 0, -1, 0};

    // 현재 거북이 위치에서 (N-1, N-1)까지 이동하는 거리 탐색
    public int[][] makeDistanceMap(int[][] map, int curR, int curC) {
        int N = map.length;
        int[][] distanceMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(distanceMap[i], -1);
        }

        distanceMap[N-1][N-1] = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{N-1, N-1});

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int c = current[0];
            int r = current[1];

            for (int i = 0; i < 4; i++) {
                int nC = c + dC[i];
                int nR = r + dR[i];

                if (nC < 0 || nR < 0 || nC >= N || nR >= N) continue;
                if (distanceMap[nR][nC] != -1) continue;
                if (map[nR][nC] != 0 && !(nR == curR && nC == curC)) continue;

                distanceMap[nR][nC] = distanceMap[r][c] + 1;
                q.add(new int[]{nC, nR});
            }
        }

        return distanceMap;
    }

    // 경로들 중 우측부터 살펴보며 경로 선택 후 이동
    public int[] moveTurtle(int[][] distanceMap, int curR, int curC) {
        if (distanceMap[curR][curC] != -1) {
            int N = distanceMap.length;
            for (int i = 0; i < 4; i++) {
                int nR = curR + dR[i];
                int nC = curC + dC[i];

                if (nC < 0 || nR < 0 || nC >= N || nR >= N) continue;

                if (distanceMap[curR][curC] - distanceMap[nR][nC] == 1)
                    return new int[]{nR, nC};
            }
        }
        return new int[]{curR, curC};
    }

    // 열기 맵 만들기
    public int[][] makeHeatMap(int[][] map, int volcanoNum, Volcano[] volcanoes) {
        int N = map.length;
        int[][] heatMap = new int[N][N];
        Queue<Volcano> qv = new LinkedList<>();
        boolean[] exploded = new boolean[volcanoNum];

        for (int i = 0; i < volcanoNum; i++) {
            if (volcanoes[i].pressure >= volcanoes[i].capacity) {
                qv.add(volcanoes[i]);
                exploded[i] = true;
            }
        }

        while (!qv.isEmpty()) {
            Volcano v = qv.poll();
            heatMap[v.r][v.c] += v.capacity;

            for (int j = 0; j < 4; j++) {
                int heat = v.capacity / 2;
                int step = 1;

                while (heat > 0) {
                    int nR = v.r + dR[j] * step;
                    int nC = v.c + dC[j] * step;

                    if (nC < 0 || nR < 0 || nC >= N || nR >= N) break;
                    if (map[nR][nC] == 1) break;

                    heatMap[nR][nC] += heat;
                    heat /= 2;
                    step++;
                }
            }

            for (int i = 0; i < volcanoNum; i++) {
                if (volcanoes[i].pressure + heatMap[volcanoes[i].r][volcanoes[i].c] >= volcanoes[i].capacity && !exploded[i]) {
                    qv.add(volcanoes[i]);
                    exploded[i] = true;
                }
            }
        }

        for (int i = 0; i < volcanoNum; i++) {
            if (exploded[i]) volcanoes[i].pressure = 0;
        }

        return heatMap;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int turtleNum = Integer.parseInt(st.nextToken());
        int volcanoNum = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] result = new int[turtleNum];
        Arrays.fill(result, -1);

        Turtle[] turtles = new Turtle[turtleNum];
        for (int i = 0; i < turtleNum; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            turtles[i] = new Turtle(r, c);
            map[r][c] = 2;
        }

        Volcano[] volcanoes = new Volcano[volcanoNum];
        for (int i = 0; i < volcanoNum; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());
            volcanoes[i] = new Volcano(i, r, c, capacity);
        }

        Main m = new Main();
        int count = 0;

        while (count < 100) {
            count++;

            // 1단계. 거북이 이동
            for (int i = 0; i < turtleNum; i++) {
                Turtle t = turtles[i];
                if (!t.alive) continue;

                int[][] distanceMap = m.makeDistanceMap(map, t.r, t.c);
                int[] moved = m.moveTurtle(distanceMap, t.r, t.c);

                map[t.r][t.c] = 0;

                if(moved[0] == N-1 && moved[1] == N-1) {
                    result[i] = count;
                    t.alive = false;
                } else {
                    map[moved[0]][moved[1]] = 2;
                    t.r = moved[0];
                    t.c = moved[1];
                }
            }

            // 2단계. 압력 증가
            for (int i = 0; i < volcanoNum; i++) {
                volcanoes[i].pressure += 10;
            }

            // 3단계. 분출 및 연쇄 반응
            int[][] heatMap = m.makeHeatMap(map, volcanoNum, volcanoes);

            // 4단계. 바다거북 화석화
            for (int i = 0; i < turtleNum; i++) {
                if (turtles[i].alive && heatMap[turtles[i].r][turtles[i].c] >= 20)
                    turtles[i].alive = false;
            }
        }

        for (int r : result)
            System.out.println(r);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

한 턴 마다 아래의 작업을 수행한다.

- **거북이 이동: $M$**마리의 거북이가 매 턴 최대 $N \times N$ 크기의 격자를 BFS 탐색 : $O(M\times N^2)$
- **화산 폭발: $K$**개의 화산이 폭발할 때 상하좌우로 뻗어가며 맵 끝까지 탐색 후 화산들을 순회하며 상태 체크 : $O(K \times N) + O(K^2)$

따라서 최종적으로 위 알고리즘의 시간 복잡도는 $O(Turns \times (M \cdot N + K \cdot N + K^2))$ 이다.
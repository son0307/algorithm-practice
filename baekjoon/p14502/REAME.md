## 문제 요약

- **목표**: $N \times M$ 크기의 연구소에 정확히 3개의 벽을 추가로 세워, 바이러스가 퍼지지 않는 '안전 영역'의 크기를 최대로 만든다.
- **조건**:
    1. 바이러스는 상하좌우 인접한 빈 칸으로 계속 퍼져나간다.
    2. 무조건 3개의 벽을 세워야 한다.
- **출력 항목**: 얻을 수 있는 안전 영역의 최대 크기

## 핵심 포인트

- **조합 (Combination)과 DFS**: 연구소의 빈 칸 중 무작위로 3개를 골라 벽을 세우는 모든 경우의 수를 백트래킹을 통해 완전 탐색해야 한다.
- **1차원 인덱싱 (1D Indexing)**: 2차원 배열의 좌표 `(y, x)`를 `i = y * M + x` 형태의 1차원 인덱스로 다루면, 2중 `for`문 없이도 2차원 배열을 간단히 탐색할 수 있다.
- **상태 복제 및 BFS:** 벽을 3개 세운 ‘가상 시나리오’마다 바이러스를 퍼뜨려야 하므로, 원본 지도를 훼손하지 않기 위해 맵을 깊은 복사(Deep Copy)한 뒤 큐를 이용해 BFS 탐색을 진행해야 한다.

### 동작 원리

- **초기화 및 바이러스 수집**: 지도를 입력받으면서 2(바이러스)인 위치의 좌표를 미리 `Queue`에 모두 담아둔다.
- **벽 세우기 (DFS)**:
    - `lastPos`부터 시작해 맵 전체를 1차원 인덱스로 순회한다.
    - 빈 칸(0)을 발견하면 벽(1)으로 바꾸고, 재귀 호출을 통해 다음 벽을 세운다. 탐색이 끝나면 다시 빈 칸(0)으로 되돌려놓는다(백트래킹).
- **바이러스 확산 (BFS)**:
    - 벽이 정확히 3개 세워지면(`cnt == 3`), 맵과 바이러스 큐를 복사한다.
    - 큐가 빌 때까지 바이러스를 상하좌우 인접한 빈 칸(0)으로 퍼뜨린다(2로 변경).
- **안전 영역 계산 및 갱신**:
    - 확산이 모두 끝난 복사된 맵을 순회하며 빈 칸(0)의 개수를 센다.
    - 센 개수가 기존의 `max_safe`보다 크다면 값을 갱신한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

class Virus {
    int x;
    int y;

    public Virus(int y, int x) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    int max_safe = Integer.MIN_VALUE;

    public void dfs(int[][] field, Queue<Virus> queue, int lastPos, int cnt) {
        // 벽을 3개 다 세운 경우
        if (cnt == 3) {
            Queue<Virus> q = new LinkedList<>(queue);
            int[][] f = new int[field.length][field[0].length];

            // 연구소 맵 깊은 복사
            for (int i = 0; i < field.length; i++)
                f[i] = Arrays.copyOf(field[i], field[i].length);

            int[] dy = {-1, 1, 0, 0};
            int[] dx = {0, 0, -1, 1};

            // BFS를 통한 바이러스 확산 시뮬레이션
            while(!q.isEmpty()) {
                Virus virus = q.poll();
                for (int i = 0; i < 4; i++) {
                    int newY = virus.y + dy[i];
                    int newX = virus.x + dx[i];

                    if (newY < 0 || newY >= f.length || newX < 0 || newX >= f[0].length) continue;
                    if (f[newY][newX] == 0) {
                        f[newY][newX] = 2;
                        q.add(new Virus(newY, newX));
                    }
                }
            }

            // 안전 영역(0) 개수 카운트
            int cur_safe = 0;
            for (int i = 0; i < f.length; i++) {
                for (int j = 0; j < f[i].length; j++) {
                    if (f[i][j] == 0) cur_safe++;
                }
            }

            // 최댓값 갱신
            max_safe = Math.max(cur_safe, max_safe);
            return;
        }

        // 1차원 인덱싱을 통한 조합 탐색
        for (int i = lastPos; i < field.length * field[0].length; i++) {
            int y = i / field[0].length;
            int x = i % field[0].length;

            if (field[y][x] == 0) {
                field[y][x] = 1;
                dfs(field, queue, i + 1, cnt + 1);
                field[y][x] = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] field = new int[n][m];
        Queue<Virus> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                field[i][j] = Integer.parseInt(st.nextToken());

                // 초기 바이러스들의 위치 저장
                if (field[i][j] == 2)
                    q.add(new Virus(i, j));
            }
        }

        Main main = new Main();
        main.dfs(field, q, 0, 0);

        System.out.println(main.max_safe);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 연구소의 최대 크기는 $8 \times 8 = 64$칸이다. 이 중에서 3칸을 뽑아 벽을 세우는 경우의 수의 최댓값은 $_{64}C_3 =$ $\frac{64 \times 63 \times 62}{3 \times 2 \times 1} = 41,664$이다.
- 각 경우마다 바이러스 확산을 위한 BFS를 수행하는데, 배열을 탐색하는데 걸리는 시간은 $N \times M$인 최대 64번의 연산이 필요하다.
- 따라서 총 연산 횟수는 약  $41,664 \times 64 \approx 266만$번이다.

최종적으로, 이 알고리즘의 시간 복잡도는 $O((NM)^3 \times NM)$이다.
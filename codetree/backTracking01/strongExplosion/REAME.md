## 문제 요약

- **목표**: $N \times N$ 격자에 주어진 폭탄 위치들에 각각 3가지 종류의 폭탄 중 하나를 설치하여, 가장 많은 영역을 초토화(파괴)시키는 경우의 수를 찾는다.
- **조건**:
    1. 폭탄 1: 중심 포함 위아래 수직으로 총 5칸 폭발
    2. 폭탄 2: 중심 포함 상하좌우 십자가로 총 5칸 폭발
    3. 폭탄 3: 중심 포함 대각선(X자)으로 총 5칸 폭발
    4. 폭발 영역은 서로 겹칠 수 있으며, 겹치더라도 1개의 초토화 영역으로 계산한다.
- **출력 항목**: 초토화시킬 수 있는 최대 영역의 칸 수

## 핵심 포인트

- **백트래킹 (Backtracking)**: 각 폭탄 위치마다 3가지 폭탄을 모두 놓아보는 모든 경우의 수($3^B$)를 탐색해야 한다. 폭탄 개수가 최대 10개이므로 $3^{10} = 59,049$가지의 경우를 탐색하기에 충분하다.
- **중첩(Overlap) 영역 처리**: 폭발 영역이 겹쳤을 때 단순히 `boolean` 값으로 `true` 처리를 해버리면, 백트래킹으로 되돌아갈 때(상태 복구 시) 다른 폭탄이 터뜨린 영역까지 `false`로 지워버리는 치명적인 버그가 발생한다. 반드시 **정수형 배열을 사용해 폭발 횟수를 누적(++, --)** 해야 한다.
- **모형(Shape)의 추상화**: 3가지 폭탄의 폭발 방향을 `dy`, `dx` 배열로 미리 추상화해두면, 길고 반복되는 코드를 획기적으로 줄일 수 있다.

### 동작 원리

1. **초기화 및 폭탄 수집:** 맵 정보를 입력받으며 1(폭탄)인 좌표를 모두 리스트에 저장한다.
2. **백트래킹 시작(`dfs`)**
    1. 0번째 폭탄부터 시작하여 1번, 2번, 3번 폭탄을 차례대로 맵에 적용한다.
    2. 특정 폭탄을 적용할 때, 해당 폭탄의 모양에 따라 격자를 벗어나지 않는다면 `damaged`배열의 값을 1 증가시킨다.
    3. 다음 폭탄으로 넘어가는 재귀 호출을 수행한다.
    4. 탐색을 마치고 돌아오면 적용했던 폭탄 영역을 복원한다.
3. **최대 영역 갱신:**
    1. 준비된 모든 폭탄을 배치했으면, 전체 격자를 순회하며 `damaged`배열의 값이 1 이상인 칸의 개수를 센다.
    2. 기존의 최댓값과 비교하여 갱신한다.
4. **결과 출력:** 모든 경우의 수 탐색이 끝나면 기록된 최댓값을 출력한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    static int max = Integer.MIN_VALUE;
    static int n;
    static int[][] damaged;
    static List<int[]> bombs = new ArrayList<>();

    static int[][][] bombShapes = {
            {{-2, 0}, {-1, 0}, {1, 0}, {2, 0}},     // 1번 폭탄
            {{-1, 0}, {1, 0}, {0, -1}, {0, 1}},     // 2번 폭탄
            {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}    // 3번 폭탄
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        damaged = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                if (Integer.parseInt(st.nextToken()) == 1)
                    bombs.add(new int[]{i,j});
            }
        }

        dfs(0);

        System.out.println(max);
    }

    static void dfs(int idx) {
        if (idx == bombs.size()) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (damaged[i][j] > 0) count++;
                }
            }
            max = Math.max(max, count);
            return;
        }

        int[] currentBomb = bombs.get(idx);
        int r = currentBomb[0];
        int c = currentBomb[1];

        // 3가지 폭탄 순서대로 놓기
        for (int t = 0; t < 3; t++) {
            boom(r, c, t, 1);

            dfs(idx + 1);

            boom(r, c, t, -1);
        }
    }

    public static void boom(int r, int c, int t, int delta) {
        damaged[r][c] += delta;

        for (int i = 0; i < 4; i++) {
            int nr = r + bombShapes[t][i][0];
            int nc = c + bombShapes[t][i][1];

            if (nr >= 0 && nc >= 0 && nr < n && nc < n)
                damaged[nr][nc] += delta;
        }
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 각 폭탄의 위치마다 3종류의 폭탄을 배치할 수 있다. : $O(3^B)$
- 모든 폭탄을 배치했을 때, 배열 전체를 순회하며 영향을 받은 칸의 개수를 카운트한다. : $O(N^2)$

최종적으로 위 알고리즘의 시간 복잡도는 $O(3^B \times N^2)$이다.
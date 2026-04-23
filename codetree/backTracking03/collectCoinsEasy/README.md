## 문제 요약

- **목표**: 시작점(S)에서 출발하여 번호가 증가하는 순서대로 최소 3개의 동전을 수집한 뒤, 도착점(E)으로 가는 최소 이동 횟수를 구한다.
- **조건**:
    1. 격자에는 벽이 없고 빈 공간(.), 시작점(S), 도착점(E), 동전(1~9)만 존재한다.
    2. 동전은 최대 9개이며, 최소 3개를 '증가하는 순서'로 먹어야 한다.
- **출력 항목**: 조건을 만족하는 최소 이동 횟수. 불가능하면 -1 출력.

## 핵심 포인트

- **벽이 없는 격자의 최단 거리 (Manhattan Distance)**: 벽이 없기 때문에 A에서 B로 가는 최단 거리는 항상 `|A.r - B.r| + |A.c - B.c|`가 된다. 최단 거리를 구하기 위해 별도의 BFS를 돌릴 필요가 없다.
- **최적의 동전 수는 이미 고정**: 문제에서 "최소 3개"라고 했지만, 4개나 5개를 먹는 경우는 계산할 필요가 없다. 거쳐 가는 경유지가 많아질수록 총거리는 늘어나거나 같아질 뿐, 절대 줄어들지 않기 때문이다. 즉, **무조건 정확히 3개의 동전만 고르는 것이 최적해**.

### 동작 원리

- **좌표 수집**: 격자의 정보를 입력받으며 S, E, 그리고 숫자(동전)들의 행과 열 좌표를 수집한다.
- **예외 처리**: 수집된 동전의 개수가 3개 미만이라면 곧바로 `-1`을 출력하고 종료한다.
- **동전 정렬**: 동전 번호를 오름차순으로 정렬하여 리스트(`sortedCoins`)에 담는다.
- **조합 탐색 (백트래킹)**:
    - 정렬된 동전 중 차례대로 3개를 고르는 조합을 DFS로 생성한다.
    - 3개가 모두 골라지면, `S -> 동전1 -> 동전2 -> 동전3 -> E` 순서로 거리를 구하고 합산한다.
    - 합산된 거리와 기존의 최솟값(`min`)을 비교하여 더 작은 값으로 갱신한다.
- **결과 출력**: 모든 조합의 탐색이 끝나면 기록된 `min` 값을 출력한다.

### 전체 코드

```java
package codetree.backTracking03.collectCoinsEasy;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainDFS {
    static int[] start = new int[2];
    static int[] end = new int[2];
    static int min = Integer.MAX_VALUE;

    static HashMap<Integer, int[]> coins = new HashMap<>();
    static List<Integer> sortedCoinKeys = new ArrayList<>();
    static int[] selectedCoins = new int[3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = line.charAt(j);

                if (c == 'S') start = new int[]{i, j};
                else if (c == 'E') end = new int[]{i, j};
                else if (c != '.') coins.put(c - '0', new int[]{i, j});
            }
        }

        // 코인 수가 3개 미만이면 해결 불가
        if (coins.size() < 3) {
            System.out.println(-1);
            return;
        }

        // 동전 번호 오름차순 정렬
        sortedCoinKeys = coins.keySet().stream().sorted().collect(Collectors.toList());

        dfs(0, 0);

        System.out.println(min);
    }

    static void dfs(int cnt, int idx) {
        if (cnt == 3) {
            int totalDistance = 0;
            totalDistance += getDistance(start, coins.get(selectedCoins[0]));
            totalDistance += getDistance(coins.get(selectedCoins[0]), coins.get(selectedCoins[1]));
            totalDistance += getDistance(coins.get(selectedCoins[1]), coins.get(selectedCoins[2]));
            totalDistance += getDistance(coins.get(selectedCoins[2]), end);

            min = Math.min(min, totalDistance);
            return;
        }

        for (int i = idx; i < sortedCoinKeys.size(); i++) {
            selectedCoins[cnt] = sortedCoinKeys.get(i);
            dfs(cnt + 1, i + 1);
        }
    }

    static int getDistance(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 주어진 동전 $N$개에서 3개의 동전을 고르는 경우의 수는 $O(_NC_3)$이다.
- 선택된 동전까지의 거리를 구하는데 소요되는 시간은 상수에 가깝기 때문에 신경쓰지 않아도 된다.

따라서, 위 알고리즘의 시간 복잡도는 $O(_NC_3)$이다.
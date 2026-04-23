## 문제 요약

- **목표**: $N$개의 점 중 $M$개를 골랐을 때, 선택된 점들 사이의 '최대 거리(유클리디안 거리의 제곱)'를 구하고, 이 최대 거리가 '최소'가 되는 조합을 찾는다.
- **조건**: $2 \le M \le N \le 20$
- **출력 항목**: 조건을 만족하는 최소 거리 제곱 값.

## 핵심 포인트

- **조합 탐색** : 백트래킹을 통해 $N$개의 점 중 $M$개를 고르는 모든 경우의 수($_NC_M$)를 찾아낸다.
- **가지치기** : 굳이 $M$개를 모두 고를 필요 없이 점을 선택할 때마다 현재까지 선택된 점과의 거리를 구해 가망이 없는 조합은 조기에 탐색을 종료한다.

### 동작 원리

1. **데이터 초기화**: `Point` 객체를 생성하여 각 점의 좌표를 저장한다.
2. **백트래킹 시작 (`selectM`)**:
    - 파라미터로 `cnt`(고른 개수), `idx`(탐색 인덱스), `currentMax`(현재까지 고른 점들 사이의 최대 거리)를 전달한다.
3. **강력한 가지치기 (Branch and Bound)**:
    - `currentMax >= min` 이라면, 이미 최솟값 갱신에 실패한 경로이므로 남은 점을 고르지 않고 즉시 `return` 하여 탐색을 종료한다.
4. **점 선택 및 거리 갱신**:
    - `points` 리스트에서 새로운 점 `p1`을 고른다.
    - 이미 `selected` 리스트에 들어있는 점(`p2`)들과 `p1` 사이의 거리를 계산하여, 가장 긴 거리를 `newMax`로 갱신한다.
    - 갱신된 `newMax`를 바탕으로 다음 단계(`cnt + 1`)로 재귀 호출한다.
5. **결과 도출**: 무사히 가지치기를 통과하여 `cnt == m`에 도달했다면, `min`을 갱신한다.

### 전체 코드

```java
package codetree.backTracking03.chooseMOutOfNPoints;

import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int min = Integer.MAX_VALUE;
    static List<Point> points = new ArrayList<>();
    static List<Point> selected = new ArrayList<>();

    static class Point{
        int x, y;

        Point(int y, int x) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int py = Integer.parseInt(st.nextToken());
            int px = Integer.parseInt(st.nextToken());
            points.add(new Point(py, px));
        }

        selectM(0, 0, 0);

        System.out.println(min);
    }

    static void selectM(int cnt, int idx, int currentMax) {
        // 이미 현재 거리가 전역 최솟값 이상이라면 더 이상 탐색할 필요 없음
        if (currentMax >= min) return;

        // m개를 골랐으면 최솟값 확인 후 갱신
        if (cnt == m) {
            min = Math.min(min, currentMax);
            return;
        }

        for (int i = idx; i < n; i++) {
            Point p1 = points.get(i);
            int newMax = currentMax;

            // 새로 고른 점과 기존 점들과의 거리 계산
            for (Point p2 : selected) {
                int dx = p1.x - p2.x;
                int dy = p1.y - p2.y;
                int dist = dx * dx + dy * dy;
                newMax = Math.max(newMax, dist);
            }

            // 새로운 점을 선택하고 다음으로 이동
            selected.add(p1);
            selectM(cnt + 1, i + 1, newMax);
            selected.remove(selected.size() - 1);
        }
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $N$개의 점 중 $M$개를 뽑아내는 경우의 수는 $O(_NC_M)$이다.
- 새로 점을 뽑을 때마다 현재까지 선택된 점들과 거리를 측정한다. 이는 최악의 경우 $M$개의 점들과 거리를 측정한다.

따라서, 위 알고리즘의 시간 복잡도는 $O(_NC_M \times M)$이다.
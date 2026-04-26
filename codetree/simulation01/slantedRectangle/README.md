## 문제 요약

- **목표**: $N \times N$ 격자 내에서 대각선 4방향(우상, 좌상, 좌하, 우하)으로 움직이며 만들어지는 '기울어진 직사각형'의 테두리에 적힌 숫자들의 합 중 최댓값을 구한다.
- **조건**:
    1. 반드시 1, 2, 3, 4번 방향 순서대로 순회해야 한다.
    2. 각 방향으로 최소 1번은 움직여야 한다.
    3. 격자 밖으로 넘어가면 안 된다.
- **출력 항목**: 조건을 만족하는 기울어진 직사각형 테두리 합의 최댓값.

## 핵심 포인트

- **직사각형의 기하학적 성질**: 기울어진 직사각형은 변이 4개지만, 평행한 마주 보는 변의 길이는 동일하다. 즉, **1번 방향의 이동 횟수(너비 $w$)와 3번 방향의 이동 횟수는 같아야 하고, 2번 방향의 이동 횟수(높이 $h$)와 4번 방향의 이동 횟수는 같아야 시작점으로 정확히 돌아오게 된다.** 이 특성을 이용하여 각 꼭짓점을 미리 계산하면 범위를 벗어나는지 아닌지 빠르게 판단할 수 있다.

### 동작 원리

1. **시작점 순회**: 격자의 모든 칸 $(i, j)$를 시작점으로 삼는다.
2. **너비($w$)와 높이($h$) 순회**: 각 시작점에 대해 가능한 1번 방향 이동 횟수 $w$ (1 ~ $N$)와 2번 방향 이동 횟수 $h$ (1 ~ $N$)를 순회합니다.
3. **경계 검사** :
    - 1번 꼭짓점: $(i - w, j + w)$
    - 2번 꼭짓점: $(i - w - h, j + w - h)$
    - 3번 꼭짓점: $(i - h, j - h)$
    - 이 세 꼭짓점만 격자 안에 있다면 4번 꼭짓점(시작점)으로 돌아오는 전체 경로는 무조건 격자 안에 존재한다.
4. **테두리 합산**: 경계를 통과했다면 $w$번, $h$번, $w$번, $h$번씩 4방향 벡터를 타고 이동하며 값을 누적하고 최댓값을 갱신합니다.

### 전체 코드

```java
package codetree.simulation01.slantedRectangle;

import java.io.*;
import java.util.*;

public class Main {
    // 1(우상), 2(좌상), 3(좌하), 4(우하)
    static int[] dy = {-1, -1, 1, 1};
    static int[] dx = {1, -1, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        // 모든 점을 시작점으로 설정
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // 1, 3번 방향 이동횟수, 2, 4번 방향 이동횟수 설정
                for (int w = 1; w < n; w++) {
                    for (int h = 1; h < n; h++) {

                        if (isPossible(i, j, w, h, n))
                            max = Math.max(max, getScore(i, j, w, h, map));
                    }
                }
            }
        }

        System.out.println(max);
    }

    // 만들어진 직사각형이 맵 위에 있는지 검사
    static boolean isPossible(int y, int x, int w, int h, int n) {
        // 1번 방향
        int y1 = y - w, x1 = x + w;
        if (y1 < 0 || y1 >= n || x1 < 0 || x1 >= n) return false;

        // 2번 방향
        int y2 = y1 - h, x2 = x1 - h;
        if (y2 < 0 || y2 >= n || x2 < 0 || x2 >= n) return false;

        // 3번 방향
        int y3 = y2 + w, x3 = x2 - w;
        if (y3 < 0 || y3 >= n || x3 < 0 || x3 >= n) return false;

        return true;
    }

    // 테두리를 따라 숫자 합 계산
    static int getScore(int i, int j, int w, int h, int[][] map) {
        int sum = 0;
        int[] moveCount = {w, h, w ,h};

        int curY = i, curX = j;

        for (int dir = 0; dir < 4; dir++) {
            for (int step = 0; step < moveCount[dir]; step++) {
                curY += dy[dir];
                curX += dx[dir];
                sum += map[curY][curX];
            }
        }

        return sum;
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 모든 중심점 ($N^2)$에 대해 너비 $w$($N)$와 높이$h(N)$를 순회한다.
- 격자 내부에 존재하는 직사각형을 순회할 때는 최대 테두리 길이$(N)$을 4번 체크한다.

따라서, 위 알고리즘의 시간복잡도는 $O(N^4)$이다.
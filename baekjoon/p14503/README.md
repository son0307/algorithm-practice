## 문제 요약

- **목표**: $N \times M$ 크기의 방에서 로봇 청소기가 주어진 작동 규칙(전진, 후진, 반시계 회전)에 따라 움직일 때, 최종적으로 청소하는 칸의 개수를 구한다.
- **조건**:
    1. 현재 칸이 청소되지 않았다면 청소한다.
    2. 주변 4칸 중 청소할 곳이 **없다면**: 바라보는 방향을 유지한 채 한 칸 후진한다. (단, 뒤가 벽이라 후진할 수 없다면 작동을 멈춘다.)
    3. 주변 4칸 중 청소할 곳이 **있다면**: 반시계 방향으로 $90^\circ$ 회전한 뒤, 앞쪽 칸이 청소되지 않은 빈 칸이면 한 칸 전진한다.
- **출력 항목**: 로봇 청소기가 작동을 멈출 때까지 청소한 칸의 총 개수

## 핵심 포인트

- **시뮬레이션 (Simulation)**: 문제에서 요구하는 논리적 규칙과 순서를 하나도 빠짐없이, 정확하게 코드로 옮겨 담는 것이 핵심인 알고리즘 유형이다.
- **방향 배열 (Direction Vectors)**: 북(0), 동(1), 남(2), 서(3) 방향에 맞춰 행(`dy`)과 열(`dx`)의 변화량을 매핑하여 이동과 탐색을 쉽게 처리한다.
- **방향 회전 및 후진 수식**: 현재 방향 `d`를 기준으로 반시계 회전이나 180도 뒤집기(후진)를 조건문이나 모듈러(`%`) 연산으로 깔끔하게 제어해야 한다.

### 동작 원리

1. **초기화**: $N \times M$ 크기의 맵 정보를 `board`에 저장하고, 청소 여부를 체크할 `visited` 배열을 생성한다. 북동남서 순서로 이동을 제어할 `dy`, `dx` 배열을 준비한다.
2. **시뮬레이션 루프 (`while`)**:
    - **1단계 (청소)**: 현재 좌표 `(r, c)`가 방문되지 않았다면 청소(`visited = true`)하고 카운트를 올린 뒤, 루프의 처음으로 돌아간다(`continue`).
    - **주변 탐색**: 1단계에 걸리지 않았다면(이미 청소된 칸이라면), 주변 4방향을 탐색하여 청소되지 않은 빈 칸이 있는지 확인(`hasUncleaned`)한다.
3. **조건 분기**:
    - **2단계 (빈 칸이 없을 때)**: 현재 방향을 유지한 채 뒤쪽 좌표(`r - dy[d]`, `c - dx[d]`)를 계산한다. 그곳이 벽(`1`)이면 루프를 종료(`break`)하고, 벽이 아니면 좌표를 후진시킨다.
    - **3단계 (빈 칸이 있을 때)**: 반시계 방향으로 $90^\circ$ 회전한다. 회전 후 바라보는 앞쪽 좌표가 빈 칸(`0`)이고 아직 청소하지 않았다면, 좌표를 전진시킨다.
4. **결과 출력**: 로봇 청소기가 벽에 막혀 종료된 후, 누적된 `count`를 출력한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        // 북(0), 동(1), 남(2), 서(3)
        int[] dy = {-1, 0, 1, 0};
        int[] dx = {0, 1, 0, -1};
        int count = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][M];
        boolean[][] visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        while(true) {
            // 1. 현재 칸이 청소되지 않았으면 청소
            if (!visited[r][c]) {
                count++;
                visited[r][c] = true;
                continue; // 청소 후 1번으로 돌아감
            }

            // 주변 4칸 중 청소되지 않은 빈 칸 탐색 (boolean 플래그로 최적화)
            boolean hasUncleaned = false;
            for (int i = 0; i < 4; i++) {
                int nr = r + dy[i];
                int nc = c + dx[i];
                if (board[nr][nc] == 0 && !visited[nr][nc]) {
                    hasUncleaned = true;
                    break; // 하나라도 찾으면 더 찾을 필요 없음
                }
            }

            // 2. 청소되지 않은 빈 칸이 없는 경우
            if (!hasUncleaned) {
                // 바라보는 방향의 뒤쪽 칸 (후진)
                int backR = r - dy[d];
                int backC = c - dx[d];
                
                // 2.2. 후진 불가능(벽), 작동 정지
                if (board[backR][backC] == 1) {
                    break;
                }
                // 2.1. 한 칸 후진
                r = backR;
                c = backC;
            }
            // 3. 청소되지 않은 빈 칸이 있는 경우
            else {
                // 3.1. 반시계 방향으로 90도 회전 (모듈러 연산 활용)
                d = (d + 3) % 4;

                // 3.2. 바라보는 방향 앞쪽 칸이 청소되지 않은 칸이면 한 칸 전진
                int frontR = r + dy[d];
                int frontC = c + dx[d];
                
                if (board[frontR][frontC] == 0 && !visited[frontR][frontC]) {
                    r = frontR;
                    c = frontC;
                }
            }
        }

        System.out.println(count);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 로봇 청소기는 한 번 청소한 칸을 다시 청소하지 않으며, 후진을 통해 지나온 길을 다시 밟을 수는 있지만 무한 루프에 빠지지는 않는다.
- 최악의 경우에도 각 칸을 탐색하고 이동하는 횟수는 방의 전체 칸 수에 비례하므로 시간 복잡도는 $O(N \times M)$이다.
## 문제 요약

- **목표**: $N \times M$ 격자 위에 두 가지 종류의 트로미노(3칸짜리 L자 모양, 3칸짜리 일자 모양) 중 하나를 올려놓았을 때, 블록이 덮는 칸의 숫자 합의 최댓값을 구한다.
- **조건**:
    1. 블록은 격자를 벗어날 수 없다.
    2. 블록은 자유롭게 회전하거나 뒤집을 수 있다.
- **출력 항목**: 조건을 만족하는 숫자 합의 최댓값.

## 핵심 포인트

- **완전 탐색 (Brute-Force)**: 격자의 최대 크기가 $200 \times 200$이므로, 모든 칸을 기준점(Pivot)으로 삼아 가능한 모든 블록 모양을 덮어보는 방식으로 시간내에 충분히 해결할 수 있다.
- **도형의 추상화 (상대 좌표)**: L자 모양은 회전과 대칭을 포함해 총 4가지, 일자 모양은 가로/세로 총 2가지 형태가 나온다. 이를 기준점 `(0, 0)`을 바탕으로 상대 좌표(`dy`, `dx`)를 배열로 만들고 반복문을 통해 탐색하는 것으로 처리할 수 있다.

### 동작 원리

- **상수 정의**: 트로미노가 취할 수 있는 6가지 형태(L자 4개, 일자 2개)의 상대 좌표를 미리 3차원 배열로 정의한다.
- **격자 순회**: 격자의 모든 칸 `(i, j)`를 2중 `for`문으로 순회하며 해당 칸을 블록의 '기준점'으로 삼는다.
- **블록 매칭**: 기준점 `(i, j)`에서 6가지 블록 모양을 모두 덮어본다.
- **경계 및 합산 검사**:
    - 블록을 구성하는 3개의 칸 중 하나라도 격자 `N x M` 범위를 벗어나면 해당 블록 모양은 무효 처리(`isPossible = false`)하고 넘어간다.
    - 3칸 모두 격자 안이라면 덮인 숫자를 합산(`sum`)한다.
- **최댓값 갱신**: 유효한 배치였다면, 계산된 `sum`과 기존의 `max`를 비교하여 최댓값을 갱신한다.

### 전체 코드

```java
package codetree.simulation01.tromino;

import java.io.*;
import java.util.*;

public class Main {
    static final int[][][] BLOCKS = {
            {{0, 0}, {0, 1}, {-1, 0}},  // L자 1
            {{0, 0}, {0, 1}, {1, 0}},   // L자 2
            {{0, 0}, {0, -1}, {1, 0}},  // L자 3
            {{0, 0}, {0, -1}, {-1, 0}}, // L자 4
            {{0, 0}, {0, -1}, {0, 1}},  // 일자 (가로)
            {{0, 0}, {-1, 0}, {1, 0}}   // 일자 (세로)
    };

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];

        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        // 완전 탐색
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {

                // 6가지 블록 형태 덮어보기
                for (int k=0; k<6; k++) {
                    int sum = 0;
                    boolean isPossible = true;

                    for (int l=0; l<3; l++) {
                        int ny = i + BLOCKS[k][l][0];
                        int nx = j + BLOCKS[k][l][1];

                        // 범위 벗어나면 무효
                        if (ny < 0 || ny >= n || nx < 0 || nx >= m) {
                            isPossible = false;
                            break;
                        }
                        sum += map[ny][nx];
                    }

                    if (isPossible)
                        max = Math.max(max, sum);
                }
            }
        }

        System.out.println(max);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $N \times M$크기의 격자를 모두 순회하며, 각 칸마다 6가지의 블록을 대입해보고, 각 블록마다 3개의 칸을 확인한다. 따라서 총 연산 횟수는 $N \times M \times 6 \times 3$이다.

따라서, 위 알고리즘의 시간복잡도는 $O(N \times M)$이다.
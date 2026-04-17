## 문제 요약

- **목표**: 8개의 톱니를 가진 4개의 톱니바퀴를 주어진 횟수(K)만큼 회전시킨 후, 최종 톱니바퀴들의 상태를 바탕으로 점수를 계산한다.
- **조건**:
    1. 회전시킬 톱니바퀴의 왼쪽/오른쪽에 맞닿은 극이 서로 다르면, 인접한 톱니바퀴는 반대 방향으로 회전한다.
    2. 극이 같으면 회전은 전달되지 않고 그 즉시 멈춘다.
- **출력 항목**: K번 회전이 모두 끝난 후, 4개 톱니바퀴의 12시 방향(인덱스 0) 극성에 따른 점수 합산.

## 핵심 포인트

- **시뮬레이션 (Simulation)**: 주어진 규칙에 따라 배열의 원소를 시계/반시계 방향으로 밀어내는(Shift) 로직을 정확하게 구현해야 한다.
- **상태 보존 (State Isolation)**: 톱니바퀴 A가 먼저 회전해버려서 변경된 극성 때문에 톱니바퀴 B의 회전 여부가 잘못 판별되는 '상태 오염'을 막아야 한다. 회전 여부를 먼저 전부 판별한 뒤에, 실제 회전을 적용하는 것이 핵심이다.
- **비트맵 또는 배열 인덱스 활용**: 12시 방향이 0번 인덱스일 때, 오른쪽 맞닿은 부분은 2번, 왼쪽 맞닿은 부분은 6번 인덱스라는 것을 명확히 매핑해야 한다.

### 동작 원리

1. **데이터 초기화**: 4개의 톱니바퀴 상태를 2차원 배열 `gears`에 저장한다.
2. **명령어 순회**: K번의 회전 명령을 받으며 시뮬레이션을 수행한다.
3. **회전 방향 탐색 (Check)**:
   명령을 받은 타겟 톱니바퀴를 기준으로, 왼쪽으로 가면서 맞닿은 극이 다르면 반대 방향 회전을 기록하고, 같으면 즉시 탐색을 멈춘다.
   오른쪽 방향도 동일하게 탐색하여 모든 톱니바퀴의 회전 방향을 1(시계), -1(반시계), 0(정지)으로 배열에 기록한다.
4. **일괄 회전 (Rotate)**:
   3단계에서 확정된 방향 배열을 순회하며, 회전해야 하는 톱니바퀴의 배열 요소를 조건에 맞게 한 칸씩 이동(Shift)시킨다.
5. **점수 계산**: 모든 회전이 종료된 후, 각 톱니바퀴의 0번 인덱스가 1(S극)일 경우 $2^i$의 점수를 더하여 최종 점수를 산출한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] gears = new int[4][8];
        for (int i = 0; i < 4; i++) {
            String[] gear = br.readLine().split("");
            for (int j = 0; j < 8; j++) {
                gears[i][j] = Integer.parseInt(gear[j]);
            }
        }

        int k = Integer.parseInt(br.readLine());
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int target = Integer.parseInt(st.nextToken()) - 1; // 0-based index
            int dir = Integer.parseInt(st.nextToken());

            // 1. 각 톱니바퀴의 회전 방향을 미리 결정할 배열 (0: 정지, 1: 시계, -1: 반시계)
            int[] dirs = new int[4];
            dirs[target] = dir;

            // 기준점으로부터 왼쪽 톱니바퀴들 상태 검사
            for (int j = target; j > 0; j--) {
                // 내 9시 방향(6)과 왼쪽 톱니의 3시 방향(2) 비교
                if (gears[j][6] != gears[j - 1][2]) {
                    dirs[j - 1] = -dirs[j]; // 극이 다르면 반대 방향
                } else {
                    break; // 극이 같으면 더 이상 회전 전파 안 됨
                }
            }

            // 기준점으로부터 오른쪽 톱니바퀴들 상태 검사
            for (int j = target; j < 3; j++) {
                // 내 3시 방향(2)과 오른쪽 톱니의 9시 방향(6) 비교
                if (gears[j][2] != gears[j + 1][6]) {
                    dirs[j + 1] = -dirs[j]; // 극이 다르면 반대 방향
                } else {
                    break; // 극이 같으면 전파 중지
                }
            }

            // 2. 결정된 방향 배열을 바탕으로 일괄 회전 적용
            for (int j = 0; j < 4; j++) {
                if (dirs[j] == 1) { // 시계 방향 회전
                    int last = gears[j][7];
                    for (int x = 7; x > 0; x--) {
                        gears[j][x] = gears[j][x - 1];
                    }
                    gears[j][0] = last;
                } else if (dirs[j] == -1) { // 반시계 방향 회전
                    int first = gears[j][0];
                    for (int x = 0; x < 7; x++) {
                        gears[j][x] = gears[j][x + 1];
                    }
                    gears[j][7] = first;
                }
            }
        }

        // 3. 점수 계산
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (gears[i][0] == 1) {
                score += (int) Math.pow(2, i);
            }
        }
        
        System.out.println(score);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 회전 명령 횟수 : K번
- 한 번 회전시키는데 걸리는 시간 : O(1)

따라서, 최종적으로 시간복잡도는 $O(K)$가 된다.
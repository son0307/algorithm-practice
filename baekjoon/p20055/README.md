## 문제 요약

- **목표**: 길이가 2N인 컨베이어 벨트 위에서 로봇들을 이동시키며, 내구도가 0인 칸의 개수가 K개 이상이 될 때까지 진행된 총 단계(Round) 수를 구한다.
- **조건**:
    1. **벨트 회전**: 벨트와 그 위에 있는 로봇이 함께 한 칸 회전한다. (내리는 위치인 $N$번째 칸에 도달한 로봇은 즉시 내린다.)
    2. **로봇 이동**: 먼저 올라간 로봇부터 앞 칸으로 이동한다. (단, 앞 칸에 로봇이 없고 내구도가 1 이상이어야 하며, 이동 시 내구도가 1 감소한다.)
    3. **로봇 올리기**: 올리는 위치(1번째 칸)의 내구도가 0이 아니라면 새 로봇을 올린다. (내구도 1 감소)
    4. **종료 조건**: 내구도가 0인 칸이 K개 이상이면 시뮬레이션을 종료한다.
- **출력 항목**: 종료되었을 때의 진행 단계(Round) 수

## 핵심 포인트

- **덱 구조의 순환 로직 구현** : 컨베이어 벨트가 순환하는 구조를 1차원 배열의 값 밀어내기로 구현한다. 여기서는 특정 위치의 값을 확인해야 하기에 덱을 직접 사용할 수 없다. 따라서, 배열로 이러한 로직을 직접 구현해야 한다.
- **역방향 탐색** : 로봇이 이동할 때 ‘먼저 올라간 로봇(출구와 가까운 로봇)’부터 이동해야 하므로, 배열의 뒤에서부터 앞으로 탐색해야 앞 로븟의 이동 결과가 뒤 로봇에게 정상적으로 반영된다.

### 동작 원리

1. **변수 초기화**: 내구도를 담을 2N 크기의 `belt` 배열, 로봇의 존재 여부를 담을 N 크기의 `robot` 배열, 단계 수를 셀 `round`, 내구도 0인 칸의 수를 셀 `zeroCount`를 선언한다.
2. **무한 루프 시작**: 매 루프마다 `round`를 1 증가시킨다.
    1. **벨트와 로봇 회전**:
        - `belt` 배열의 모든 요소를 오른쪽으로 한 칸씩 밀고, 마지막 요소를 0번 인덱스로 가져온다.
        - `robot` 배열 역시 오른쪽으로 한 칸 밀어준다. 이때 내리는 위치(인덱스 N-1)에 도달한 로봇은 즉시 내린다(`false` 처리).
    2. **로봇 스스로 이동**:
        - 출구에 가장 가까운 로봇(인덱스 N-2)부터 0번 인덱스까지 역순으로 검사한다.
        - 현재 칸에 로봇이 있고, 다음 칸이 비어있으며, 다음 칸의 내구도가 1 이상이라면 로봇을 이동시키고 내구도를 1 깎는다. 내구도가 0이 되면 `zeroCount`를 증가시킨다.
        - 이동 후 다시 내리는 위치(N-1)에 도달한 로봇이 있다면 즉시 내린다.
    3. **새로운 로봇 투입**:
        - 올리는 위치(`belt[0]`)의 내구도가 0보다 크면 새 로봇을 올리고(`robot[0] = true`) 내구도를 1 깎는다. 0이 되면 `zeroCount`를 증가시킨다.
    4. **종료 조건 검사**: `zeroCount`가 K 이상이 되면 무한 루프를 탈출하고 현재 `round`를 출력한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] belt = new int[n*2];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n*2; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }
        boolean[] robot = new boolean[n];
        int round = 0;
        int zeroCount = 0;

        while (true) {
            round++;

            // 1.1. 벨트 회전
            int last = belt[belt.length - 1];
            for (int i = belt.length - 2; i >= 0; i--) {
                belt[i + 1] = belt[i];
            }
            belt[0] = last;

            // 1.2. 로봇 회전
            for (int i = robot.length - 2; i >= 0; i--) {
                robot[i + 1] = robot[i];
            }
            robot[0] = false;

            // 로봇이 내리는 위치에 도착하면 즉시 내리기
            if(robot[robot.length - 1]) robot[robot.length - 1] = false;

            // 2. 로봇 스스로 이동
            for (int i = robot.length-2; i >= 0; i--) {
                if (robot[i] && !robot[i+1] && belt[i+1] != 0) {
                    robot[i] = false;
                    robot[i+1] = true;
                    if (--belt[i+1] == 0)
                        zeroCount++;
                }
            }

            // 로봇이 내리는 위치에 도착하면 즉시 내리기
            if(robot[robot.length - 1]) robot[robot.length - 1] = false;

            // 3. 로봇 올리기
            if (belt[0] != 0) {
                robot[0] = true;
                if (--belt[0] == 0)
                    zeroCount++;
            }

            // 4. 내구도가 0인 칸의 개수 검사
            if (zeroCount >= k)
                break;
        }

        System.out.println(round);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 라운드마다 벨트를 회전시키고 로봇을 이동시키는 데 소요되는 시간 : $O(N)$
- 최악의 라운드 수는 최대 내구도 제한과 K 값에 따라 변동

따라서, 최종적으로 시간 복잡도는 $O(Round \times N)$이 된다.
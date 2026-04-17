## 문제 요약

여러 대의 로봇이 정해진 운송 경로를 따라 이동할 때, 같은 시간 같은 좌표에 두 대 이상의 로봇이 모이는 ‘충돌 위험’ 상황의 총 횟수를 구하는 문제

- **이동 규칙** : 1초마다 r 좌표와 c 좌표 1씩 이동. 최단 경로가 여러 가지인 경우, r좌표가 변하는 이동이 우선
- **충돌 정의** : 특정 시간 t에 특정 좌표 (r, c)에 2대 이상의 로봇이 존재하는 경우
- **목표** : 모든 로봇이 운송을 마칠 때까지 발생하는 모든 충돌 위험의 누적 합 계산

## 핵심 포인트

이 문제는 단순히 경로를 겹쳐보는 것이 아니라, ‘시간’이라는 축을 추가로 도입하여 각 초마다의 로봇 좌표를 독립적으로 관리해야 하는 시뮬레이션 문제이다.

1. **시간별 위치 기록 :** 모든 로봇의 이동 경로를 시뮬레이션하며, 각 초(`time` )마다 로봇이 위치한 좌표를 `Map` 또는 3차원 배열에 기록한다.
2. **이동 우선순위** : 2차원 배열에서의 최단 경로의 거리는 한 가지로 정해져있다. 따라서, **상하 이동(r 변화)**를 우선적으로 처리한 뒤, **좌우 이동(c 변화)**를 처리한다.
3. **중복 카운트** : 같은 시간, 같은 위치에 로봇 3대가 모여도 충돌 위험은 1회로 처리하므로, 좌표별 로봇 수를 카운트한 뒤 2 이상인 칸만 체크한다.

### 동작 원리

1. **경로 시뮬레이션 :** 각 로봇(`routes`)에 대해 출발점부터 목적지 리스트를 순회하며 이동 경로를 생성한다.
2. **좌표 기록**
    1. 로봇이 목적지 위치에 도착하거나 이동할 때마다 `time_map`의 해당 시간대에 `map[r][c]++` 를 수행하여 특정 시간의 특정 위치에 로봇을 표시한다.
    2. 0초일 때, 시작점에서도 충돌이 발생할 수 있으므로 시작점에도 위치를 기록한 뒤, 루프를 돌며 r 좌표 → c 좌표 순으로 이동하며 기록한다.
3. **충돌 위험 탐지 :** 모든 로봇의 경로 기록이 끝나면 `time_map` 을 순회하며, 각 시간대별 격자에서 값이 2 이상인 칸의 개수를 모두 더한다.

### 전체 코드

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;

        Map<Integer, int[][]> time_map = new HashMap<>();
        for (int[] route : routes) {
            int time = 0;
            
            // 시작점 로봇 위치 기록
            int[] current = Arrays.copyOf(points[route[0] - 1], 2);
            if (!time_map.containsKey(time)) time_map.put(time, new int[101][101]);
            time_map.get(time)[current[0]][current[1]]++;
            
            // 경로 처리
            for (int i = 1; i < route.length; i++) {
                int[] start = Arrays.copyOf(points[route[i-1] - 1], 2);
                int[] end = Arrays.copyOf(points[route[i] - 1], 2);
                
                // r 좌표 먼저 처리
                while (start[0] != end[0]) {
                    time++;
                    if (start[0] > end[0])
                        start[0]--;
                    else
                        start[0]++;

                    if (!time_map.containsKey(time))
                        time_map.put(time, new int[101][101]);
                    time_map.get(time)[start[0]][start[1]]++;
                }

								// c 좌표 처리
                while (start[1] != end[1]) {
                    time++;
                    if (start[1] > end[1])
                        start[1]--;
                    else
                        start[1]++;
                   
                    if (!time_map.containsKey(time))
                        time_map.put(time, new int[101][101]);
                    time_map.get(time)[start[0]][start[1]]++;
                }
            }
        }

				// 충돌 위험 탐지
        for (int key : time_map.keySet()) {
            int[][] map = time_map.get(key);

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (map[i][j] > 1) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $R$은 로봇의 수, $T$는 최대 이동 시간, $N$은 격자의 크기라고 할 때,
- 모든 로봇의 경로를 기록하는데 걸리는 시간은 $O(R \times T)$이다.
- 경로 기록 후, 시간별 격자를 전부 검사하는데 걸리는 시간은 $O(T \times N^2)$이다.

그러므로, 최종 시간 복잡도는 $O(R \times T + T \times N^2)$이다.
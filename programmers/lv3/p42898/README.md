### 문제 요약

- (1, 1) 위치에서부터 (m, n) 위치까지 이동하는 경우의 수에 1,000,000,007로 나눈 나머지를 구하는 문제
- 단, 물에 잠긴 지역은 피해가야 함.
    - 주어진 예제에서는 (2,2)가 물에 잠겨있고, 이를 피해 (4,3)에 위치한 학교까지 이동하는 경우의 수는 4개이다.

  ![image.png](attachment:b1037f43-f152-4fa9-b9a8-e9e63e1fb08e:image.png)


## 핵심 포인트

단순히 오른쪽과 아래쪽으로만 움직이는 방식으로 DFS와 같은 탐색을 이용하면 그래프가 조금만 커져도 문제를 해결하는데 걸리는 시간이 기하급수적으로 늘어난다. 따라서, 다른 방법으로 탐색을 해야한다. 여기서 생각할 수 있는 방법이 바로 DP이다.

DP로 문제를 해결하기 위해 이 문제를 부분 문제로 나누면 다음과 같이 나눌 수 있다.

![image.png](attachment:fa1909a0-9bba-4312-a24c-754024e63677:image.png)

(x, y)의 위치에서 (m, n)까지 가는 경우의 수 =
(x+1, y)의 위치에서 (m, n)까지 가는 경우의 수 + (x, y+1)의 위치에서 (m, n)까지 가는 경우의 수

이를 바탕으로 함수를 구성하고 코드로 작성하면 다음과 같이 작성할 수 있다.

| 상태 | (x, y) | (x, y)에서 (m, n)까지 가는 경우의 수 |
| --- | --- | --- |
| 종료 조건 1 | x 또는 y가 범위를 벗어났을 경우 | 범위를 벗어나면 반환할 값이 없으므로 0을 반환 |
| 종료 조건 2 | 현재 위치가 물에 잠긴 경우 | 해당 위치로 이동이 불가하므로 0을 반환 |
| 종료 조건 3 | 학교(m, n)에 도달한 경우 | 학교에 도착하는 경로를 찾은 것이므로 1을 반환 |
| 점화식 |  | (x, y) = (x+1, y) + (x, y+1) |

```java
private int count(int x, int y, int w, int h, boolean[][] puddles_map) {
    if (x > w || y > h) return 0;
    if (puddles_map[y][x]) return 0;

    if (x == w && y == h) return 1;
    return count(x, y + 1, w, h, puddles_map) + count(x+1, y, w, h, puddles_map);
}
```

위 함수에 반복되는  부분 문제를 중복해서 해결하지 않도록 메모이제이션을 적용해주면 문제를 해결할 수 있다.

단, 여기서 주의해야 할 점이 있는데 단순히 모든 경우의 수를 구한 다음 1,000,000,007로 나눈 나머지를 계산하는 경우 오답이 발생한다. 계산하는 도중에 값이 너무 커져 int 범위를 벗어나기 때문이다. 이를 해결하기 위해선 다음과 같은 법칙을 이용해야 한다.

```java
(A + B) % C = (A % C + B % C) % C
(A - B) % C = (A % C - B % C) % C
(A * B) % C = (A % C * B % C) % C
```

각 부분 문제의 답을 구할 때 마다 모듈러 연산을 적용하여 값의 범위를 나누는 수 아래로 맞춰야 오버플로우가 발생하지 않는다.

- 전체 코드

    ```java
    import java.util.Arrays;
    
    class Solution {
            private int[][] dp = new int[101][101];
    
        private int count(int x, int y, int w, int h, boolean[][] puddles_map) {
            if (x > w || y > h) return 0;
            if (puddles_map[y][x]) return 0;
    
            if (dp[y][x] != -1) return dp[y][x];
            if (x == w && y == h) return 1;
            return dp[y][x] = (count(x, y + 1, w, h, puddles_map) + count(x+1, y, w, h, puddles_map)) % 1000000007;
        }
    
        public int solution(int m, int n, int[][] puddles) {
            boolean[][] puddles_map = new boolean[n+1][m+1];
            for (int[] puddle : puddles)
                puddles_map[puddle[1]][puddle[0]] = true;
    
            for (int[] row : dp)
                Arrays.fill(row, -1);
    
            count(1, 1, m, n, puddles_map);
    
            return dp[1][1];
        }
    }
    ```
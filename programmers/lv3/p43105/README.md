### 문제 요약

- 삼각형이 주어졌을 때, 위에서부터 아래로 내려가는 경로 중 그 경로에 속한 값들의 합의 최댓값을 구하는 문제
    - 주어진 예제의 답은 30, 경로는 7 - 3 - 8 - 7 - 5이다.

## 핵심 포인트

단순히 현재 위치의 아래에 있는 두 값중 큰 값을 고르는 방법으로는 문제를 해결할 수 없다. 당장 주어진 예제만 보아도 해당 방식을오 구한 경로가 답이 아닌 것을 확인할 수 있다.

이 문제는 부분 문제로 나누어 생각해야 한다.

![image.png](attachment:21b13b67-7111-4a2e-9e23-aa4e648b7095:image.png)

이렇게 현재 위치의 왼쪽 아래 삼각형의 최댓값과 오른쪽 아래 삼각형의 최댓값을 구하고 그 중 최댓값을 선택하면 된다. 이런 방식으로 더 작은 부분 문제들로 분할해가며 문제를 해결하면 된다. 재귀를 정의하면 다음과 같이 나타낼 수 있다.

| 상태 | (x, y) | 삼각형에서 (x, y)에 위치한 점으로부터 시작하여 아래로 내려가는 경로의 최댓값 |
| --- | --- | --- |
| 종료 조건 | y가 삼각형의 높이를 벗어났을 때 | y 좌표가 삼각형의 범위를 벗어나면 반환할 값이 없으므로 0을 반환 |
| 점화식 |  | (x, y) = [x, y] + max( (x, y + 1), (x + 1, y + 1 ) |

이를 바탕으로 재귀를 정의하면 다음과 같이 작성할 수 있다.

```java
private int max(int x, int y, int[][] triangle) {
    if (y == triangle.length) return 0;

    return triangle[y][x] + Math.max(
            max(x, y+1, triangle),
            max(x+1, y+1, triangle)
    );
}
```

여기에 반복되는 부분 문제를 중복으로 계산하지 않도록 메모이제이션을 적용하여 효율성을 높이면 된다.

### 전체 코드

```java
import java.util.Arrays;

class Solution {
    private final int[][] dp = new int[501][501];

    private int max(int x, int y, int[][] triangle) {
        if (dp[x][y] != -1) return dp[x][y];
        if (y == triangle.length) return 0;

        return dp[x][y] = triangle[y][x] + Math.max(
                max(x, y+1, triangle),
                max(x+1, y+1, triangle)
        );
    }

    public int solution(int[][] triangle) {
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        max(0, 0, triangle);

        return dp[0][0];
    }
}
```

해당 문제는 아래에서부터 위로 올라가는 방식으로도 문제를 해결할 수 있다. 아래에서부터 두 자식 숫자 중 큰 숫자를 골라 현재 위치에 더해 값을 갱신하는 방법을 반복하여 간단하게 해결이 가능하다.

![image.png](attachment:55e4bae4-a3b5-4197-8a0b-4dcdf9cc341a:image.png)

### 전체 코드

```java
class Solution {
    public int solution(int[][] triangle) {
        for (int i = triangle.length - 1; i >= 0; i--) {
            for (int j = 0; j < triangle[i].length - 1; j++) {
                triangle[i-1][j] += Math.max(triangle[i][j], triangle[i][j+1]);
            }
        }

        return triangle[0][0];
    }
}
```
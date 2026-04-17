### 문제 요약

- 가로, 세로, 대각선으로 움직일 수 있는 퀸들을 겹치지 않고 움직이도록 배치할 수 있는 경우의 수를 구하는 문제
    - 퀸의 위치 좌표를 통해 상태 공간 트리를 구성하고, 모든 가능한 경우의 수를 탐색

## 핵심 포인트

이 문제는 퀸들을 가능한 위치에 놓으면서 놓을 수 없는 상황이 되면 되돌아가서 위치를 바꿔보며 전부 놓을 수 있는 경우를 찾아야 한다. 즉, 백트래킹 방식을 이용하여 문제를 해결해야 한다.

백트래킹 사용 시 탐색을 중단할 기준이 되는 유망 함수를 구성해야 한다. 이 문제에서 탐색을 중단해야 하는 경우는 퀸을 놓았을 때, 같은 열이나 행, 대각선 경로에 겹치는 퀸이 있는 경우이다. 이를 바탕으로 유망 함수를 설계해보도록 하자.

- 현재 좌표 (x, y)를 기준으로
    - 같은 열에 다른 퀸이 존재하는지 확인. 존재 시 중단
    - 오른쪽 위 → 왼쪽 아래 방향의 대각선 경로에 다른 퀸이 존재하는지 확인. 존재 시 중단
    - 왼쪽 위 → 오른쪽 아래 방향의 대각선 경로에 다른 퀸이 존재하는지 확인. 존재 시 중단

행에 대해서는 위에서부터 아래로 차례대로 한 행씩 검사를 수행할 것이기에 위 세 조건만 제대로 확인하면 된다.

- 같은 열에 다른 퀸이 존재하는지 확인

  같은 열에 퀸을 놓지 않으려면 어떤 열에 이미 퀸이 존재하는지 기억하고 아직 채워지지 않는 열에 퀸을 두면 된다. 즉, 다음과 같이 어떤 열에 퀸이 있는지를 배열에 저장해두면 된다.

  ![image.png](attachment:13aba364-7854-4a52-942c-9b2b6c2f14d7:image.png)

- 오른쪽 위 → 왼쪽 아래 방향의 대각선 경로에 다른 퀸이 존재하는지 확인

  같은 열에 다른 퀸이 존재하는지 확인할 때와 유사하게 오른쪽 위 → 왼쪽 아래 방향으로 향하는 대각선 별로 퀸이 존재하는지 기억하고 아직 채워지지 않는 대각선에 퀸을 두면 된다. 먼저, 아래 예시로 몇 개의 열이 존재하는지 확인해보자.

  ![image.png](attachment:4fae7f15-2117-4525-ae2e-265420e5a8b4:image.png)

  n * n 행렬에서 대각선의 개수는 2 * n - 1개로, 이 예시에서는 총 7개의 대각선이 존재한다. 대각선 별로 T/F를 저장하는 배열 diag1을 만들고, 퀸을 하나 놓을 때마다 해당 대각선을 체크하면 된다. 각 대각선의 인덱스는 왼쪽 위를 (0, 0) 기준으로 생각했을 때, (y좌표 + x좌표)로 구할 수 있다. 위 예시를 기준으로 배열을 나타내면 다음과 같다.

  ![image.png](attachment:f6d123a5-4878-478d-9291-24695f8be623:image.png)

- 왼쪽위 → 오른쪽 아래 방향의 대각선 경로에 다른 퀸이 존재하는지 확인

  위에서 다른 대각선 방향의 퀸을 검사할 때와 같이 수행하면 된다. 단, 좌표 계산 시 조금 유의해야 한다.

  ![image.png](attachment:7b7c2651-49c6-4e1a-a6bb-3279d52183a2:image.png)

  동일하게 총 7개의 대각선이 존재하며, 동일한 방식으로 배열 diag2을 만들고 T/F를 저장하여 퀸 여부를 체크하면 된다. 각 대각선의 인덱스는 왼쪽 위를 (0, 0) 기준으로 생각했을 때, (n - 1 + y좌표 - x좌표)로 구할 수 있다. 위 예시를 기준으로 배열을 나타내면 다음과 같다.

  ![image.png](attachment:4d129412-0821-431c-aed3-745bcb0f5b9c:image.png)


이 세 조건들을 바탕으로 가능한 위치에 퀸을 놓으면서 탐색하다가 퀸을 더 놓지 못하는 경우 되돌아와서 다른 위치에 퀸을 놓아 탐색을 반복하는 백트래킹 방식으로 문제를 해결하면 된다.

### 전체 코드

```java
class Solution {
    int answer = 0;

    public void queen(int col, int n, boolean[] width, boolean[] diagonal1, boolean[] diagonal2) {
        if (col == n) {
            answer++;
            return;
        }
        
        for (int row = 0; row < n; row++) {
            if (!width[row] && !diagonal1[col + row] && !diagonal2[n-1 + col - row]) {
                width[row] = true;
                diagonal1[col + row] = true;
                diagonal2[n-1 + col - row] = true;

                queen(col + 1, n, width, diagonal1, diagonal2);

                width[row] = false;
                diagonal1[col + row] = false;
                diagonal2[n-1 + col - row] = false;
            }
        }
    }

    public int solution(int n) {
        boolean[] width = new boolean[n];
        boolean[] diagonal1 = new boolean[2*n - 1];
        boolean[] diagonal2 = new boolean[2*n - 1];

        queen(0, n, width, diagonal1, diagonal2);

        return answer;
    }
}
```
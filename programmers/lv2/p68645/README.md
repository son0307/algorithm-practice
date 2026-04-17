## 핵심 포인트

삼각형을 한 쪽으로 밀어보면 계단 형식으로 숫자들이 나타나는 것을 확인할 수 있다. 이를 2차원 배열에 적용시키면 이와 같이 나타난다.

| 1 | 0 | 0 | 0 |
| --- | --- | --- | --- |
| 2 | 9 | 0 | 0 |
| 3 | 10 | 8 | 0 |
| 4 | 5 | 6 | 7 |

| 1 | 0 | 0 | 0 | 0 |
| --- | --- | --- | --- | --- |
| 2 | 12 | 0 | 0 | 0 |
| 3 | 13 | 11 | 0 | 0 |
| 4 | 14 | 15 | 10 | 0 |
| 5 | 6 | 7 | 8 | 9 |

이를 통해 규칙을 찾을 수 있다. 왼쪽 위부터 시작해 아래로 숫자를 채우면서 내려가다 끝에 도달하면 오른쪽으로, 오른쪽 끝에 도달하면 왼쪽 위로 이동하며 숫자를 채운다. 이게 반복되는 형태인 것이다. 이 규칙을 파악하면 손쉽게 구현할 수 있다.

## 해결 과정

문제는 다음과 같은 과정으로 해결할 수 있다.

1. 아래로 이동하며 숫자를 순서대로 채운다.
2. 오른쪽으로 이동하며 숫자를 순서대로 채운다.
3. 왼쪽 위 대각선 방향으로 이동하며 숫자를 순서대로 채운다.
4. 1~3 과정을 더 이상 채울 공간이 없을 때까지 반복한다.

### 기본 세팅

```java
int[][] arr = new int[n][n];
int num = 1;
int indX = 0;
int indY = 0;
```

주어진 크기에 맞춰 2차원 배열을 생성하고 다음에 채울 숫자 num에 1을 저장한다. 숫자를 넣을 위치를 지정하는 indX와 indY도 0으로 설정하여 가장 왼쪽 위 모서리부터 채우기 시작하도록 한다.

### 1. 아래로 이동하며 숫자 채우기

가장 먼저 이동할 방향은 아래쪽이므로 이를 먼저 구현한다.

```java
// 아래로 내려가며 수를 채움
while (true) {
    arr[indY][indX] = num++;
    if (indY + 1 >= n || arr[indY + 1][indX] != 0) break;
    indY++;
}
if (n == 1 || arr[indY][indX+1] != 0) break;
indX++;
```

indY를 1씩 증가시키며 숫자를 채워나간다. 끝부분에 도달하여 더 이상 아래쪽으로 나아갈 수 없거나 이미 숫자가 채워진 경우에는 반복문을 벗어난다.
이후, n이 1이거나 오른쪽을 확인하여 더 나아갈 수 없는 경우에는 더 채울 공간이 없는 것을 의미하기에 전체 반복문을 빠져나가도록 한다. 그렇지 않다면 다음 숫자를 채울 위치를 오른쪽 빈칸으로 지정한다.

### 2. 오른쪽으로 이동하며 숫자 채우기

```java
// 오른쪽으로 이동하며 수를 채움
while (true) {
    arr[indY][indX] = num++;
    if (indX + 1 >= n || arr[indY][indX + 1] != 0 ) break;
    indX++;
}
if (arr[indY-1][indX-1] != 0) break;
indX--;
indY--;
```

아래쪽으로 이동하며 수를 채우는 것과 유사한 흐름으로 처리한다. 끝부분에 도달하거나 더 채울 공간이 없는 경우 반복문을 빠져나간다. 이후, 왼쪽 위 대각선 방향의 칸을 검사하여 숫자를 더 채울 수 없는 경우 전체 반복문을 빠져나간다. 채울 수 있다면 다음 숫자를 채울 공간을 왼쪽 위 대각선 방향의 빈칸으로 지정한다.

### 3. 왼쪽 위로 이동하며 숫자 채우기

```java
// 왼쪽 위로 이동하며 수를 채움
while (true) {
    arr[indY][indX] = num++;
    if (arr[indY - 1][indX - 1] != 0) break;
    indX--;
    indY--;
}
if (arr[indY+1][indX] != 0) break;
indY++;
```

앞서 설명한 방법들과 유사한 방식으로 숫자를 채운다.

---

위 과정이 모두 끝나고 난 다음에는 왼쪽 위에서부터 차례대로 answer 배열에 숫자를 채운다.

```java
int[] answer = new int[num-1];
int index = 0;
for(int i=0; i<n; i++){
    for(int j=0; j<n; j++) {
        if(arr[i][j] == 0) continue;
        answer[index++] = arr[i][j];
    }
}
```

### 전체코드

```java
import java.util.Arrays;

class Solution {
    public int[] solution(int n) {
        int[][] arr = new int[n][n];
        int num = 1;
        int indX = 0;
        int indY = 0;

        while(true) {
            // 아래로 내려가며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (indY + 1 >= n || arr[indY + 1][indX] != 0) break;
                indY++;
            }
            if (n == 1 || arr[indY][indX+1] != 0) break;
            indX++;

            // 오른쪽으로 이동하며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (indX + 1 >= n || arr[indY][indX + 1] != 0 ) break;
                indX++;
            }
            if (arr[indY-1][indX-1] != 0) break;
            indX--;
            indY--;

            // 왼쪽 위로 이동하며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (arr[indY - 1][indX - 1] != 0) break;
                indX--;
                indY--;
            }
            if (arr[indY+1][indX] != 0) break;
            indY++;
        }

        int[] answer = new int[num-1];
        int index = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++) {
                if(arr[i][j] == 0) continue;
                answer[index++] = arr[i][j];
            }
        }
        return answer;
    }
}
```
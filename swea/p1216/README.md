# 100×100 글자판의 최장 회문 찾기

## 1. 문제 요약

100×100 글자판에서 가로 또는 세로로 연속된 문자열 중 가장 긴 회문의 길이를 구한다. 회문은 앞에서 읽거나 뒤에서 읽어도 같은 문자열이다. 총 10개의 테스트케이스가 주어지며, 각 결과를 `#테스트케이스번호 길이` 형식으로 출력한다.

## 2. 핵심 포인트

두 방법 모두 **가로와 세로를 각각 검사**한다. 차이는 후보 문자열이 회문인지 판단하는 방식이다.

| 방법 | 회문 판단 방식 |
|---|---|
| 브루트포스 | 후보의 양 끝부터 가운데까지 직접 비교 |
| DP | 양 끝 문자를 비교하고, 안쪽 구간에 저장된 회문 여부를 확인 |

DP에서 `dp[start][end]`는 한 줄의 `start`부터 `end`까지가 회문인지를 뜻한다. 길이 3 이상에서는 다음 관계를 이용한다.

```text
dp[start][end]
= (문자[start] == 문자[end]) && dp[start + 1][end - 1]
```

길이 1은 항상 회문이고, 길이 2는 두 문자가 같으면 회문이다.

## 3. 동작 원리

### 브루트포스

길이를 2부터 100까지 늘려 가며, 해당 길이로 만들 수 있는 모든 가로·세로 구간을 검사한다. 각 구간에서 양 끝 문자를 비교하고, 같으면 한 칸씩 안으로 이동한다. 끝까지 일치하면 회문이므로 `maxLength`를 갱신한다.

### DP

각 행과 열에 대해 **짧은 구간부터 긴 구간 순서로** 회문 여부를 계산한다. 예를 들어 `ABABA`를 검사할 때는 양 끝의 `A`가 같은지 확인하고, 안쪽 `BAB`가 회문인지 저장된 결과를 조회한다.

안쪽 결과를 먼저 계산해야 하므로 길이 1부터 순서대로 처리한다.

## 4. 전체 코드

두 코드는 각각 별도로 제출하는 `Solution` 클래스다.

### 방법 1: 브루트포스

제출한 코드처럼 각 후보의 양 끝을 직접 비교하는 방식이다.

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int t = 1; t <= 10; t++) {
            int tc = sc.nextInt();

            char[][] map = new char[100][100];
            for (int i = 0; i < 100; i++) {
                String s = sc.next();
                for (int j = 0; j < 100; j++) {
                    map[i][j] = s.charAt(j);
                }
            }

            int maxLength = 1;

            for (int len = 2; len <= 100; len++) {
                boolean found = false;

                // 가로 검사
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j <= 100 - len; j++) {
                        boolean isValid = true;

                        for (int k = 0; k < len / 2; k++) {
                            if (map[i][j + k]
                                    != map[i][j + len - k - 1]) {
                                isValid = false;
                                break;
                            }
                        }

                        if (isValid) {
                            found = true;
                        }
                    }
                }

                // 세로 검사
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j <= 100 - len; j++) {
                        boolean isValid = true;

                        for (int k = 0; k < len / 2; k++) {
                            if (map[j + k][i]
                                    != map[j + len - k - 1][i]) {
                                isValid = false;
                                break;
                            }
                        }

                        if (isValid) {
                            found = true;
                        }
                    }
                }

                if (found) {
                    maxLength = len;
                }
            }

            System.out.println("#" + tc + " " + maxLength);
        }

        sc.close();
    }
}
```

### 방법 2: DP

`rowDp`와 `colDp`에 현재 행과 열의 구간별 회문 여부를 저장한다.

```java
import java.util.Scanner;

public class Solution {
    static final int SIZE = 100;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int t = 0; t < 10; t++) {
            int tc = sc.nextInt();
            char[][] map = new char[SIZE][SIZE];

            for (int i = 0; i < SIZE; i++) {
                map[i] = sc.next().toCharArray();
            }

            int maxLength = 1;

            for (int line = 0; line < SIZE; line++) {
                boolean[][] rowDp = new boolean[SIZE][SIZE];
                boolean[][] colDp = new boolean[SIZE][SIZE];

                for (int len = 1; len <= SIZE; len++) {
                    for (int start = 0; start + len <= SIZE; start++) {
                        int end = start + len - 1;

                        // line번째 행
                        if (map[line][start] == map[line][end]
                                && (len <= 2
                                        || rowDp[start + 1][end - 1])) {
                            rowDp[start][end] = true;
                            maxLength = Math.max(maxLength, len);
                        }

                        // line번째 열
                        if (map[start][line] == map[end][line]
                                && (len <= 2
                                        || colDp[start + 1][end - 1])) {
                            colDp[start][end] = true;
                            maxLength = Math.max(maxLength, len);
                        }
                    }
                }
            }

            System.out.println("#" + tc + " " + maxLength);
        }

        sc.close();
    }
}
```

## 5. 시간 복잡도

글자판의 한 변 길이를 `N`으로 일반화하면 다음과 같다.

| 방법 | 시간 복잡도 | 공간 복잡도 |
|---|---:|---:|
| 브루트포스 | **O(N⁴)** | O(N²) |
| DP | **O(N³)** | O(N²) |

브루트포스는 길이 O(N)개, 각 길이의 후보 O(N²)개를 살펴보고, 후보마다 최대 O(N)번 문자를 비교한다. DP는 같은 구간들을 살펴보되, 각 구간의 회문 여부를 저장된 안쪽 결과로 O(1)에 판단한다.
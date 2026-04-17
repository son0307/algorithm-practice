## 문제 요약

- **목표**: $N \times N$ 크기의 교실에 $N^2$명의 학생을 주어진 우선순위 규칙에 따라 배치한 뒤, 최종 만족도의 총합을 구한다.
- **조건 (자리 배치 우선순위)**:
    1. 비어있는 칸 중에서, 인접한 4방향에 '좋아하는 학생'이 가장 많은 칸으로 정한다.
    2. 1을 만족하는 칸이 여러 개면, 인접한 칸 중 '비어있는 칸'이 가장 많은 칸으로 정한다.
    3. 2를 만족하는 칸도 여러 개면, 행 번호가 가장 작은 칸 → 열 번호가 가장 작은 칸 순으로 정한다.
- **출력 항목**: 모든 학생을 배치한 후 산출된 전체 학생의 만족도 총합

## 핵심 포인트

- **우선순위를 정밀하게 코드화** : 고려해야 하는 조건이 3개인 만큼 탐색 순서 등을 고려하여 정확히 코드를 작성해야 한다.
- **그리디 & 완전 탐색 시뮬레이션** : 매 학생마다 교실의 모든 칸을 탐색하며 현재 상황에서 최적의 자리를 찾아 배치하는 과정을 반복해야 한다.
- **최적화** : 특정 학생이 특정 학생을 좋아하는지 매번 반복문을 통해 찾으면 추가적인 시간이 소요된다. 이를 개선하기 위한 구조가 필요하다.

### 동작 원리

1. **변수 및 데이터 초기화** : 학생들을 배치한 자리를 저장할 $N \times N$ 크기의 `seats` 배열과 호감도를 조회하기 위한 2차원 배열 `likes` 을 초기화한다.
2. **학생 순회 및 탐색** : 입력받은 순서대로 학생을 격자에 배치하기 위해 ‘좋아하는 학생’을 하나씩 입력받을 때마다 교실 전체 격자를 왼쪽 위에서부터 오른쪽 아래로 순회하며 탐색한다.
    1. **인접 칸 검사** : 빈칸을 발견하면 상, 하, 좌, 우 4방향을 탐색하여, 그 자리에 앉았을 때 인접한 ‘좋아하는 학생 수(`curLikes`)’와 ‘빈칸 수(`curEmpty`)’를 카운트한다.
    2. **최적의 자리 갱신** :
        - `curLikes`가 이번 탐색에서 현재까지 찾은 최대치(`bestLikes`)보다 크면 무조건 좌표를 갱신한다.
        - `curLikes`가 같고, `curEmpty`가 이번 탐색에서 현재까지 찾은 최대치(`bestEmpty`)보다 크면 좌표를 갱신한다.
        - 위 두 조건에 걸리지 않으면 무시한다. (조건 3은 왼쪽 위에서부터 탐색하기 때문에 자동으로 충족된다.)
    3. **자리 선정** : 모든 빈칸을 탐색한 후 최종적으로 선정된 좌표에 해당 학생의 번호를 저장한다.
3. **만족도 계산** :
   모든 학생의 배치가 끝나면 다시 교실 전체를 순회하며, 각 학생의 4방향에 위치한 ‘좋아하는 학생’ 수를 구하고 `switch`문을 통해 점수로 환산하여 모두 더한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int x = Integer.parseInt(br.readLine());
        int n = x * x; // 총 학생 수
        int[][] seats = new int[x+1][x+1];
        // O(1) 조회를 위한 호감도 배열
        boolean[][] likes = new boolean[n+1][n+1]; 

        int[] dy = {1, -1, 0, 0};
        int[] dx = {0, 0, -1, 1};

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            for (int j = 0; j < 4; j++) {
                int friend = Integer.parseInt(st.nextToken());
                likes[student][friend] = true;
            }

            // 현재 학생의 최선의 자리 찾기
            int bestR = 0;
            int bestC = 0;
            int bestLikes = -1;
            int bestEmpty = -1;

            for (int j = 1; j <= x; j++) {
                for (int k = 1; k <= x; k++) {
                    if (seats[j][k] != 0) continue; // 이미 누가 앉은 자리는 패스
                    
                    // 첫 빈칸 무조건 저장 (최악의 조건 방어용)
                    if (bestR == 0 && bestC == 0) {
                        bestR = j;
                        bestC = k;
                    }

                    int curLikes = 0;
                    int curEmpty = 0;

                    for (int l = 0; l < 4; l++) {
                        int newR = j + dy[l];
                        int newC = k + dx[l];

                        if (newR < 1 || newR > x || newC < 1 || newC > x) continue;
                        if (seats[newR][newC] == 0) curEmpty++;
                        else if (likes[student][seats[newR][newC]]) curLikes++;
                    }

                    // 조건 1. 좋아하는 학생 수가 더 많을 때
                    if (curLikes > bestLikes) {
                        bestLikes = curLikes;
                        bestEmpty = curEmpty;
                        bestR = j;
                        bestC = k;
                    } 
                    // 조건 2. 좋아하는 학생 수가 같고 빈칸이 더 많을 때
                    else if (curLikes == bestLikes) {
                        if (curEmpty > bestEmpty) {
                            bestEmpty = curEmpty;
                            bestR = j;
                            bestC = k;
                        }
                    }
                    // 조건 3은 루프 진행 방향(위->아래, 좌->우)과 '>' 연산자로 인해 자동 해결됨
                }
            }

            // 탐색 완료 후 최종 자리에 학생 배치
            seats[bestR][bestC] = student;
        }

        // 완성된 자리 기반으로 만족도 계산
        int score = 0;
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= x; j++) {
                int stud = seats[i][j];
                int count = 0;

                for (int k = 0; k < 4; k++) {
                    int newR = i + dy[k];
                    int newC = j + dx[k];

                    if (newR < 1 || newR > x || newC < 1 || newC > x) continue;
                    if (likes[stud][seats[newR][newC]]) count++;
                }

                switch(count) {
                    case 0: score+=0; break;
                    case 1: score+=1; break;
                    case 2: score+=10; break;
                    case 3: score+=100; break;
                    case 4: score+=1000; break;
                }
            }
        }
        System.out.println(score);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 전체 학생 수 : $N^2$명
- 각 학생을 배치할 때마다 최대 $N \times N$ 크기의 격자를 모두 순회해야 하므로 $N^2$번의 칸 탐색이 발생하며, 각 칸마다 4방향을 탐색한다.
  따라서, 한 학생마다 $4N^2$번 연산을 수행한다.

최종적으로, 위 알고리즘의 시간 복잡도는 $O(N^4)$가 된다.

문제에서 주어진 $N$의 최댓값은 20으로, $4 \times20^4 = 640,000$번의 연산만 수행하면 된다. 따라서, 시간복잡도가 매우 높게나왔지만 제한 시간 안에 통과할 수 있었다.
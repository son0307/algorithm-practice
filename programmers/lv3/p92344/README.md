## 문제 요약

N x M 크기의 맵에서 건물의 내구도를 관리하고 최종적으로 파괴되지 않은 건물의 수를 반환하는 문제

- **공격/회복** : 직사각형 범위 (r1, c1) ~ (r2, c2)에 대해 내구도 증감
- **파괴 기준** : 내구도가 0 이하가 되면 파괴됨 판정 (회복을 통해 복구 가능, 데미지는 0이하가 되어도 누적됨)
- **목표** : 모든 스킬 적용 후 파괴되지 않은 (내구도 ≥ 1) 건물의 개수 반환
- **제한 사항** : 행/열 최대 1,000, 스킬 최대 250,000개

## 핵심 포인트

이 문제는 건물의 내구도를 어떻게 빠르게 계산하는 지가 중요한 문제이다. 이 문제를 보고 가장 먼저 떠오를 수 있는 해결 방법은 주어진 스킬들을 하나씩 순회하며 주어진 맵의 건물들의 내구도를 갱신하는 것이다.

하지만, 이 방법은 정확도 테스트에서는 시간 내에 수행할 수 있지만 효율성 테스트에서는 시간 초과가 발생한다. 스킬 개수를 K, 맵의 크기를 N x M이라 할 때, 이 알고리즘은 $O(K \times N \times M)$의 시간복잡도를 가지고, 최대 반복 횟수는 250,000,000,000 이다. 따라서, 이 방법으로는 시간 내에 해결할 수 없다.

1. **2차원 누적합**

   그로므로 다른 방법을 이용해야 한다. 그 방법은 바로 **누적합**을 이용하는 것이다. O(1)의 시간으로 건물들에 가해지는 데미지들을 누적하여 기록하고, $O(N \times M)$의 시간으로 한 번만 계산하여 파괴 여부를 검사하는 것이다.

2. **마킹 방식**

   범위 (r1, c1)부터 (r2, c2)에 값 d를 더할 때, 4개의 지점에 마킹하면 값이 영향을 끼칠 범위를 지정할 수 있다.

    - 예시 설명

      예를 들어, (1, 1)부터 (2, 2)에 값 x를 더한다고 가정하고, 누적합을 이용하여 적용해보도록 하자.

      ![image.png](attachment:963a6b49-a680-4f1a-97b2-9e297f48eb75:image.png)

      (1, 1)에 값 x를 두면 해당 값이 미치는 범위는 위와 같다. 이를 한정하기 위해 우리는 값 -x를 위치시켜 합이 0이 되도록 해야 한다.

      ![image.png](attachment:de373c57-856a-4d29-a8f9-477243f347a9:image.png)

      ![image.png](attachment:b01884db-efd9-4939-99a3-96fea408b8e8:image.png)

      이를 위해 값 -x를 (3, 1)과 (1, 3)에 위치시키면 값 x의 영향 범위를 한정시킬 수 있다. 이 때, 두 개의  -x가 겹치는 부분이 있으므로 이를 0으로 만들기 위해 값 x를 추가로 배치한다.

      ![image.png](attachment:91adf657-30bb-44cb-baf3-71f17d0c283d:image.png)

      이렇게 누적합을 이용하여 특정 범위 내에 숫자들을 더하거나 뺄 수 있다.


    정리하자면 다음과 같다.
    
    - `(r1, c1)`에 +d
    - `(r1, c2 + 1)`에 -d
    - `(r2 + 1, c1)`에 -d
    - `(r2 + 1, c2 + 1)`에 +d
3. **최종 합산**

   가로 방향 누적합 이후 세로 방향 누적합을 수행하여 각 칸에 가해질 최종 데미지를 계산한다.


### 동작 원리

1. **배열 생성** : 누적 데미지 계산을 위해 board보다 크기가 1 더 큰 sumBoard를 생성한다.
2. **스킬 기록** : skill 배열을 순회하며 각 스킬의 범위 및 데미지를 기록한다. (4개의 지점을 저장한다.)
3. **누적합 계산** : 각 행에 대해 왼쪽에서 오른쪽으로 값을 누적하며 더한 다음 각 열에 대해 위에서 아래로 값을 누적하여 더한다.
4. **결과 판정** : 원본 board의 값에 sumBoard의 변화량을 뺀 값이 1 이상인 경우 answer을 1 증가시킨다.

### 전체 코드

```java
class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;

        int[][] sumBoard = new int[board.length + 1][board[0].length + 1];
        for (int[] skillRow : skill) {
            int dmg;
            if (skillRow[0] == 1) dmg = skillRow[5];
            else dmg = -skillRow[5];

            sumBoard[skillRow[1]][skillRow[2]] += dmg;
            sumBoard[skillRow[3] + 1][skillRow[4] + 1] += dmg;
            sumBoard[skillRow[3] + 1][skillRow[2]] -= dmg;
            sumBoard[skillRow[1]][skillRow[4] + 1] -= dmg;
        }

        for (int row = 0; row < sumBoard.length; row++) {
            for (int col = 0; col < sumBoard[row].length - 1; col++) {
                sumBoard[row][col + 1] += sumBoard[row][col];
            }
        }

        for (int row = 0; row < sumBoard.length - 1; row++) {
            for (int col = 0; col < sumBoard[row].length; col++) {
                sumBoard[row + 1][col] += sumBoard[row][col];
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] - sumBoard[row][col] >= 1)
                    answer++;
            }
        }

        return answer;
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 스킬 개수(K)만큼 마킹하는데 $O(K)$가 걸린다.
- 이후 N x M 크기의 배열을 순회하며 누적합을 구하는데 $O(N \times M)$이 소요된다.

따라서, 최종 시간 복잡도는 $O(K + N \times M)$ 이다.
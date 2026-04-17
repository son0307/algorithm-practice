## 문제 요약

어피치와 라이언이 n발의 화살로 양궁 대결을 하여 라이언이 가장 큰 점수 차이로 승리할 수 있는 과녁 상황을 구하는 문제

- 득점 규칙 : 각 점수마다 더 많은 화살을 쏜 사람이 점수를 가져감 (단, 화살 수가 동일할 경우 어피치가 점수를 가져가며, 둘 다 0발이면 아무도 점수를 못 가져감)
- 승리 조건 : 라이언의 총점이 어피치의 총점보다 높아야 함. 점수가 동일한 경우, 낮은 점수를 더 맞힌 경우를 선택
- 제한 사항 : 화살 개수 n ≤ 10, 과녁 점수는 0 ~ 10점

## 핵심 포인트

과녁의 점수 종류가 11개, 화살은 최대 10발이므로, 백트래킹(DFS)를 이용하여 모든 경우의 수를 시간 내에 충분히 탐색할 수 있다.

1. **선택할 수 있는 경우** : 각 점수대에서 라이언은 두 가지 선택을 할 수 있다.
    1. 화살을 쏨 : 어피치보다 한 발 더 많이 쏴 점수를 가져온다.
    2. 화살을 쏘지 않음 : 화살을 한 발도 쏘지않고 다음 점수로 넘긴다.
2. **남은 화살 처리** : 모든 점수대를 살펴보고 마지막 점수대(0점)에서 남은 화살들을 모두 쏘아 동일한 최대 점수차일 때 해당 경우가 선택되도록 유도한다.
3. **우선순위 비교** : 라이언과 어피치의 점수 차가 동일한 경우, 0점부터 역순으로 살펴보며 낮은 점수대에 더 많은 화살을 쏜 배열을 선택한다.

### 동작 원리

1. DFS 탐색 (`ryanShooting`)
    1. 10점부터 0점까지 순차적으로 확인한다.
    2. 현재 남은 화살이 어피치가 쏜 화살보다 많다면, `apeach[i] + 1` 만큼 화살을 쏘고 다음 점수로 재귀 호출한다. (점수 획득)
    3. 화살을 쏘지 않고 다음 점수로 재귀 호출한다. (점수 생략)
2. 종료 조건 : 0점까지 모든 화살 개수가 결정되면(`index == 11` ), 총점을 계산한다.
3. 점수 계산 및 갱신 (`calcDiff`)
    1. 라이언과 어피치의 점수 차를 계산한다.
    2. 현재 `maxDiff` 보다 점수 차가 크다면 정답 배열을 교체한다.
    3. `maxDiff` 가 동일하면 `isPriority` 함수를 통해 낮은 점수 비중을 비교하여 교체 여부를 결정한다.
4. 최종 결과 반환 : `maxDiff` 가 초기값 그대로라면 승리할 수 없는 경우이므로 `[-1]` 을 반환한다.

### 전체 코드

```java
class Solution {
    int maxDiff = -1;
    int[] maxRyan = {-1};

		// 점수 차이 계산 및 최대값 갱신
    public void calcDiff(int[] apeach, int[] ryan) {
        int ryanScore = 0;
        int apeachScore = 0;

        for (int i = 0; i <= 10; i++) {
            if (apeach[i] == 0 && ryan[i] == 0) continue;
            if (apeach[i] >= ryan[i]) {
                apeachScore += (10 - i);
            } else {
                ryanScore += (10 - i);
            }
        }

        int diff = ryanScore - apeachScore;

        if (diff > 0) {
            if (diff > maxDiff) {
                maxDiff = diff;
                maxRyan = ryan.clone();
            } else if (diff == maxDiff) {
                if (isPriority(ryan)) {
                    maxRyan = ryan.clone();
                }
            }
        }
    }

		// 낮은 점수 우선순위 확인
    public boolean isPriority(int[] ryan) {
        for (int i = 10; i >= 0; i--) {
            if (ryan[i] > maxRyan[i]) return true;
            else if (ryan[i] < maxRyan[i]) return false;
        }
        return false;
    }

    public void ryanShooting(int index, int arrows, int[] ryan, int[] apeach) {
        if (index == 11) {
            calcDiff(apeach, ryan);
            return;
        }

				// 0점 확인 시 남은 화살 전부 소진
        if (index == 10) {
            ryan[index] = arrows;
            ryanShooting(index + 1, 0, ryan, apeach);
            ryan[index] = 0;
            return;
        }

				// 해당 점수를 얻는 경우 (어피치보다 1발 더 쏘기)
        if (arrows > apeach[index]) {
            ryan[index] = apeach[index] + 1;
            ryanShooting(index + 1, arrows - ryan[index], ryan, apeach);
            ryan[index] = 0;
        }

				// 해당 점수를 포기하는 경우 (0발 쏘기)
        ryanShooting(index + 1, arrows, ryan, apeach);
    }

    public int[] solution(int n, int[] info) {
        maxDiff = -1;
        maxRyan = new int[]{-1};

        ryanShooting(0, n, new int[11], info);

        return maxRyan;
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 각 점수마다 쏜다, 쏘지않는다 2가지의 선택지가 존재한다. 이 때, 점수대의 개수를 K라고 한다면 가능한 경우의 수는 최대 $2^K$이다. 따라서, 시간 복잡도는 $O(2^K)$가 된다.
## 문제 요약

동영상 재생기의 10초 전, 10초 후, 오프닝 건너뛰기 기능을 구현하는 문제

- **10초 전 (prev)** : 현재 위치에서 10초 전으로 이동
- 1**0초 후 (next)** : 현재 위치에서 10초 후로 이동
- **오프닝 건너뛰기** : 현재 위치가 오프닝 구간에 있으면 오프닝 종료 시점으로 건너뛰기
- **주의사항** : 명령어 수행 전 시작 위치가 오프닝 구간일 때도 건너뛰기가 적용되어야 함

## 핵심 포인트

1. **시간 단위 통일** : “mm:ss” 형태로 주어진 시간을 간단하게 처리하기 위해 모든 시간을 **초 단위**로 변환하여 계산 로직을 단순화한다.
2. **오프닝 체크 시점** : 오프닝 구간 체크는  **최초 시작 시점**과 **매 명령어 수행 직후**에 매번 이루어져야 한다.
3. **경계값 처리** : 영상의 처음과 끝을 벗어나지 않도록 최솟값/최댓값 처리를 잘 수행해야 한다.

### 동작 원리

1. **데이터 전처리** : “mm:ss”로 주어진 시간들을 모두 초로 변환하여 저장해둔다.
2. **초기 위치 체크** : 시작 지점 `pos` 가 오프닝 구간 안에 있으면 즉시 `op_end` 로 이동시킨다.
3. **명령어 시뮬레이션** : `commands` 배열을 순회하며 처리 작업을 수행한다.
    1. **next** : 현재 초에 10을 더한다. 영상 길이를 초과하면 영상 길이로 고정한다.
    2. **prev** : 현재 초에서 10을 뺀다. 0보다 작아지면 0으로 고정한다.
    3. **오프닝 재확인** : 이동한 위치가 다시 오프닝 구간에 들어왔는지 확인한다. 오프닝 구간 내에 있다면 즉시 `op_end` 로 이동시킨다.
4. **결과 포맷팅** : 계산된 최종 초를 다시 “mm:ss” 형식으로 변환하여 반환한다.

### 전체 코드

```java
class Solution {
    int converter(String time) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]);
        int second = Integer.parseInt(times[1]);

        return minute * 60 + second;
    }

    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int lenSecond = converter(video_len);
        int posSecond = converter(pos);
        int opStartSecond = converter(op_start);
        int opEndSecond = converter(op_end);

        if (opStartSecond <= posSecond && posSecond <= opEndSecond ) {
            posSecond = opEndSecond;
        }

        for (String command : commands) {
            switch (command) {
                case "next":
                    posSecond += 10;
                    if (posSecond > lenSecond)
                        posSecond = lenSecond;
                    break;
                case "prev":
                    posSecond -= 10;
                    if (posSecond < 0)
                        posSecond = 0;
                    break;
            }

            if (opStartSecond <= posSecond && posSecond <= opEndSecond ) {
                posSecond = opEndSecond;
            }
        }

        int minute = posSecond / 60;
        int second = posSecond % 60;

        return String.format("%02d:%02d", minute, second);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 명령어 배열 `commands` 의 길이 $N$만큼 반복문을 수행한다. 반복문 안의 계산 로직은 $O(1)$ 만큼 소요된다.

따라서, 최종 시간 복잡도는 $O(N)$이다.
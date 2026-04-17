## 핵심 포인트

이 문제는 별도의 알고리즘이나 자료구조를 사용하지 않고 주어진 제한 사항에 대한 조건만 올바르게 처리하도록 구현하면 되는 문제이다. 주의 깊게 봐야하는 제한 사항은 2가지이다.

- 출근 희망 시각
    - 각 직원들이 설정한 출근 희망 시각에 10분을 더한 시간을 기준으로 더 늦게 출근 시 지각으로 처리한다.
    - 출근 희망 시각에 10분을 더할 때, 단순히 10분만 더하는 것이 아닌 시간 포맷에 맞추어 변형해야 할 필요성이 있다.
    - 10분을 더했을 때, 60분을 넘어가면 시간 부분에 1을 더하는 방식으로 수정해야 한다.
- 요일
    - 시작 기준으로 되는 요일이 입력으로 주어지고, 토요일, 일요일의 출근 시간은 이벤트에 영향을 끼치지 않는다.
    - 요일은 반복되므로 모듈러 연산(% 7)을 이용해 주말을 제외하는 것이 적절하다. 월요일이 1, 일요일이 7로 주어지므로, 7로 나눈 나머지가 6(토요일)이거나 0(일요일, 7 % 7 = 0)인 경우는 계산에서 제외한다.
        - 일요일 시작인 경우, startDay가 7이다. 이 때, 검사할 때마다 1씩 증가하도록 한 경우 다음 토요일의 번호가 13이 되므로 단순히 6과 7일 때 계산을 무시하면 오답이 발생한다.
    - 평일에 모두 지각 없이 출근한 경우 해당 직원은 상품을 받게된다.

위 제한 사항들을 제대로 고려하고 코드를 작성해야 한다. 아래는 각 고려 사항에 대해 코드 내에서 어떻게 처리했는지를 서술한다.

- 출근 희망 시각

    ```java
    for (int i = 0; i < recognize_times.length; i++) {
        int schedule = schedules[i] + 10;
        if (schedule % 100 >= 60)
            schedule += 40;
    
        recognize_times[i] = schedule;
    }
    ```

    - 주어진 시간이 시는 100단위, 그 아래에는 분 부분으로 구성되어 있다. 그러므로 일단 10을 더했다.
    - 이후 100으로 나눈 나머지 값을 확인하여 이 부분이 60 이상인 경우 60분을 넘은 경우 이므로 100을 더하고 60을 뺀 값인 40을 더하여 포맷에 맞추도록 값을 수정하였다.
- 요일

    ```java
    for (int i = 0; i < schedules.length; i++) {
        Boolean isSave = true;
    
        for (int j = 0; j < 7; j++) {
            int currentDay = startday + j;
            if (currentDay % 7 == 6 || currentDay % 7 == 0)
                continue;
    
            if (timelogs[i][j] > recognize_times[i]) {
                isSave = false;
                break;
            }
        }
    
        if(isSave) result++;
    }
    ```

    - 이 부분은 각 직원의 출근 시간을 검사하여 지각 여부를 확인하는 부분이다.
    - startDay에 1씩 더해가며 각 요일의 출근 시간을 검사하는데, 더한 값을 7로 모듈러 연산을 수행한 결과가 6인 경우는 토요일, 0인 경우는 일요일이므로 계산을 무시한다.
    - 한 번이라도 인정 시각을 넘은 경우 상품을 받지 못하므로 계산을 생략하였다.

### 전체 코드

```java
import java.util.*;

class Solution {
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int[] recognize_times = new int[schedules.length];
        int result = 0;

        for (int i = 0; i < recognize_times.length; i++) {
            int schedule = schedules[i] + 10;
            if (schedule % 100 >= 60)
                schedule += 40;

            recognize_times[i] = schedule;
        }

        for (int i = 0; i < schedules.length; i++) {
            Boolean isSave = true;

            for (int j = 0; j < 7; j++) {
                int currentDay = startday + j;
                if (currentDay % 7 == 6 || currentDay % 7 == 0)
                    continue;

                if (timelogs[i][j] > recognize_times[i]) {
                    isSave = false;
                    break;
                }
            }

            if(isSave) result++;
        }

        return result;
    }
}
```
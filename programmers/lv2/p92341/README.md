## 문제 요약

주차장의 요금표와 차량의 입/출차 기록을 바탕으로 차량별 주차 요금을 계산하는 문제

- 출차 기록이 없는 차량: 23:59분에 출차한 것으로 간주
- 요금 계산 방식: 누적 주차 시간이 기본 시간 이하면 기본 요금, 초과 시 초과 시간 요금 추가 적용
- 결과 출력 시 차량 번호가 작은 순서대로 정렬하여 반환

## 핵심 포인트

이 문제는 각 차량의 상태를 실시간으로 관리하고, 최종적인 누적 시간을 기준으로 요금을 한 번에 계산하는 것이 포인트이다.

1. **시간 단위 통일** : “HH:MM” 형태로 주어진 시간을 계산하기 편하도록 모두 분 단위로 통일하여 처리한다.
2. **상태 실시간 관리**
    1. parking : 현재 주차 중인 차량의 입차 시간을 저장한다.
    2. totalTimes : 차량별 총 누적 주차 시간을 저장한다.
3. **예외 처리** : 모든 기록을 순환한 뒤 parking 맵에 남아있는 차량은 23:59를 기준으로 출차 처리한다.
4. **정렬 및 정산** : 차량 번호를 오름차순으로 정렬한 뒤, 문제에서 주어진 주차비 계산 공식에 따라 요금을 계산한다.

동작 원리는 다음과 같다.

1. 입/출차 기록 처리
    1. IN : parking 맵에 차량 번호와 입차 시간을 기록한다.
    2. OUT : parking 맵에서 입차 시간을 꺼내 주차 시간을 계산하고, total_times에 누적한 뒤 parking 맵에서 삭제한다.
2. 미출차 차량 처리 : 모든 기록을 순환한 뒤 parking 맵에 남아있는 모든 차량에 대해 23:59 기준으로 출차 처리를 진행한다.
3. 정렬 : total_times의 Key(차량 번호)를 리스트에 담아 오름차순으로 정렬한다.
4. 요금 계산
    1. 누적 시간이 기본 시간보다 작으면 기본 요금
    2. 초과 시 : 기본 요금 + $\lceil(누적 시간 - 기본 시간) / 단위 시간\rceil \times 단위 요금$
    3. 올림 처리를 위해 Math.ceil()을 활용한다.

### 전체 코드

```java
import java.util.*;

class Solution {
    HashMap<String, Integer> parking = new HashMap<>();
    HashMap<String, Integer> total_times = new HashMap<>();

    public void in(String time, String num) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        parking.put(num, minute);
    }

    public void out(String time, String num) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        int diff = minute - parking.get(num);
        parking.remove(num);
        total_times.put(num, total_times.getOrDefault(num, 0) + diff);
    }

    public int[] calculateFee(List<String> nums, int[] fees) {
        int[] answer = new int[nums.size()];

        for (int i = 0; i < nums.size(); i++) {
            answer[i] = fees[1];
            int time = total_times.get(nums.get(i));
            if (time > fees[0]) {
                answer[i] += (int) (Math.ceil((double) (time - fees[0]) / fees[2]) * fees[3]);
            }
        }

        return answer;
    }

    public int[] solution(int[] fees, String[] records) {
        for (String record : records) {
            String[] split = record.split(" ");
            if (split[2].equals("IN")) {
                in(split[0], split[1]);
            } else if (split[2].equals("OUT")) {
                out(split[0], split[1]);
            }
        }

        List<String> remaining = new ArrayList<>(parking.keySet());
        for (String num : remaining) {
            out("23:59", num);
        }

        List<String> sortedNum = new ArrayList<>(total_times.keySet());
        Collections.sort(sortedNum);

        return calculateFee(sortedNum, fees);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 입/출차 기록의 개수를 N이라고 하면, 전체를 한 번 순회하므로 O(N)의 시간이 소요된다.
- 주차장을 이용한 차량의 종류를 K라고 하면, 오름차순으로 정렬 시 O(KlogK)의 시간이 소요된다.

따라서, 전체 시간복잡도는 $O(N + K \log K)$가 된다.

위 알고리즘에서 주차장에 남아있는 차량들을 출차 처리할 때, parking.keySet()을 이용하여 반복문을 돌 수 있을 것이라 생각했었다. 하지만, 해당 부분에서 `ConcurrentModificationException` 이 발생하는 문제가 발생하였다.

해당 문제의 원인은 for-each 루프의 동작 원리로 인해 발생한 문제였다. for-each 루프는 내부적으로 Iterator를 사용한다. 이 때, out 함수가 호출되어 parking.remove(num)이 실행되면, Map의 구조가 바뀌고, Iterator는 상태가 유효하지 않다고 판단하여 예외를 던진다.

따라서, 반복문에서 사용할 키셋을 별도의 리스트로 복사한 뒤 순화하도록 하여 문제를 해결하였다.
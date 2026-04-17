### 문제 요약

- 대실 시작 시간, 종료 시간이 주어졌을 때 사람들에게 방을 배분하기 위해 필요로 하는 방의 수를 구하는 문제
    - 종료 시간에서 10분이 지나야 다시 대실할 수 있다는 것에 유의해야 한다.
    - 대실 시작 시간, 종료 시간이 HH : MM 형태로 주어지므로 이를 적절하게 변환하는 것이 중요하다.
- 첫번째 예제를 분석하면 다음과 같이 나오게된다.

  ![image.png](attachment:af1b933e-ad08-4a58-b0eb-c19d5a066bff:image.png)

  대실 시간이 빠른 순서대로 생각해보자. 그러면 다음과 같이 생각할 수 있다.

    - 14:10 ~ 19:20 - 대실 시작, 동시에 사용한 방 개수 + 1
    - 14:20 ~ 15:20 - 가장 빨리 비는 방이 19:30이므로 추가로 방을 사용, 동시에 사용한 방 개수 + 1
    - 15:00 ~ 17:00 - 가장 빨리 비는 방이 15:30이므로 추가로 방 사용, 동시에 사용한 방 개수 + 1
    - 16:40 ~ 18:20 - 가장 빨리 비는 방이 15:30이었으므로 빈 방 사용
    - 18:20 ~ 21:20 - 가장 발리 비는 방이 17:10이었으므로 빈 방 사용

  최대로 동시에 사용한 방 개수는 총 3개이다.


## 핵심 포인트

예제를 분석한 것과 같이 이 문제를 해결하는데 있어 가장 중요한 단서는 ‘가장 빨리 끝나는 방의 종료 시간’ 이다. 다음 사람의 대실 시작 시간이 가장 빨리 끝나는 방의 종료 시간보다 이른 경우엔 방을 추가로 사용해야 하고, 늦는 경우에는 해당 방을 재사용하면 된다. 따라서, 종료 시간을 저장할 때, 우선순위 큐와 같이 정렬할 수 있는 자료구조를 통해 시간 순서대로 정렬하면 문제를 효율적으로 해결할 수 있다.

다시 한번 정리하면 다음과 같다.

1. 종료 시간 기준 빠른 순서대로 정렬되는 우선순위 큐를 준비한다.
2. 전달된 대실 시간들을 시작 시간이 빠른 순서대로 정렬한다.
3. 대실 시간들을 순회하며 우선 순위 큐에 삽입한다. 단, 아래의 조건에 부합하도록 한다.
    1. 가장 빠른 종료시간보다 대실 시작 시간이 더 늦는 경우 우선순위 큐의 가장 위에 있는 값을 제거한다.
4. 순회가 끝난 후 우선순위 큐에 남아있는 요소들이 최대로 동시에 사용한 방의 개수이다.

### 전체 코드

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int solution(String[][] book_time) {
        int[][] bookings = new int[book_time.length][2];

        for (int i = 0; i < book_time.length; i++) {
           bookings[i][0] = parseTime(book_time[i][0]);
           bookings[i][1] = parseTime(book_time[i][1]) + 10;
        }

        Arrays.sort(bookings, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();

        for (int[] booking : bookings) {
            int startTime = booking[0];
            int endTime = booking[1];

            if (!roomEndTimes.isEmpty() && roomEndTimes.peek() <= startTime) {
                roomEndTimes.poll();
            }

            roomEndTimes.add(endTime);
        }

        return roomEndTimes.size();
    }

    private int parseTime(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}
```
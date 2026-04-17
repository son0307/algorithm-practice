## 문제 요약

- 호텔에 투숙하려는 고객들이 원하는 방 번호를 순서대로 제출
    - 원하는 방이 비어있으면 즉시 배정
    - 원하는 방이 이미 배정되었으면, 그 번호보다 큰 번호의 방 중 가장 작은 번호의 방으로 배정
    - 전체 방 개수$(k)$는 최대 $10^{12}$으로, int 범위를 초과하기에 배열을 사용할 수 없다.

## 핵심 포인트

이 문제는 인접해있는 이미 할당된 방들을 하나의 그룹으로 봐야 하기에 **Disjoint Set(Union-find)** 자료구조를 이용할 수 있다. 다만, 전체 방의 개수$(k)$가 int 범위를 초과하기 때문에 일반적인 배열 기반의 부모 노드를 저장하는 방법은 불가능하다.

따라서 **HashMap**을 이용해 필요한 노드들만 저장하는 희소 배열 방식을 사용해야 한다. 또한, 빈 방을 찾는 과정에서 소요되는 시간을 줄이기 위해 **경로 압축** 기법을 적용해야 한다.

탐색 및 배정 방법은 다음과 같이 정의할 수 있다.

1. **요청한 번호의 방이 비어있는 경우 (Map에 해당 번호 Key가 없는 경우)**
    - 해당 방을 배정한다.
    - Map에 Key와 그 다음 방 번호를 저장한다.
2. **요청한 번호의 방이 차있는 경우 (Map에 해당 번호 Key가 존재하는 경우)**
    - Map을 재귀적으로 순회하며 가장 작은 숫자의 빈 방을 찾는다.
    - 빈 방을 찾았으면 배정하고, 탐색 경로에 포함된 방들의 다음 빈 방 번호를 찾은 빈 방의 다음 방번호로 갱신한다.

이 방식을 통해 연속적으로 이미 배정된 구간을 O(1)로 뛰어넘을 수 있다.

### 전체 코드

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Long, Long> map = new HashMap<>();

    private long find(long room) {
       if (!map.containsKey(room)) {
           map.put(room, room + 1);
           return room;
       }

       long nextRoom = map.get(room);
       long emptyRoom = find(nextRoom);

       map.put(room, emptyRoom + 1);

       return emptyRoom;
    }

    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];

        for (int i = 0; i < room_number.length; i++) {
            answer[i] = find(room_number[i]);
        }

        return answer;
    }
}
```

위 알고리즘의 시간 복잡도는 $O(N)$(N은 room_number의 길이) 이라고 볼 수 있다. find 연산에서 경로 압축을 수행하기 때문에 각 배정 요청은 거의 상수 시간에 처리된다. 모든 방(k)에 대해 상태를 저장할 필요 없이 배정된 방에 대한 정보만 저장하기에 공간 효율성도 확보할 수있다.
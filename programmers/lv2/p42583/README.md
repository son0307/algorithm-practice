### 문제 요약

- 주어진 트럭들이 다리를 건너는 최소 시간을 구하는 문제
    - 단, 다리 위에 올라간 트럭들의 무게가 weight를 초과할 수 없음
    - 각 트럭은 1초에 1씩만 움직일 수 있음

## 핵심 포인트

이 문제는 다리에 올라간 트럭들의 총 무게를 중점적으로 생각해야 한다. 트럭들의 무게 총합은 시간의 흐름에 따라 실시간으로 바뀌므로 이를 계속해서 계산해 추가로 트럭이 투입한 가능하면 바로바로 넣어줘야 최소 시간을 구할 수 있다.

그렇다면 어떤 자료구조를 활용할 수 있을까? 다리를 지나는 트럭들은 한쪽 방향에서 들어와서 다른 방향으로 나가는 일방향으로 움직인다. 이를 생각하면 큐를 활용할 수 있다. 다리의 길이만큼 큐에 더미 숫자들을 미리 넣어두면 트럭 번호를 넣었을 때, 다리의 길이만큼 숫자들이 빠져야 넣은 트럭의 번호가 나오게되는데 이를 트럭이 다리를 지나갔다고 생각할 수 있다.

bridge_length가 5, 대기 트럭은 [2, 4, 3, 3], weight가 7인 경우를 그림으로 살펴보도록 하자. 초기 상태는 다음과 같다.

![image.png](attachment:b05b6ae2-fa07-430b-b89b-7be6ae2a61f2:image.png)

1번 트럭의 무게가 2이고, 2번 트럭의 무게가 4이므로 두 트럭은 동시에 다리에 올라갈 수 있다. 따라서, 1초와 2초 시점에선 다음과 같이 나타난다.

![image.png](attachment:e3cf6ee0-e1cb-4fdc-b35a-96eaf4406096:image.png)

3번 트럭의 무게는 3으로 현재 시점에서는 올라갈 수 없다. 그러므로 올라갈 수 있을 때까지 대기하고 있어야 한다.

![image.png](attachment:9fd3281e-3fd0-489c-8956-80dd6e142a06:image.png)

6초 시점에 1번 트럭이 다리를 지나가고, 다리의 무게가 4가 되었으므로 이제 3번 트럭이 다리에 올라갈 수 있다. 따라서, 다음과 같이 나타난다.

![image.png](attachment:e2613be4-9260-4546-9f17-a89c505360b8:image.png)

다음 7초 시점에서는 2번 트럭이 다리를 지나갔으며, 다리의 무게가 3이 되어 다음 4번 트럭도 다리에 올라갈 수 있다.

![image.png](attachment:4d7fe064-99a5-463e-bf77-c777b0db5f12:image.png)

이제 남은 트럭이 없으므로 다리 위에 있는 트럭이 모두 지나가기를 기다리면 된다. 마지막으로 올라간 트럭이 다리를 지나가기까지 다리의 길이만큼 시간이 소요되므로 마지막 트럭이 올라간 시점에 다리의 길이만큼 더해주면 모든 트럭이 다리를 지나는데 걸리는 시간을 구할 수 있다. 이 경우에는 12초가 되어야 4번 트럭이 다리를 모두 지나게 된다.

### 전체 코드

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new LinkedList<>();

        for (int i = 0; i < bridge_length; i++) {
            bridge.offer(0);
        }

        int bridge_weight = 0;
        int time = 0;
        int truck_index = 0;

        while(truck_index < truck_weights.length) {
            bridge_weight -= bridge.poll();
            if (bridge_weight + truck_weights[truck_index] <= weight) {
                bridge_weight += truck_weights[truck_index];
                bridge.offer(truck_weights[truck_index]);

                truck_index++;
            } else {
                bridge.offer(0);
            }

            time++;
        }

        return time + bridge_length;
    }
}
```
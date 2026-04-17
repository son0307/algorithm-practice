### 문제 요약

- 주어진 규칙에 따라 연산을 수행하는 이중 우선순위 큐에서 입력한 대로 연산을 수행한 후 최댓값, 최솟값을 반환하는 문제
    - 입력 순서에 따라 큐에 들어있는 원소들이 실시간으로 변하기에 매 원소마다 시뮬레이션해야함

## 핵심 포인트

이 문제에서 설명하듯이 큐에 숫자들이 수시로 들어오고, 마지막에는 최댓값과 최솟값을 구해야하므로 우선순위 큐를 이용해야 한다.

최댓값을 관리하는 우선순위 큐와 최솟값을 관리하는 우선순위 큐 2개를 사용하면 최댓갑과 최솟값을 손쉽게 찾을 수 있을 것이다.

```groovy
PriorityQueue<Integer> minPQ = new PriorityQueue<>();
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
```

이 때의 처리 과정은 다음과 같다.

- 새로운 값을 추가할 때
    - 최댓값 큐와 최솟값 큐 모두 값을 추가한다.
- 최댓값 또는 최솟값을 뺄 때
    - 최댓값 제거 : 최댓값 큐에서 가장 위에 있는 최댓값을 바로 반환, 최솟값 큐에게 해당 값을 지우라고 명령
    - 최솟값 제거 : 최솟값 큐에서 가장 위에 있는 최솟값을 바로 반환, 최댓갑 큐에게 해당 값을 지우라고 명령
- 결과 반환 시
    - 최댓값 큐로부터 최댓값을, 최솟값 큐로부터 최솟값을 받아 결과 반환

위 과정에 따라 입력된 연산들을 처리하면 정답을 얻을 수 있다.

### 전체 코드

```groovy
import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];

        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        for (String operation : operations) {
            String[] tokens = operation.split(" ");
            switch (tokens[0]) {
                case "I":
                    minPQ.add(Integer.parseInt(tokens[1]));
                    maxPQ.add(Integer.parseInt(tokens[1]));
                    break;
                case "D":
                    if (minPQ.isEmpty())
                        break;

                    if (tokens[1].equals("1")) {
                        int max = maxPQ.poll();
                        minPQ.remove(max);
                    } else {
                        int min = minPQ.poll();
                        maxPQ.remove(min);
                    }

                    if (minPQ.isEmpty()) {
                        minPQ.clear();
                        maxPQ.clear();
                    }
                    break;
            }
        }

        if (!minPQ.isEmpty()) {
            answer[0] = maxPQ.peek();
            answer[1] = minPQ.peek();
        }

        return answer;
    }
}
```

위 코드의 최악의 시간복잡도는 O(N^2)이다. 우선순위 큐는 힙트리를 이용하므로 O(N * log N)이 될거라고 생각하지만, 여기서는 remove(max)와 remove(min)으로 인해 삭제 시 시간복잡도가 O(N)이 되기 때문이다. PriorityQueue의 remove(T value)는 힙트리를 이용하지 않고, 원소들을 하나씩 순회하며 주어진 value와 동일한 값을 찾아 삭제한다. 이로 인해, O(N)이 되는 것이다.

이번 문제에서는 입력된 operations가 값 추가와 값 삭제가 섞여있어 위 코드가 정상적으로 통과가 된 것처럼 보인다. 하지만, 입력 수가 늘어난다거나 특정 패턴(500,000개 입력 후 500,000번 삭제)과 같이 입력되면 시간 초과가 발생할 수 있을 것이라 생각된다.

### 코드 2 : TreeMap 이용

Java에서 제공하는 TreeMap을 이용하면 위 문제를 O(N * log N)으로 해결할 수 있다. 이는 Red-Black 트리를 이용하여 값들을 정렬하는데, 이는 이진 트리이면서 높이를 자동으로 균등하게 맞추기 때문에 특정 값을 찾아낼 때 항상 O(log N)만 소요된다. 또한, 주어진 Key들을 내부적으로 오름차순으로 자동 정렬해두고, getFirstKey()와 getLastKey()로 최솟값과 최댓값을 손쉽게 얻을 수 있다.

- 전체 코드

    ```groovy
    public int[] solution(String[] operations) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (String operation : operations) {
            String[] tokens = operation.split(" ");
            String command = tokens[0];
            int value = Integer.parseInt(tokens[1]);
    
    				// TreeMap에 넣는 데이터는 <값, 등장 횟수>이다.
            if (command.equals("I")) {
                map.put(value, map.getOrDefault(value, 0) + 1);
            } else {
                if (map.isEmpty())
                    continue;
    
                int targetKey = (value == 1) ? map.lastKey() : map.firstKey();
    
                if (map.get(targetKey) == 1) {
                    map.remove(targetKey);
                } else {
                    map.put(targetKey, map.get(targetKey) - 1);
                }
            }
        }
    
        if (map.isEmpty()) {
            return new int[] {0, 0};
        } else {
            return new int[] {map.lastKey(), map.firstKey()};
        }
    }
    ```
### 문제 요약

- 처음 순위에서 해설자가 부른 이름에 따라 변화된 최종 순위를 반환하는 문제
    - 해설자가 부르는 이름은 추월한 선수의 이름이며, 상태가 실시간으로 변한다.

## 핵심 포인트

이 문제에서 중요한 포인트는 **추월하는 선수가 몇 번째에 있는가?**와 **그 앞에 있는 선수가 누구인가?**이다. 배열 내에서 추월하는 선수와 그 앞에 있는 선수를 서로 뒤바꿔야 하기 때문에 해당 선수들의 인덱스를 알아야 하기 때문이다.

간단히 생각하면 주어진 선수들의 이름을 바탕으로 배열을 순회하며 인덱스를 알아내고 그 앞의 선수와 swap하면 될 것 같지만, 이는 시간이 오래 걸리는 방법이다. 만약, 마지막 선수와 그 앞의 선수가  계속해서 위치를 바꾸는 경우라면 매번 배열을 끝까지 탐색해야 되기 때문이다.

따라서, 각 선수가 몇 번째에 있는지를 별도로 저장해두면 효율적으로 문제를 해결할 수 있다. 이를 위해 Map 자료구조를 사용한다. 선수 이름을 Key로, 현재 위치를 Value로 가지도록 구성한다. (배열로 쉽게 생각하기 위해서 인덱스를 저장하였다.)

```java
HashMap<String, Integer> ranks = new HashMap<>();
for (int i = 0; i < players.length; i++) {
    ranks.put(players[i], i);
}
```

이후, callings 배열을 순회하며 한 선수씩 앞 선수와 swap하며 추월을 시뮬레이션하면 된다.

### 전체 코드

```java
import java.util.HashMap;

class Solution {
    public String[] solution(String[] players, String[] callings) {
        String[] answer = new String[players.length];

        HashMap<String, Integer> ranks = new HashMap<>();
        for (int i = 0; i < players.length; i++) {
            ranks.put(players[i], i);
        }

        for (String calling : callings) {
            String target = players[ranks.get(calling) - 1];
            ranks.put(calling, ranks.get(calling) - 1);
            ranks.put(target, ranks.get(target) + 1);

            players[ranks.get(calling)] = calling;
            players[ranks.get(target)] = target;
        }

        return players;
    }
}
```

위 알고리즘의 시간 복잡도는 $O(N + M)$이다.

- 초기 ranks 맵을 구성하는데 선수 인원수만큼 소요된다. $O(N)$
- callings 배열을 순회하며 현재 위치를 알아내고 배열을 수정하는 과정은 O(1)이므로, 호명 횟수만큼 소요된다. $O(M)$
## 문제 요약

- 주어진 시작 단어(begin)에서 목표 단어(target)로 변환하기 위한 최소 단계를 찾는 문제
    - 한 번에 하나의 알파벳만 변경 가능
    - 변환 과정에 사용되는 단어는 words 리스트에 무조건 존재해야 함

## 핵심 포인트

이 문제는 단어들 간의 관계를 그림으로 표현하면 그래프와 같은 모양이 나온다. 즉, 그래프에서 두 점 사이의 최소 거리를 구하는 것과 같다고 볼 수 있으며, 최소 거리를 찾아내는 방법 중 최단 거리를 빠르게 찾아내는 BFS 알고리즘을 적용할 수 있다.

그러면 인접한 노드들 중에서 어떤 노드를 탐색할 지를 결정해야 한다. 여기서 사용할 수 있는 것이 첫번째 조건, 한 번에 하나의 알파벳만 변경 가능하다는 조건이다. 해당 조건에 따라 현재 보고 있는 단어와 다른 단어들의 알파벳을 하나씩 비교하여 다른 알파벳의 개수가 딱 1개인 경우만 찾아내도록 하면 된다.

```java
public boolean isConvertable(String src, String dest) {
    char[] src_char = src.toCharArray();
    char[] dest_char = dest.toCharArray();
    int cnt = 0;

    for (int i = 0; i < src_char.length; i++) {
        if (src_char[i] != dest_char[i]) {
            cnt++;
            if (cnt >= 2)
                return false;
        }
    }

    return true;
}
```

또한, 최소 거리를 찾아내야 하는 문제 특성상 중복된 노드를 방문하지 않도록 처리하는 것도 필요하다.

### 전체 코드

```java
import java.util.*;

class Solution {
    static class Node {
        String word;
        int rank;

        public Node(String word, int rank) {
            this.word = word;
            this.rank = rank;
        }
    }

    public boolean isConvertable(String src, String dest) {
        char[] src_char = src.toCharArray();
        char[] dest_char = dest.toCharArray();
        int cnt = 0;

        for (int i = 0; i < src_char.length; i++) {
            if (src_char[i] != dest_char[i]) {
                cnt++;
                if (cnt >= 2)
                    return false;
            }
        }

        return true;
    }

    public int solution(String begin, String target, String[] words) {
        boolean[] visited = new boolean[words.length];

        Queue<Node> q = new LinkedList<>();
        q.add(new Node(begin, 0));

        while(!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.word.equals(target))
                return cur.rank;

            for (int i = 0; i < words.length; i++) {
                if (!visited[i] && isConvertable(cur.word, words[i])) {
                    visited[i] = true;
                    q.add(new Node(words[i], cur.rank + 1));
                }
            }
        }
        
        return 0;
    }
}
```

위 알고리즘의 시간 복잡도는 $O(N^2 * L)$이다. (N은 단어의 개수, L은 단어의 길이).

- BFS 루프에서 모든 단어를 한번씩 순회: $N$
- 큐에서 단어 하나를 가져오고 다른 단어들과 비교: $N$
- 비교 과정에서 알파벳 하나하나 다른지 검사: $L$

위 과정에 따라 시간 복잡도가 결정된다.
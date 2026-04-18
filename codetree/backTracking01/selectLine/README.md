## 문제 요약

- **목표**: 수직선상에 주어진 $N$개의 선분 중, 서로 겹치지 않도록 가장 많이 고를 수 있는 선분의 수를 구한다.
- **조건**:
    1. 두 선분이 끝점을 공유하는 경우(예: `[1, 2]`와 `[2, 3]`)도 겹친 것으로 간주한다.
- **출력 항목**: 겹치지 않게 뽑을 수 있는 최대 선분의 수

## 핵심 포인트

- **부분집합(Subset) 백트래킹**: 각 선분에 대해 '선택한다' 혹은 '선택하지 않는다'의 2가지 갈래로 탐색을 진행하여 모든 조합을 확인한다.
- **정렬 (Sorting)**: 선분들을 시작점(`start`) 기준으로 미리 오름차순 정렬해 두면, 뒤에 오는 선분은 항상 앞 선분보다 같거나 오른쪽에 위치하므로 겹침 판별이 매우 쉬워진다.
- **상태 추적 최소화**: 겹침 여부를 확인하기 위해 지금까지 고른 모든 선분을 다시 뒤져볼 필요 없이, **'가장 마지막에 고른 선분의 끝점'** 하나만 기억하면 된다.

### 동작 원리

1. **정렬**: 주어진 $N$개의 선분을 시작점(`start`) 기준으로 오름차순 정렬한다.
2. **백트래킹 시작 (`dfs`)**:
    - 매개변수로 현재 확인할 선분의 인덱스(`idx`), 현재까지 고른 선분 개수(`count`), 마지막으로 고른 선분의 끝점(`lastEnd`)을 넘긴다. 초기 `lastEnd`는 -1로 시작한다.
3. **재귀 분기 (2갈래)**:
    - **선택하는 경우**: 현재 선분의 시작점(`current.start`)이 `lastEnd`보다 크다면 겹치지 않으므로, 이 선분을 선택하고 `count + 1`, `lastEnd`를 `current.end`로 갱신하여 다음 깊이로 내려간다.
    - **선택하지 않는 경우**: 현재 선분을 버리고 기존의 `count`와 `lastEnd`를 그대로 유지한 채 다음 깊이로 내려간다.
4. **최댓값 갱신**: 모든 선분을 다 확인했다면(`idx == n`), 현재까지 구한 `count`와 `max`를 비교해 갱신한다.

### 전체 코드

```java
import java.util.*;
import java.io.*;

class Line implements Comparable<Line>{
    int start;
    int end;

    public Line(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int compareTo(Line o) {
        return start - o.start;
    }
}

public class Main {
    static int max = Integer.MIN_VALUE;
    static ArrayList<Line> lines = new ArrayList<>();
    static int n;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            lines.add(new Line(start, end));
        }

        Collections.sort(lines);

        dfs(0, 0, -1);

        System.out.println(max);
    }

    public static void dfs(int idx, int count, int lastEnd) {
        if (idx == n) {
            max = Math.max(max, count);
            return;
        }

        Line current = lines.get(idx);

        // 현재 선분과 마지막 선분이 겹치지 않는 경우
        if (current.start > lastEnd) {
            dfs(idx + 1, count + 1, current.end);
        }

        // 현재 선분을 선택하지 않는 경우
        dfs(idx + 1, count, lastEnd);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 각 선분마다 ‘고른다’, ‘고르지 않는다’의 2가지 선택지가 존재한다. 따라서, 상태 공간 트리의 크기는 $2^N$이 된다.
- $N$의 최댓값이 15이므로, 최대 $2^{15} = 32,768$번의 재귀 호출이 일어난다.

최종적으로 위 알고리즘의 시간복잡도는 $O(2^N)$이다.
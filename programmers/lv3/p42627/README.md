### 문제 요약

- 작업들이 주어질 때, 해당 작업들을 도착 후 완료되기까지의 시간(반환 시간)의 평균을 구하는 문제
    - 단, 작업들은 순서대로 작동하는 것이 아닌 특정 규칙에 따라 우선적으로 실행되는 작업이 달라진다. 그 규칙은 다음과 같다.
        1. 소요시간이 짧은 작업
        2. 요청 시간이 빠른 작업
        3. 번호가 작은 작업

## 핵심 포인트

이 문제는 특정 시점마다 실행할 작업을 우선순위 규칙에 따라 적절히 선택하는 것이 중요하다. 작업을 하나씩 가져온다는 점에서 큐를 활용할 수 있을 것이고, 우선순위에 따라 작업들을 정렬해야 하니 큐 중에서도 우선순위 큐를 활용할 수 있다는 것을 눈치챌 수 있다.

위에서 말한 3가지 우선순위 규칙 중 3번째 규칙은 무시할 수 있다. 번호까지 생각해야 하는 경우는 소요시간과 요청 시간이 모두 동일한 경우인데, 이 때는 해당 작업들 중 어떤 작업을 먼저 시작하더라도 반환 시간은 달라지지 않는다. 따라서, 1번과 2번에 대해서만 생각하면 된다.

우선, 코드에서 명확하게 이해할 수 있도록 각 작업을 별도의 클래스로 변환하고 이들을 요청 시간을 기준으로 오름차순으로 정렬하였다.

```groovy
Work[] works = new Work[jobs.length];
for (int i = 0; i < jobs.length; i++) {
    Work work = new Work(jobs[i][0], jobs[i][1]);
    works[i] = work;
}
Arrays.sort(works, Comparator.comparingInt(work -> work.start));
```

이후, 이 작업들을 하나씩 가져오도록 큐로 변환하고, 현재 시간 기준으로 요청된 작업들을 소요 시간을 기준으로 정렬할 수 있도록 우선순위 큐를 구성하였다.

```groovy
Queue<Work> q = new LinkedList<>(List.of(works));
PriorityQueue<Work> pq = new PriorityQueue<>(Comparator.comparingInt(work -> work.duration));
```

우선순위 큐에서 소요 시간이 동일할 때, 요청 시간이 늦은 작업이 먼저 처리될 수도 있다. 하지만, 소요 시간이 동일하면 어떤 작업이 먼저 처리되어도 반환 시간의 합은 동일하기에 현재 조건 상에서 문제가 되지 않는다.

위와 같이 구성하고, 이제 각 작업들을 순회하면서 현재 시간과 총 소요 시간을 계산할 차례이다. 모든 작업을 처리할 때까지 다음과 같은 연산을 수행한다.

- 현재 시간을 기준으로 요청된 작업들을 우선순위 큐에 하나씩 넣는다.
- 우선순위 큐에 가능한 작업이 없으면 현재 시간을 가장 빠른 요청 시간으로 지정한다.
- 우선순위 큐 맨 위에 있는 작업을 가져와 반환 시간을 계산하고, 현재 시간을 해당 작업의 소요 시간만큼 늘린다.

위 과정이 모두 끝나면 모든 작업에 대한 반환 시간이 계산된 것이므로 그 평균을 반환한다.

### 전체 코드

```groovy
import java.util.*;

class Work {
    int start;
    int duration;

    public Work(int start, int duration) {
        this.start = start;
        this.duration = duration;
    }
}

public int solution(int[][] jobs) {
    Work[] works = new Work[jobs.length];
    for (int i = 0; i < jobs.length; i++) {
        Work work = new Work(jobs[i][0], jobs[i][1]);
        works[i] = work;
    }
    Arrays.sort(works, Comparator.comparingInt(work -> work.start));
    Queue<Work> q = new LinkedList<>(List.of(works));
    PriorityQueue<Work> pq = new PriorityQueue<>(Comparator.comparingInt(work -> work.duration));

    int exec = 0;
    int time = 0;

    while (!q.isEmpty() || !pq.isEmpty()) {
        while (!q.isEmpty() && q.peek().start <= time) {
            pq.add(q.poll());
        }

        if (pq.isEmpty()) {
            time = q.peek().start;
            continue;
        }

        Work work = pq.poll();
        exec += time + work.duration - work.start;
        time += work.duration;
    }

    return exec / works.length;
}
```
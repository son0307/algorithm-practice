## 문제 요약

- 주어진 게임 맵에서 목적지까지 도달하는 최소 거리를 구하는 문제
    - 캐릭터의 시작 위치는 (1, 1), 도착 위치는 (n, m)
    - 도달하지 못 하는 경우엔 -1을 반환

## 핵심 포인트

이 문제는 BFS를 이용하여 최단 거리를 대표적인 문제이다. (1, 1)에서부터 시작하여 이동할 수 있는 모든 방향으로 한 칸씩 이동하며 도착하였는지 아닌지를 검사하면 되는 문제이다. 다만, 최소 거리를 구하는 것이므로 이전에 이미 지나간 경로는 다시 지나가지 않도록 따로 검사 로직을 구현해야 한다.

```java
boolean[][] visited = new boolean[maps.length][maps[0].length];
int[] dx = {0, 0, 1, -1};
int[] dy = {1, -1, 0, 0};
```

그 역할을 visited 배열이 수행한다. 이동할 수 있는 방향을 고려할 때 해당 위치의 visited 배열 값이 true이면 이미 지나간 경로이므로 건너뛰도록 할 것이다.

dx, dy는 현재 위치를 기준으로 상, 하, 좌, 우로 이동하도록 하는 값들이다. 이렇게 미리 정의해두고 반복문을 통해 dx, dy를 순회하며 이동할 좌표값을 구하도록 하면 깔끔하게 코드를 작성할 수 있다.

```java
static class pos {
    int x;
    int y;
    int distance;

    public pos(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}
```

현재 위치 및 시작점으로부터의 거리를 저장하는 별도의 객체를 구성하였다. 이 정보를 큐에 넣고 빼며 탐색을 수행할 것이다.

```java
while(!q.isEmpty()) {
    pos cur = q.poll();
    if (cur.x == maps[0].length - 1 && cur.y == maps.length - 1) {
        return cur.distance;
    }

    for (int i = 0; i < 4; i++) {
        int nx = cur.x + dx[i];
        int ny = cur.y + dy[i];
        if (nx >= 0 && ny >= 0 && nx < maps[0].length && ny < maps.length && maps[ny][nx] == 1 && !visited[ny][nx]) {
            visited[ny][nx] = true;
            q.add(new pos(nx, ny, cur.distance + 1));
        }
    }
}
```

BFS의 핵심 부분이다. 큐에서 좌표 정보를 하나 가져오고 그 위치가 목표 지점인지 아닌지 검사한다. 목표 지점이면 해당 위치까지의 거리를 반환한다.

해당 위치에서 이동할 수 있는 좌표를 찾아낸다. 좌표가 주어진 맵 내부에 존재하고, 방문하지 않았으며, 막혀있지 않은 경우 이동할 수 있으므로 새로 큐에 삽입한다. 방문 처리를 처음에 큐에서 뽑았을 때 처리하는 것이 아닌 여기 부분에서 처리하는데, 그 이유는 해당 좌표로 도달할 수 있는 여러 경우가 존재할 수 있는데 딱 한 번만 검사하기 위해서이다. 이를 통해 불필요한 반복을 줄일 수 있다.

### 전체 코드

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    static class pos {
        int x;
        int y;
        int distance;

        public pos(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public int solution(int[][] maps) {
        boolean[][] visited = new boolean[maps.length][maps[0].length];
        Queue<pos> q = new LinkedList<>();
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        q.add(new pos(0, 0, 1));
        visited[0][0] = true;
        while(!q.isEmpty()) {
            pos cur = q.poll();
            if (cur.x == maps[0].length - 1 && cur.y == maps.length - 1) {
                return cur.distance;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if (nx >= 0 && ny >= 0 && nx < maps[0].length && ny < maps.length && maps[ny][nx] == 1 && !visited[ny][nx]) {
                    visited[ny][nx] = true;
                    q.add(new pos(nx, ny, cur.distance + 1));
                }
            }
        }

        return -1;
    }
}
```

이 알고리즘의 시간복잡도는 $O(N * M)$이다. (N * M은 게임 맵의 크기). 막힌 공간이 없는 완전히 열린 게임 맵의 경우, 해당 맵의 모든 부분을 탐색해야 하기 때문이다.
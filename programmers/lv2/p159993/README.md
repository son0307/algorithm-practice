## 핵심 포인트

이 문제는 ‘S’에서 시작하여 ‘L’에 도착한 뒤 ‘E’에 도착하는 문제이다. 즉, ‘S’ → ‘L’ 탐색 1번, ‘L’ → ‘E’ 탐색 1번, 총 2번의 탐색을 수행해야 한다. 그래프의 크기가 크면 백트래킹을 이용한 DFS를 통해 탐색하여 효율성을 따져야 하지만, 이번 문제는 그래프의 크기가 그리 크지 않아 단순히 BFS 탐색 기법을 이용한 2번의 탐색만으로 문제를 해결할 수 있다.

다만, 주의해야 할 점은 ‘S’ → ‘L’ 탐색과 ‘L’ → ‘E’ 탐색 중 하나라도 도달 불가능한 경우가 발생하면 정답 자체가 -1이 되는 것이다. 불필요한 탐색을 줄이기 위해 탐색이 끝났을 때 그 결과를 검사하여 정답을 검사하도록 한다.

### 전체 코드

```java
int[] dc = {0, 0, -1, 1};
int[] dr = {1, -1, 0, 0};

public int solution(String[] maps) {
    int StoL = 0;
    int LtoE = 0;

    int n = maps.length;
    int m = maps[0].length();

    String[][] map = new String[n][m];
    for (int i = 0; i < n; i++) {
        map[i] = maps[i].split("");
    }

    boolean[][] visited = new boolean[n][m];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (map[i][j].equals("S"))
                StoL = bfs(map, visited, i, j, "L");
        }
    }
    if (StoL == -1)
        return -1;

    visited = new boolean[n][m];

    for(int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (map[i][j].equals("L"))
                LtoE = bfs(map, visited, i, j, "E");
        }
    }
    if (LtoE == -1)
        return -1;

    System.out.println("StoL = " + StoL + " LtoE = " + LtoE);
    return StoL + LtoE;
}

private int bfs(String[][] map, boolean[][] visited, int x, int y, String target) {
    Queue<int[]> q = new LinkedList<>();
    visited[x][y] = true;
    q.add(new int[]{x, y, 1});
    
    while(!q.isEmpty()) {
        int[] cur = q.poll();
        int curX = cur[0];
        int curY = cur[1];
        int count = cur[2];

        for (int i = 0; i < 4; i++) {
            int nx = curX + dc[i];
            int ny = curY + dr[i];

            if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length && !visited[nx][ny] && !map[nx][ny].equals("X")) {
                visited[nx][ny] = true;
                if(map[nx][ny].equals(target))
                    return count;
                q.add(new int[]{nx, ny, count + 1});
            }
        }
    }

    return -1;
}
```
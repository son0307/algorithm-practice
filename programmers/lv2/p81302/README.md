## 핵심 포인트

이 문제의 핵심은 한 응시자가 앉아있는 자리에서 맨허튼 거리가 2 이하인 모든 경우를 검사하는 것이 가장 중요하다. 즉, 다음과 같은 범위를 검사해야 한다.

|  |  | 2 |  |  |
| --- | --- | --- | --- | --- |
|  | 2 | 1 | 2 |  |
| 2 | 1 | P | 1 | 2 |
|  | 2 | 1 | 2 |  |
|  |  | 2 |  |  |

저 범위 내에 중간에 X로 구분되어지지 않고 P가 위치한 경우, 거리두기가 제대로 지켜지지 않은 상태라고 할 수 있다.

주의해야하는 점은 위쪽 방향에 파티션이 있다고 하더라도 왼쪽 위나 오른쪽 위와 같은 자리는 다른 방향으로 도달할 수 있다는 점이다. 이를 유의하여 특정 방향이 막혀있다고 해당 방향을 통하는 위치들을 배재하는 것이 아닌 뚫려있는 방향으로 탐색을 수행해야 한다. 그림으로 나타내면 다음과 같다.

|  | X | V |  |
| --- | --- | --- | --- |
|  | (x,y) | O | V |
|  |  | V |  |

위쪽 방향은 파티션으로 막혀있으므로 검사를 생략한다. 오른쪽 방향은 빈 테이블이 있으므로 검사를 수행한다. 이 빈 테이블을 통해 다른 응시자에 도달하면 거리두기가 지켜지지 않은 것이다.

## 해결 과정

문제는 다음과 같은 과정으로 해결할 수 있다.

1. 상하좌우 중 빈 테이블의 상하좌우 검사
2. 이 과정에서 다른 P 발견 시 거리두기가 지켜지지 않은 것
3. 1,2 과정을 모든 응시자에 대해 수행
4. 거리두기를 위반한 경우가 없는 경우 제대로 거리두기를 지킨 것으로 판단

### 기본 세팅

한 배열 안에서 문자열로 전달된 대기실의 배치를 2차원 배열로 전환해야 문제를 해결하는데 어려움이 없다.

```java
int[] answer = new int[places.length];
    for(int i=0; i<answer.length; i++) {
        // 대기실 세팅
        String[] place = places[i];
        char[][] room = new char[place.length][];
        for(int j=0; j<place.length; j++) {
            room[j] = place[j].toCharArray();
        }
        
        // 거리두기 검사
    }
```

전달된 대기실 배치 중 하나를 가져와 문자열 한 줄씩 읽으며 toCharArray()를 이용하여 2차원 배열로 변환한다.

---

차후에 상하좌우 검사를 수행하게 되는데 이를 편하게 하기 위해서 X좌표와 Y좌표가 어떻게 변해야하는지를 미리 정해둔다.

```java
private static final int[] dx = {0, 0, -1, 1};
private static final int[] dy = {-1, 1, 0, 0};
```

이를 이용하면 상하좌우를 검사하는 곳에서 반복문으로 하나씩 사용하여 상하좌우의 좌표를 구할 수 있으니 코드가 간결해진다.

### 1. 상하좌우 중 빈 테이블 검사

P가 저장된 위치의 상하좌우를 검사하여 O인 곳은 추가 검사를, X는 검사 생략을, P는 거리두기를 지키지 않았다는 것을 반환하는 것을 수행해야 한다.

```java
private boolean isDistanced(char[][] room) {
    for(int y=0; y<room.length; y++) {
        for(int x=0; x<room[y].length; x++) {
            if(room[y][x] != 'P') continue;

            // P이면 상하좌우 검사
            for (int d=0; d<4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                // 범위 초과 시 계산 생략
                if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || room[ny][nx] == 'X') continue;

                // 검사 시 P가 있으면 false 반환
                if(room[ny][nx] == 'P') return false;

                if(isNextTo(room, ny, nx, y, x)) return false;
            }
        }
    }

    return true;
}
```

사전에 정의해둔 dx와 dy를 이용하여 상하좌우 검사를 수행한다. 상하좌우 검사 시 그 좌표가 범위를 벗어나거나 X인 경우에는 검사를 생략한다. P가 있으면 거리두기를 지키지 않았다는 뜻으로 false를 반환한다. O인 경우에는 추가적인 검사를 필요로 하므로 새 메서드를 만들고 검사에 필요한 데이터를 넘겨준다.

추가적인 검사도 제대로 통과한다면 거리두기를 제대로 지킨것이므로 true를 반환한다.

### 2. 빈 테이블의 상하좌우 검사

이전 단계에서 탐색된 빈 테이블에 대해서도 상하좌우 검사를 수행해야 한다. 그렇게 해야 맨허튼 거리가 2인 응시자들도 찾아낼 수 있기 때문이다.

```java
private boolean isNextTo(char[][] room, int y, int x, int py, int px) {
    for (int d=0; d<4; d++) {
        int nx = x + dx[d];
        int ny = y + dy[d];

        if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || (py == ny && px == nx)) continue;

        if(room[ny][nx] == 'P') return true;
    }

    return false;
}
```

검사 로직 자체는 이전 단계와 동일하다. 다만, 이전 단계에서 검사한 P는 검사 대상에서 제외해야 하므로 검사 생략 조건에 새로운 조건을 추가한다. 이번 함수의 목표는 근처에 또 다른 P가 위치해있는지를 검사하는 것이기에 주변에 P가 있으면 true를 반환하고, 없으면 false를 반환한다.

### 3. 검사 결과 저장

```java
// 거리두기 검사
if(isDistanced(room)) {
    answer[i] = 1;
} else {
    answer[i] = 0;
}
```

거리두기를 제대로 지켜 isDistanced 메서드가 true를 반환하면 1을, 아니면 0을 저장한다.

### 전체 코드

```java
class Solution {
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};

    private boolean isDistanced(char[][] room) {
        for(int y=0; y<room.length; y++) {
            for(int x=0; x<room[y].length; x++) {
                if(room[y][x] != 'P') continue;
                
                // P이면 상하좌우 검사
                for (int d=0; d<4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    // 범위 초과 시 계산 생략
                    if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || room[ny][nx] == 'X') continue;
                    
                    // 검사 시 P가 있으면 false 반환
                    if(room[ny][nx] == 'P') return false;

                    if(isNextTo(room, ny, nx, y, x)) return false;
                }
            }
        }

        return true;
    }

    private boolean isNextTo(char[][] room, int y, int x, int py, int px) {
        for (int d=0; d<4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || (py == ny && px == nx)) continue;

            if(room[ny][nx] == 'P') return true;
        }

        return false;
    }

    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        for(int i=0; i<answer.length; i++) {
            // 대기실 세팅
            String[] place = places[i];
            char[][] room = new char[place.length][];
            for(int j=0; j<place.length; j++) {
                room[j] = place[j].toCharArray();
            }

            // 거리두기 검사
            if(isDistanced(room)) {
                answer[i] = 1;
            } else {
                answer[i] = 0;
            }
        }

        return answer;
    }
}
```
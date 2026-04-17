## 핵심 포인트

이 문제의 핵심은 2차원으로 나타난 점의 좌표를 2차원 배열(격자판)에 잘 위치시키는 것이 가장 중요하다. 점의 좌표를 구하는 방법은 참고 사항에 나와있는 것을 이용하면 손 쉽게 해결할 수 있지만 이를 통해 구한 좌표를 2차원 배열에 맞추어 위치시키는 과정에서 주의해야 한다.

## 해결 과정

문제는 다음과 같은 과정으로 해결할 수 있다.

1. 교점 구하기
2. 교점들이 표현될 격자판의 크기 정하기
3. 교점들을 격자판에 나타내기

### 1. 교점 구하기

우선, 교점을 나타낼 하나의 객체를 정의하였다.

```java
private static class Point {
    public final long x;
    public final long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}
```

---

그 다음, 주어진 직선들을 2개씩 전달하도록 코드를 구성하고 교점을 구할 메서드를 정의한다.

```java
ArrayList<Point> points = new ArrayList<>();

for(int i=0; i<line.length; i++) {
    for (int j=i+1; j<line.length; j++) {
        Point meet = getMeet(line[i][0], line[i][1], line[i][2], line[j][0], line[j][1], line[j][2]);

        if(meet == null) continue;
        points.add(meet);
    }
}
```

주어질 직선들은 line에 저장된다. line에서 직선 2개를 가져와 교점을 구할 메서드 getMeet에 전달한다.

**주의사항**

제한사항에서 A, B, C는 -100,000 이상 100,000 이하인 정수라고 한다. 이를 보고 자료형을 int로 사용하는 경우가 있다. 각 숫자를 나타내는데는 int로도 충분하지만, 이후 계산에서 int의 범위를 초과하여 오버플로우가 발생할 수 있다. 따라서, int가 아닌 long을 사용하는 것이 오류를 일으키지 않는다.

```java
private Point getMeet(long a, long b, long e, long c, long d, long f) {
    long denominator = (a * d - b * c);

    // 분모가 0이면 교차점 없음(평행 또는 일치)
    if (denominator == 0) {
        return null;
    }

    double x = (double) (b * f - e * d) / denominator;
    double y = (double) (e * c - a * f) / denominator;

    if (x % 1 == 0 && y % 1 == 0)
        return new Point((long) x, (long) y);
    else
        return null;
}
```

우선, AD-BC가 0인지 확인한다. 분모가 0인 경우는 교점이 존재하지 않거나 무수히 많은 경우이기에 제외되어야 하는 경우이기 때문에 교점 자체를 구할 수 없으므로 null을 반환한다. 분모가 0이 아니라면 참고 사항에 나와있는 식대로 계산하여 교점의 X좌표와 Y좌표를 구한다.
이후 해당 좌표들이 정수인지 아닌지 판별한다. 1로 나누었을 때 나머지가 0이면 정수이고 아니면 정수가 아니다. 이를 이용하여 정수이면 교점 객체에 담아 반환하고, 아니면 null을 반환한다. 이를 주어진 모든 직선들에 대해 수행하면 정수인 모든 교점을 구할 수 있다.

### 2. 교점들이 표현될 격자판의 크기 정하기

문제에서는 격자판을 최소 크기로 나타내는 것을 요구한다. 따라서, 교점들의 좌표를 이용하여 격자판의 크기를 직접 구해야한다.

```java
long minX = Long.MAX_VALUE;
long maxX = Long.MIN_VALUE;
long minY = Long.MAX_VALUE;
long maxY = Long.MIN_VALUE;

for(Point p : points) {
    minX = Math.min(p.x, minX);
    maxX = Math.max(p.x, maxX);
    minY = Math.min(p.y, minY);
    maxY = Math.max(p.y, maxY);
}

char[][] board = new char[(int) (maxY-minY+1)][(int) (maxX-minX+1)];
for (char[] chars : board) {
    Arrays.fill(chars, '.');
}
```

이는 Math.min과 Math.max를 이용하여 구할 수 있다. 모든 교점들을 가지고 와 하나씩 비교하며 X 좌표의 최댓값과 최솟값, Y 좌표의 최댓값과 최솟값을 구해 격자판의 크기를 정하고 .으로 모두 채운다.

### 3. 교점들을 격자판에 나타내기

격자판이 준비되었으면 교점들을 그 위에 나타낼 차례이다. 이 과정에서 주의해야 할 점이 있는데 2차원 좌표에서는 (X, Y) 순으로 좌표를 표현하지만 2차원 배열에서는 (Y, X) 순으로 나타난다는 점과 - 좌표를 바로 표현할 수 없다는 점이다. 이를 고려하여 직접 2차원 배열에 맞춰 좌표를 변환해야 한다.

```java
private String[] drawBoard(char[][] board, ArrayList<Point> points, long minX, long maxY) {
    String[] boardString = new String[board.length];

		// 좌표 변환
    for(Point p : points) {
        long x = (p.x - minX);
        long y = -(p.y - maxY);

        board[(int) y][(int) x] = '*';
    }

    for(int i=0; i<board.length; i++) {
        boardString[i] = String.valueOf(board[i]);
    }

    return boardString;
}
```

현재 격자판의 가장 왼쪽 위 모서리를 (0, 0)으로 기준을 잡고 이에 맞춰 2차원 좌표를 배열의 인덱스로 변환한다. 이후, 계산된 인덱스의 문자를 *로 변환하면 정확한 위치에 표시된다. 이 과정을 모든 교점들에 대해 수행하고 한 줄씩 String으로 반환한다.

### 전체코드

```java
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
	
    private static class Point {
        public final long x;
        public final long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private Point getMeet(long a1, long a2, long a3, long b1, long b2, long b3) {
        long denominator = (a1 * b2 - a2 * b1);

        // 분모가 0이면 교차점 없음(평행 또는 일치)
        if (denominator == 0) {
            return null;
        }

        double x = (double) (a2 * b3 - a3 * b2) / denominator;
        double y = (double) (a3 * b1 - a1 * b3) / denominator;

        if (x % 1 == 0 && y % 1 == 0)
            return new Point((long) x, (long) y);
        else
            return null;
    }

    private String[] drawBoard(char[][] board, ArrayList<Point> points, long minX, long maxY) {
        String[] boardString = new String[board.length];

        for(Point p : points) {
            long x = (p.x - minX);
            long y = -(p.y - maxY);

            board[(int) y][(int) x] = '*';
        }

        for(int i=0; i<board.length; i++) {
            boardString[i] = String.valueOf(board[i]);
        }

        return boardString;
    }

    public String[] solution(int[][] line) {
        ArrayList<Point> points = new ArrayList<>();

        for(int i=0; i<line.length; i++) {
            for (int j=i+1; j<line.length; j++) {
                Point meet = getMeet(line[i][0], line[i][1], line[i][2], line[j][0], line[j][1], line[j][2]);

                if(meet == null) continue;
                points.add(meet);
            }
        }

        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;

        for(Point p : points) {
            minX = Math.min(p.x, minX);
            maxX = Math.max(p.x, maxX);
            minY = Math.min(p.y, minY);
            maxY = Math.max(p.y, maxY);
        }

        char[][] board = new char[(int) (maxY-minY+1)][(int) (maxX-minX+1)];
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }

        return drawBoard(board, points, minX, maxY);
    }
}
```

###
package codetree.p2026a1.EscapeMazeWithWallFollowing;

import java.util.*;

public class Main {
    public static final int MAX_N = 100;
    public static final int[] dx = {1, 0, -1, 0};
    public static final int[] dy = {0, 1, 0, -1};

    public static int n;
    public static int time;

    public static int curX, curY, curDir;
    public static char[][] map = new char[MAX_N+1][MAX_N+1];
    public static boolean[][][] visited = new boolean[MAX_N+1][MAX_N+1][4];

    public static boolean inRange(int x, int y) {
        return 1 <= x && 1 <= y && x <= n && y <= n;
    }

    public static boolean isWall(int x, int y) {
        return map[y][x] == '#';
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        curY = sc.nextInt();
        curX = sc.nextInt();

        curDir = 0;

        for (int i = 1; i <= n; i++) {
            String line = sc.next();
            for (int j = 1; j <= n; j++) {
                map[i][j] = line.charAt(j - 1);
            }
        }

        do {
            System.out.println("curX: " + curX + " curY: " + curY + " curDir: " + curDir);
            if(visited[curY][curX][curDir]) {
                System.out.println(-1);
                return;
            }

            visited[curY][curX][curDir] = true;

            int nextX = curX + dx[curDir];
            int nextY = curY + dy[curDir];

            if(!inRange(nextX, nextY)) {
                time++;
                break;
            }
            else if(isWall(nextX, nextY)) {
                curDir = (curDir - 1 + 4) % 4;
            }
            else {
                int rx = nextX + dx[(curDir + 1) % 4];
                int ry = nextY + dy[(curDir + 1) % 4];

                if(isWall(rx, ry)) {
                    curX = nextX; curY = nextY;
                    time++;
                }
                else {
                    curX = rx; curY = ry;
                    curDir = (curDir + 1) % 4;
                    time += 2;
                }
            }
        } while (inRange(curX, curY));

        System.out.println(time);
    }
}

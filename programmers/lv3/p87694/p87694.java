package programmers.lv3.p87694;

import java.util.ArrayDeque;
import java.util.Queue;

public class p87694 {
    static class Pos {
        int x, y;
        int distance;

        public Pos(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int[][] map = new int[101][101];
        boolean[][] visited = new boolean[101][101];

        for(int[] r : rectangle) {
            int y1 = r[1] * 2, y2 = r[3] * 2;
            int x1 = r[0] * 2, x2 = r[2] * 2;

            for(int y = y1; y <= y2; y++) {
                for (int x = x1; x <= x2; x++) {

                    if(x > x1 && x < x2 && y > y1 && y < y2) {
                        map[y][x] = 2;
                    }
                    else if (map[y][x] != 2) {
                        map[y][x] = 1;
                    }
                }
            }
        }

        Queue<Pos> q = new ArrayDeque<>();
        visited[characterY * 2][characterX * 2] = true;
        q.add(new Pos(characterX * 2, characterY * 2, 0));

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            if(cur.x == itemX * 2 && cur.y == itemY * 2)
                return cur.distance / 2;

            for(int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if(ny >= 0 && ny < map.length && nx >= 0 && nx < map[0].length && !visited[ny][nx] && map[ny][nx] == 1) {
                    visited[ny][nx] = true;
                    q.add(new Pos(nx, ny, cur.distance + 1));
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        p87694 p = new p87694();
        int[][] rectangle = {{1,1,7,4},{3,2,5,5},{4,3,6,9},{2,6,8,8}};
        int characterX = 1;
        int characterY = 3;
        int itemX = 7;
        int itemY = 8;
        System.out.println(p.solution(rectangle, characterX, characterY, itemX, itemY));
    }
}

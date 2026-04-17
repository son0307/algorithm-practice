package programmers.lv2.p1844;

import java.util.LinkedList;
import java.util.Queue;

public class p1844 {
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
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<pos> q = new LinkedList<>();
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

    public static void main(String[] args) {
        int[][] maps = {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,0},{0,0,0,0,1}};
        System.out.println(new p1844().solution(maps));
    }
}

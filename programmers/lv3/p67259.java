package programmers.lv3;

import java.util.LinkedList;
import java.util.Queue;

public class p67259 {
    public int[] dx = {0, 0, 1, 0, -1};
    public int[] dy = {0, -1, 0, 1, 0};

    public class Node {
        int x;
        int y;
        int direction;
        int cost;

        public Node(int x, int y, int direction, int cost) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
        }
    }

    public int solution(int[][] board) {
        int answer = Integer.MAX_VALUE;
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(0, 0, 0, 0));
        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (cur.x == board.length-1 && cur.y == board[0].length-1) {
                if (cur.cost < answer)
                    answer = cur.cost;
            }

            for (int i = 1; i <= 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < board.length && ny < board[0].length) {
                    if (board[ny][nx] != 1) {
                        if ((cur.x == 0 && cur.y == 0) || cur.direction == i) {
                            q.add(new Node(nx, ny, i, cur.cost + 100));
                        }
                        else if (cur.direction == (i + 1) % 5 + 1) {
                            q.add(new Node(nx, ny, (i + 1) % 5 + 1, cur.cost + 600));
                        }
                        else if (cur.direction == (i + 3) % 5 + 1) {
                            q.add(new Node(nx, ny, (i + 3) % 5 + 1, cur.cost + 600));
                        }
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] board = {{0,0,0}, {0,0,0}, {0,0,0}};
        System.out.println(new p67259().solution(board));
    }
}

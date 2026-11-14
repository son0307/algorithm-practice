package swea.p1954;

import java.util.*;

public class Solution {
    static int[] dx = {1, 0, -1 ,0};
    static int[] dy = {0, 1, 0, -1};

    static int n;
    static int[][] arr;

    static boolean inRange(int y, int x) {
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    static void move() {
        int cnt = 1;

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0,0});
        arr[0][0] = 1;
        int dir = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            int ny = cur[0] + dy[dir];
            int nx = cur[1] + dx[dir];

            if(!inRange(ny,nx) || arr[ny][nx] != 0) {
                dir = (dir + 1) % 4;
                ny = cur[0] + dy[dir];
                nx = cur[1] + dx[dir];

                if(arr[ny][nx] != 0) break;
            }

            q.add(new int[]{ny,nx});
            arr[ny][nx] = ++cnt;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for(int i = 1; i <= t; i++) {
            n = sc.nextInt();
            arr = new int[n][n];

            System.out.println("#" + i);

            if(n == 1) {
                System.out.println("1");
                continue;
            }

            move();

            for(int y = 0; y < n; y++) {
                for(int x = 0; x < n; x++) {
                    System.out.print(arr[y][x] + " ");
                }
                System.out.println();
            }
        }
    }
}

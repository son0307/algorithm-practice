package codetree.bfs01.moveToMaxKTimes;

import java.io.*;
import java.util.*;

class Cell implements Comparable<Cell> {
    int r, c, val;

    public Cell(int r, int c, int val) {
        this.r = r;
        this.c = c;
        this.val = val;
    }

    @Override
    public int compareTo(Cell o) {
        // 1순위. 적혀있는 숫자가 클수록 (내림차순)
        if (this.val != o.val) return o.val - this.val;
        // 2순위. 행 번호가 작을수록 (오름차순)
        if (this.r != o.r) return this.r - o.r;
        // 3순위. 열 번호가 작을수록 (오름차순)
        return this.c - o.c;
    }
}

public class Main {
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int startR = Integer.parseInt(st.nextToken()) - 1;
        int startC = Integer.parseInt(st.nextToken()) - 1;

        for(int count = 0; count < k; count++) {
            Queue<Cell> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][n];

            Cell bestCell = null;   // 이번 턴에 이동할 최적의 칸
            int startVal = map[startR][startC];     // 탐색 시작점

            q.add(new Cell(startR, startC, startVal));
            visited[startR][startC] = true;

            while (!q.isEmpty()) {
                Cell cur = q.poll();

                // 시작점이 아니면 최적의 후보 갱신
                if (cur.r != startR || cur.c != startC) {
                    if (bestCell == null || cur.compareTo(bestCell) < 0) {
                        bestCell = cur;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    int nextR = cur.r + dr[i];
                    int nextC = cur.c + dc[i];
                    if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n) continue;

                    if (!visited[nextR][nextC] && map[nextR][nextC] < startVal) {
                        visited[nextR][nextC] = true;
                        q.add(new Cell(nextR, nextC, map[nextR][nextC]));
                    }
                }
            }

            // 이동할 수 있는 칸이 없으면 시뮬레이션 종료
            if (bestCell == null) break;

            // 이동 처리 (다음 턴의 시작점)
            startR = bestCell.r;
            startC = bestCell.c;
        }

        System.out.println((startR+1) + " " + (startC+1));
    }
}

package baekjoon.p14503;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        int[] dy = {-1, 0, 1, 0};
        int[] dx = {0, 1, 0, -1};
        int count = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][M];
        boolean[][] visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        while(true) {
            // 1. 현재 칸이 청소되지 않았으면 청소
            if (!visited[r][c]) {
                count++;
                visited[r][c] = true;
                continue;
            }

            // 주변 4칸 중 청소되지 않은 빈 칸 탐색
            boolean hasUncleaned = false;
            for (int i = 0; i < 4; i++) {
                int nr = r + dy[i];
                int nc = c + dx[i];
                if (board[nr][nc] == 0 && !visited[nr][nc]) {
                    hasUncleaned = true;
                    break;
                }
            }

            // 2. 청소되지 않은 빈 칸이 없는 경우
            if (!hasUncleaned) {
                // 바라보는 방향 뒷 칸 좌표 계산
                int backR = r - dy[d];
                int backC = c - dx[d];

                // 2.2. 후진 불가능, 작동 정지
                if (board[backR][backC] == 1)
                    break;

                // 2.1. 한 칸 후진
                r = backR;
                c = backC;
            }
            // 3. 청소되지 않은 빈 칸이 있는 경우
            else {
                // 3.1. 반시계 방향으로 90도 회전
                d = (d + 3) % 4;

                // 3.2. 바라보는 방향 앞쪽 칸이 청소되지 않은 칸이면 한 칸 전진
                int frontR = r + dy[d];
                int frontC = c + dx[d];

                if (board[frontR][frontC] == 0 && !visited[frontR][frontC]) {
                    r = frontR;
                    c = frontC;
                }
            }
        }

        System.out.println(count);
    }
}

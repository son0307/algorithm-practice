package p14891;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] gears = new int[4][8];
        for (int i = 0; i < 4; i++) {
            String[] gear = br.readLine().split("");
            for (int j = 0; j < 8; j++)
                gears[i][j] = Integer.parseInt(gear[j]);
        }

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int target = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());

            // 1. 각 톱니바퀴의 회전 방향 결정 (0: 정지, 1: 시계, -1: 반시계)
            int[] dirs = new int[4];
            dirs[target] = dir;

            // 기준점으로부터 왼쪽 톱니바퀴들 검사
            for (int j = target; j > 0; j--) {
                // 내 9시 방향(6)과 왼쪽 톱니의 3시 방향(2) 비교
                if (gears[j][6] != gears[j - 1][2])
                    dirs[j - 1] = -dirs[j]; // 극이 다르면 반대 방향
                else
                    break;  // 극이 같으면 회전 전파 x
            }

            // 기준점으로부터 오른쪽 톱니바퀴들 검사
            for (int j = target; j < 3; j++) {
                // 내 3시 방향(2)과 오른쪽 톱니의 9시 방향(6) 비교
                if (gears[j][2] != gears[j + 1][6])
                    dirs[j + 1] = -dirs[j]; // 극이 다르면 반대 방향
                else
                    break;  // 극이 같으면 회전 전파 x
            }

            // 2. 결정된 회전 방향대로 톱니들 회전
            for (int j = 0; j < 4; j++) {
                if (dirs[j] == 1) { // 시계 방향 회전
                    int last = gears[j][7];
                    for (int x = 7; x > 0; x--) {
                        gears[j][x] = gears[j][x - 1];
                    }
                    gears[j][0] = last;
                }
                else if (dirs[j] == -1) { // 반시계 방향 회전
                    int first = gears[j][0];
                    for (int x = 0; x < 7; x ++) {
                        gears[j][x] = gears[j][x + 1];
                    }
                    gears[j][7] = first;
                }
            }
        }

        // 3. 점수 계산
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (gears[i][0] == 1)
                score += (int) Math.pow(2, i);
        }

        System.out.println(score);
    }
}

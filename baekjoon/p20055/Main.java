package baekjoon.p20055;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] belt = new int[n*2];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n*2; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }
        boolean[] robot = new boolean[n];
        int round = 0;
        int zeroCount = 0;

        while (true) {
            round++;

            // 1.1. 벨트 회전
            int last = belt[belt.length - 1];
            for (int i = belt.length - 2; i >= 0; i--) {
                belt[i + 1] = belt[i];
            }
            belt[0] = last;

            // 1.2. 로봇 회전
            for (int i = robot.length - 2; i >= 0; i--) {
                robot[i + 1] = robot[i];
            }
            robot[0] = false;

            // 로봇이 내리는 위치에 도착하면 내리기
            if(robot[robot.length - 1]) robot[robot.length - 1] = false;

            // 2. 로봇 이동
            for (int i = robot.length-2; i >= 0; i--) {
                if (robot[i] && !robot[i+1] && belt[i+1] != 0) {
                    robot[i] = false;
                    robot[i+1] = true;
                    if (--belt[i+1] == 0)
                        zeroCount++;
                }
            }

            // 로봇이 내리는 위치에 도착하면 내리기
            if(robot[robot.length - 1]) robot[robot.length - 1] = false;

            // 3. 로봇 올리기
            if (belt[0] != 0) {
                robot[0] = true;
                if (--belt[0] == 0)
                    zeroCount++;
            }

            // 4. 내구도가 0인 칸의 개수 검사
            if (zeroCount >= k)
                break;
        }

        System.out.println(round);
    }
}

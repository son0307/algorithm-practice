package p14891;

import java.io.*;
import java.util.*;

class GearRotation {
    int num;
    int direction;

    public GearRotation(int num, int direction) {
        this.num = num;
        this.direction = direction;
    }
}

public class MainMe {
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
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            Queue<GearRotation> needToCheck = new LinkedList<>();
            boolean[] checked = new boolean[4];
            needToCheck.add(new GearRotation(x - 1, d));

            while (!needToCheck.isEmpty()) {
                GearRotation cur = needToCheck.poll();
                if (checked[cur.num]) continue;
                checked[cur.num] = true;

                // 왼쪽 기어 극 다르면 회전 시킬 목록에 추가
                if (cur.num != 0 && gears[cur.num][6] != gears[cur.num - 1][2]) {
                    needToCheck.add(new GearRotation(cur.num - 1, -cur.direction));
                }

                // 오른쪽 기어 극 다르면 회전 시킬 목록에 추가
                if (cur.num != 3 && gears[cur.num][2] != gears[cur.num + 1][6]) {
                    needToCheck.add(new GearRotation(cur.num + 1, -cur.direction));
                }

                int[] newGear = new int[8];
                // 시계 방향 회전
                if (cur.direction == 1) {
                    int last = gears[cur.num][7];
                    for (int j = 0; j < 7; j++) {
                        newGear[j + 1] = gears[cur.num][j];
                    }
                    newGear[0] = last;
                }
                // 반시계 방향 회전
                else if (cur.direction == -1) {
                    int first = gears[cur.num][0];
                    for (int j = 7; j > 0; j--) {
                        newGear[j - 1] = gears[cur.num][j];
                    }
                    newGear[7] = first;
                }
                gears[cur.num] = newGear;
            }
        }

        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (gears[i][0] == 1)
                score += (int) Math.pow(2, i);
        }
        System.out.println(score);
    }
}

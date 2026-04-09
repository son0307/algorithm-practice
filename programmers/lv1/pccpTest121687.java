package programmers.lv1;

import java.util.Arrays;

public class pccpTest121687 {
    public int[] solution(String command) {
        int[] loc = new int[2];

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int dir = 0;

        for (int i = 0; i < command.length(); i++) {
            switch (command.charAt(i)) {
                case 'R':
                    dir = (dir + 1) % 4;
                    break;
                case 'L':
                    if (dir == 0)
                        dir = 3;
                    else
                        dir = (dir - 1) % 4;
                    break;
                case 'G':
                    loc[0] += dx[dir];
                    loc[1] += dy[dir];
                    break;
                case 'B':
                    loc[0] -= dx[dir];
                    loc[1] -= dy[dir];
            }
        }

        return loc;
    }

    public static void main(String[] args) {
        String command = "LGLGLGLG";
        System.out.println(Arrays.toString(new pccpTest121687().solution(command)));
    }
}

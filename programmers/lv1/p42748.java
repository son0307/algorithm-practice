package programmers.lv1;

import java.util.Arrays;

public class p42748 {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for(int i = 0; i < commands.length; i++) {
            int[] copy = Arrays.copyOfRange(array, commands[i][0] - 1, commands[i][1]);
            Arrays.sort(copy);
            answer[i] = copy[commands[i][2] - 1];
        }

        return answer;
    }

    public static void main(String[] args) {
        p42748 p = new p42748();

        System.out.println("p = " + Arrays.toString(p.solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2,5,3}, {4,4,1}, {1,7,3}})));
    }
}

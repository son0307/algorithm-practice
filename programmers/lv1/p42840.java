package programmers.lv1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class p42840 {
    private static int[][] no = {{1, 2, 3, 4, 5}, {2, 1, 2, 3, 2, 4, 2, 5}, {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}};

    public Object[] solution(int[] answers) {
        int[] answer = new int[3];
        for(int i=0; i<3; i++) {
            for(int j = 0; j < answers.length; j++) {
                if(answers[j] == no[i][j % no[i].length]) answer[i]++;
            }
        }

        int max = Math.max(Math.max(answer[0], answer[1]), answer[2]);
        List<Integer> ans = new ArrayList<>();
        for(int i=0; i<3; i++) {
            if(answer[i] == max) ans.add(i+1);
        }
        return ans.toArray();
    }

    public static void main(String[] args) {
        p42840 p = new p42840();
        System.out.println(Arrays.toString(p.solution(new int[]{1, 3, 2, 4, 2})));
    }
}

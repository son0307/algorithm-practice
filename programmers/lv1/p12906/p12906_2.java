package programmers.lv1.p12906;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class p12906_2 {
    public int[] solution(int[] arr) {
        List<Integer> answer = new ArrayList<>();
        int preNum = 10;

        for (int num : arr) {
            if (preNum != num) {
                answer.add(num);
                preNum = num;
            }
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        p12906_2 p = new p12906_2();
        int[] arr = {4,4,4,3,3};
        System.out.println(Arrays.toString(p.solution(arr)));
    }
}

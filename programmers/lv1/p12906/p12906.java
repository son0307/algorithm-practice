package programmers.lv1.p12906;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class p12906 {
    public int[] solution(int[] arr) {
        List<Integer> answer = new ArrayList<>();
        Stack<Integer> st = new Stack<>();

        for (int i : arr) {
            if (st.empty() || st.peek() != i) {
                st.push(i);
                answer.add(i);
            }
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        p12906 p = new p12906();
        int[] arr = {4,4,4,3,3};
        System.out.println(Arrays.toString(p.solution(arr)));
    }
}

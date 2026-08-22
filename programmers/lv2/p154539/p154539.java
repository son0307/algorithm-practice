package programmers.lv2.p154539;

import java.util.Arrays;
import java.util.Stack;

public class p154539 {
    public int[] solution(int[] numbers) {
        int[] answer = new int[numbers.length];
        Arrays.fill(answer, -1);
        Stack<Integer> st = new Stack<>();

        for(int i = 0; i < answer.length; i++) {
            while(!st.isEmpty() && numbers[st.peek()] < numbers[i]) {
                answer[st.pop()] = numbers[i];
            }

            st.push(i);
        }

        return answer;
    }

    public static void main(String[] args) {
        p154539 p = new p154539();
        int[] numbers1 = {2,3,3,5};
        int[] numbers2 = {6,1,5,3,7,2};
        System.out.println(Arrays.toString(p.solution(numbers1)));
        System.out.println(Arrays.toString(p.solution(numbers2)));
    }
}

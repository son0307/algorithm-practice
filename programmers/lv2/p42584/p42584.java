package programmers.lv2.p42584;

import java.util.Arrays;
import java.util.Stack;

public class p42584 {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < prices.length; i++) {
            while (!st.empty() && prices[st.peek()] > prices[i]) {
                int index = st.pop();
                answer[index] = i - index;
            }

            st.push(i);
        }

        while(!st.empty()) {
            int index = st.pop();
            answer[index] = (answer.length - 1) - index;
        }

        return answer;
    }

    public static void main(String[] args) {
        p42584 p = new p42584();
        int[] prices = {1,2,3,2,3};
        int[] prices2 = {1,2,3,1,2,3,0};

        System.out.println(Arrays.toString(p.solution(prices2)));
    }
}

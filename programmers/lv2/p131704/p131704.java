package programmers.lv2.p131704;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class p131704 {
    public int solution(int[] order) {
        int answer = 0;

        Queue<Integer> q = new ArrayDeque<>();
        Stack<Integer> st = new Stack<>();
        for(int i = 1; i <= order.length; i++)
            q.add(i);

        for(int i = 0; i < order.length; i++) {
            int goal = order[i];

            if(!st.isEmpty() && goal == st.peek()) {
                st.pop();
                answer++;
                continue;
            }

            while(!q.isEmpty() && goal != q.peek()) {
                st.push(q.poll());
            }

            if(q.isEmpty())
                break;

            answer++;
            q.poll();
        }

        return answer;
    }

    public static void main(String[] args) {
        p131704 p = new p131704();
        int[] order = {5,4,3,2,1};
        System.out.println(p.solution(order));
    }
}

package programmers.lv2;

import java.util.PriorityQueue;

public class pccpTest121688 {
    public int solution(int[] ability, int number) {
        int answer = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : ability)
            pq.add(i);

        for (int i = 0; i < number; i++) {
            int num1 = pq.poll();
            int num2 = pq.poll();
            pq.add(num1 + num2); pq.add(num1 + num2);
        }

        while (!pq.isEmpty())
            answer += pq.poll();

        return answer;
    }

    public static void main(String[] args) {
        int[] ability = {1, 2, 3, 4};
        int number = 3;
        System.out.println(new pccpTest121688().solution(ability, number));
    }
}

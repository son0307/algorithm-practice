package programmers.lv2;

import java.util.*;

public class p42586 {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> answer = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < progresses.length; i++) {
            int day = (int) Math.ceil((double) (100 - progresses[i]) / speeds[i]);

            q.add(day);
        }

        while(!q.isEmpty()) {
            int day = q.poll();
            int count = 1;
            while (!q.isEmpty() && q.peek() <= day) {
                count++;
                q.poll();
            }
            answer.add(count);
        }

        return answer.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        p42586 p = new p42586();
        int[] progresses = {90, 90, 90};
        int[] speeds = {1, 5, 4};
        System.out.println(Arrays.toString(p.solution(progresses, speeds)));
    }
}

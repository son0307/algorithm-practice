package programmers.lv2.p118667;

import java.util.ArrayDeque;
import java.util.Queue;

public class p118667 {
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;

        long total1 = 0L;
        long total2 = 0L;
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        for(int i = 0; i < queue1.length; i++) {
            q1.add(queue1[i]);
            q2.add(queue2[i]);
            total1 += queue1[i];
            total2 += queue2[i];
        }

        if((total1 % 2) != (total2 % 2)) return -1;

        while(total1 != total2) {
            if(answer > (queue1.length + queue2.length) * 4) return -1;

            if(q1.isEmpty() || q2.isEmpty())
                return -1;

            if(total1 > total2) {
                int n = q1.poll();
                total1 -= n;
                q2.add(n);
                total2 += n;
            } else {
                int n = q2.poll();
                total2 -= n;
                q1.add(n);
                total1 += n;
            }

            answer++;
        }

        return answer;
    }
}

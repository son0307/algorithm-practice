package programmers.lv2.p42626;

import java.util.PriorityQueue;

public class p42626 {
    public int solution(int[] scoville, int K) {
        int count = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int s : scoville) {
            pq.add(s);
        }

        while(pq.size() > 1 && pq.peek() < K) {
            pq.add(pq.poll() + pq.poll() * 2);
            count++;
        }

        if(pq.peek() < K)
            return -1;

        return count;
    }

    public static void main(String[] args) {
        p42626 p = new p42626();
        int[] scoville = {1, 2, 3, 9, 10, 12};
        int k = 110;

        System.out.println(p.solution(scoville, k));
    }
}

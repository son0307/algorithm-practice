package programmers.lv2.p42627;

import java.util.*;

public class p426827 {
    static class Work {
        int id;
        int requestedTime;
        int duration;

        public Work(int id, int requestedTime, int duration) {
            this.id = id;
            this.requestedTime = requestedTime;
            this.duration = duration;
        }
    }

    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (j1, j2) -> j1[0] - j2[0]);
        int currentTime = 0;
        int totalReturnTime = 0;

        Queue<Work> q = new ArrayDeque<>();
        for(int i = 0; i < jobs.length; i++)
            q.add(new Work(i, jobs[i][0], jobs[i][1]));

        PriorityQueue<Work> pq = new PriorityQueue<>((w1, w2) -> {
            if(w1.duration == w2.duration)
                return w1.requestedTime - w2.requestedTime;
            else
                return w1.duration - w2.duration;
        });

        while(!q.isEmpty() || !pq.isEmpty()) {
            while(!q.isEmpty() && q.peek().requestedTime <= currentTime)
                pq.add(q.poll());

            if(pq.isEmpty()) {
                currentTime = q.peek().requestedTime;
                continue;
            }

            Work currentWork = pq.poll();
            currentTime += currentWork.duration;
            totalReturnTime += currentTime - currentWork.requestedTime;
        }

        return totalReturnTime / jobs.length;
    }

    public static void main(String[] args) {
        p426827 p = new p426827();
        int[][] jobs = {{5,3}, {6,10}, {9,3}};
        System.out.println(p.solution(jobs));
    }
}

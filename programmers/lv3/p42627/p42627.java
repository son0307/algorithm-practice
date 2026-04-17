package programmers.lv3.p42627;

import java.util.*;

class Work {
    int start;
    int duration;

    public Work(int start, int duration) {
        this.start = start;
        this.duration = duration;
    }
}

public class p42627 {
    public int solution(int[][] jobs) {
        Work[] works = new Work[jobs.length];
        for (int i = 0; i < jobs.length; i++) {
            Work work = new Work(jobs[i][0], jobs[i][1]);
            works[i] = work;
        }
        Arrays.sort(works, Comparator.comparingInt(work -> work.start));
        Queue<Work> q = new LinkedList<>(List.of(works));
        PriorityQueue<Work> pq = new PriorityQueue<>(Comparator.comparingInt(work -> work.duration));

        int exec = 0;
        int time = 0;

        while (!q.isEmpty() || !pq.isEmpty()) {
            while (!q.isEmpty() && q.peek().start <= time) {
                pq.add(q.poll());
            }

            if (pq.isEmpty()) {
                time = q.peek().start;
                continue;
            }

            Work work = pq.poll();
            exec += time + work.duration - work.start;
            time += work.duration;
        }

        return exec / works.length;
    }

    public static void main(String[] args) {
        int[][] jobs = {{0, 3}, {1, 9}, {3, 5}};
        System.out.println(new p42627().solution(jobs));
    }
}

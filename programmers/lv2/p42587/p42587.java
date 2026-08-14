package programmers.lv2.p42587;

import java.util.*;

public class p42587 {
    static class Process {
        int location;
        int priority;

        public Process(int location, int priority) {
            this.location = location;
            this.priority = priority;
        }
    }

    public int solution(int[] priorities, int location) {
        int answer = 1;
        Queue<Process> q = new ArrayDeque<>();

        // 작업 큐에 저장
        for(int i = 0; i < priorities.length; i++) {
            q.add(new Process(i, priorities[i]));
        }

        // 높은 우선순위대로 정렬
        Arrays.sort(priorities);
        int priorityIndex = priorities.length - 1;

        // 작업 시뮬레이션
        while(!q.isEmpty()) {
            Process process = q.poll();

            if (process.priority == priorities[priorityIndex]) {
                if (process.location == location) {
                    return answer;
                }

                answer++;
                priorityIndex--;

            } else {
                q.add(process);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        p42587 p = new p42587();

        int[] priorities = {1, 1, 9, 1, 1, 1};
        int location = 0;
        System.out.println(p.solution(priorities, location));
    }
}


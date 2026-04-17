package programmers.lv3.pccpTest121686;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Program {
    int score;
    int callTime;
    int runTime;

    public Program(int score, int callTime, int runTime) {
        this.score = score;
        this.callTime = callTime;
        this.runTime = runTime;
    }
}

public class pccpTest121686 {
    public long[] solution(int[][] program) {
        long[] answer = new long[11];

        PriorityQueue<Program> callTimePq = new PriorityQueue<>(Comparator.comparingInt(p -> p.callTime));
        PriorityQueue<Program> waitPq = new PriorityQueue<>((p1, p2) -> {
            if (p1.score == p2.score) return Integer.compare(p1.callTime, p2.callTime);
            return Integer.compare(p1.score, p2.score);
        });

        for (int[] p : program) {
            callTimePq.offer(new Program(p[0], p[1], p[2]));
        }

        long currentTime = callTimePq.peek().callTime;
        int count = 0;
        int totalPrograms = program.length;

        while (count < totalPrograms) {
            while (!callTimePq.isEmpty() && callTimePq.peek().callTime <= currentTime)
                waitPq.offer(callTimePq.poll());

            if (waitPq.isEmpty()) {
                Program next = callTimePq.poll();
                currentTime = next.callTime;
                waitPq.offer(next);

                while(!callTimePq.isEmpty() && callTimePq.peek().callTime <= currentTime)
                    waitPq.offer(callTimePq.poll());
            }

            Program current = waitPq.poll();
            answer[current.score] += (currentTime - current.callTime);
            currentTime += current.runTime;
            count++;
        }

        answer[0] = currentTime;
        return answer;
    }

    public static void main(String[] args) {
        int[][] program = {{1, 10000000, 1000}, {2, 10000000, 1000}, {3, 10000000, 1000}, {4, 10000000, 1000}};
        System.out.println(Arrays.toString(new pccpTest121686().solution(program)));
    }
}

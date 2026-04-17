package programmers.lv3.p42628;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class p42628 {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];

        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        for (String operation : operations) {
            String[] tokens = operation.split(" ");
            switch (tokens[0]) {
                case "I":
                    minPQ.add(Integer.parseInt(tokens[1]));
                    maxPQ.add(Integer.parseInt(tokens[1]));
                    break;
                case "D":
                    if (minPQ.isEmpty())
                        break;

                    if (tokens[1].equals("1")) {
                        int max = maxPQ.poll();
                        minPQ.remove(max);
                    } else {
                        int min = minPQ.poll();
                        maxPQ.remove(min);
                    }

                    if (minPQ.isEmpty()) {
                        minPQ.clear();
                        maxPQ.clear();
                    }
                    break;
            }
        }

        if (!minPQ.isEmpty()) {
            answer[0] = maxPQ.peek();
            answer[1] = minPQ.peek();
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] operations = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
        System.out.println(Arrays.toString(new p42628().solution(operations)));
    }
}

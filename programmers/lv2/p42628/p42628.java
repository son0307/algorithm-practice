package programmers.lv2.p42628;

import java.util.*;

public class p42628 {
    public int[] solution(String[] operations) {
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();

        for(String o : operations) {
            String[] exec = o.split(" ");
            int num = Integer.parseInt(exec[1]);

            if(exec[0].equals("I")) {
                maxPQ.add(num);
                minPQ.add(num);
            } else {
                if (num == 1) {
                    minPQ.remove(maxPQ.poll());
                } else {
                    maxPQ.remove(minPQ.poll());
                }
            }
        }

        if(maxPQ.isEmpty()) {
            return new int[]{0,0};
        } else {
            return new int[]{maxPQ.peek(), minPQ.peek()};
        }
    }

    public static void main(String[] args) {
        p42628 p = new p42628();
        String[] operations1 = {"I 16", "I 16", "D -1"};
//        String[] operations2 = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};

        System.out.println(Arrays.toString(p.solution(operations1)));
//        System.out.println(Arrays.toString(p.solution(operations2)));
    }
}

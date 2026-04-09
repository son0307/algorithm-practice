package programmers.lv3;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class p42628_2 {
    public int[] solution(String[] operations) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (String operation : operations) {
            String[] tokens = operation.split(" ");
            String command = tokens[0];
            int value = Integer.parseInt(tokens[1]);

            if (command.equals("I")) {
                map.put(value, map.getOrDefault(value, 0) + 1);
            } else {
                if (map.isEmpty())
                    continue;

                int targetKey = (value == 1) ? map.lastKey() : map.firstKey();

                if (map.get(targetKey) == 1) {
                    map.remove(targetKey);
                } else {
                    map.put(targetKey, map.get(targetKey) - 1);
                }
            }
        }

        if (map.isEmpty()) {
            return new int[] {0, 0};
        } else {
            return new int[] {map.lastKey(), map.firstKey()};
        }
    }

    public static void main(String[] args) {
        String[] operations = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
        System.out.println(Arrays.toString(new p42628_2().solution(operations)));
    }
}

package programmers.lv1.p68644;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class p68644 {
    public int[] solution(int[] numbers) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                set.add(numbers[i] + numbers[j]);
            }
        }

        int[] answer = set.stream().mapToInt(i -> i).sorted().toArray();
        return answer;
    }

    public static void main(String[] args) {
        p68644 p = new p68644();
        System.out.println(Arrays.toString(p.solution(new int[]{5,0})));
    }
}

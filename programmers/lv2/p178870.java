package programmers.lv2;

import java.util.Arrays;

public class p178870 {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];

        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < sequence.length; right++) {
            sum += sequence[right];

            while (sum > k && left <= right) {
                sum -= sequence[left++];
            }

            if (sum == k) {
                int currentLength = right - left;

                if (currentLength < minLength) {
                    minLength = currentLength;
                    answer[0] = left;
                    answer[1] = right;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] sequence1 = {1,2,3,4,5};
        int k1 = 7;
        int[] sequence2 = {1,1,1,2,3,4,5};
        int k2 = 5;
        int[] sequence3 = {2,2,2,2,2};
        int k3 = 6;

        p178870 p = new p178870();
        System.out.println("Test 1: " + Arrays.toString(p.solution(sequence1, k1)));
        System.out.println("Test 2: " + Arrays.toString(p.solution(sequence2, k2)));
        System.out.println("Test 3: " + Arrays.toString(p.solution(sequence3, k3)));
    }
}

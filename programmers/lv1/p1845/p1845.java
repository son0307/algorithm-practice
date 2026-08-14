package programmers.lv1.p1845;

import java.util.HashSet;

public class p1845 {
    public int solution(int[] nums) {
        HashSet<Integer> hs = new HashSet<>();
        for (int n : nums)
            hs.add(n);
        int selectable = nums.length / 2;

        return Math.min(hs.size(), selectable);
    }

    public static void main(String[] args) {
        p1845 p = new p1845();

        int[] nums = {3,3,3,2,2,2};
        System.out.println(p.solution(nums));
    }
}

package programmers.lv1.p42862;

import java.util.HashSet;

public class p42862 {
    public int solution(int n, int[] lost, int[] reserve) {
        HashSet<Integer> lostSet = new HashSet<>();
        HashSet<Integer> reserveSet = new HashSet<>();

        for (int i : lost)
            lostSet.add(i);
        for (int j : reserve) {
            if (lostSet.contains(j))
                lostSet.remove(j);
            else
                reserveSet.add(j);
        }

        for (int j : reserveSet) {
            if (lostSet.contains(j - 1))
                lostSet.remove(j - 1);
            else if (lostSet.contains(j + 1))
                lostSet.remove(j + 1);
        }

        return n - lostSet.size();
    }

    public static void main(String[] args) {
        int n = 5;
        int[] lost = {2, 4};
        int[] reserve = {1, 3, 5};
        System.out.println(new p42862().solution(n, lost, reserve));
    }
}

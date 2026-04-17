package programmers.lv2.p92342;

import java.util.Arrays;

public class p92342 {
    int maxDiff = -1;
    int[] maxRyan = {-1};

    public void calcDiff(int[] apeach, int[] ryan) {
        int ryanScore = 0;
        int apeachScore = 0;

        for (int i = 0; i <= 10; i++) {
            if (apeach[i] == 0 && ryan[i] == 0) continue;
            if (apeach[i] >= ryan[i]) {
                apeachScore += (10 - i);
            } else {
                ryanScore += (10 - i);
            }
        }

        int diff = ryanScore - apeachScore;

        if (diff > 0) {
            if (diff > maxDiff) {
                maxDiff = diff;
                maxRyan = ryan.clone();
            } else if (diff == maxDiff) {
                if (isPriority(ryan)) {
                    maxRyan = ryan.clone();
                }
            }
        }
    }

    public boolean isPriority(int[] ryan) {
        for (int i = 10; i >= 0; i--) {
            if (ryan[i] > maxRyan[i]) return true;
            else if (ryan[i] < maxRyan[i]) return false;
        }
        return false;
    }

    public void ryanShooting(int index, int arrows, int[] ryan, int[] apeach) {
        if (index == 11) {
            calcDiff(apeach, ryan);
            return;
        }

        if (index == 10) {
            ryan[index] = arrows;
            ryanShooting(index + 1, 0, ryan, apeach);
            ryan[index] = 0;
            return;
        }

        if (arrows > apeach[index]) {
            ryan[index] = apeach[index] + 1;
            ryanShooting(index + 1, arrows - ryan[index], ryan, apeach);
            ryan[index] = 0;
        }

        ryanShooting(index + 1, arrows, ryan, apeach);
    }

    public int[] solution(int n, int[] info) {
        maxDiff = -1;
        maxRyan = new int[]{-1};

        ryanShooting(0, n, new int[11], info);

        System.out.println("maxDiff = " + maxDiff);
        System.out.println("maxRyan = " + Arrays.toString(maxRyan));
        return maxRyan;
    }

    public static void main(String[] args) {
        int n = 10;
        int[] info = new int[]{0,0,0,0,0,0,0,0,3,4,3};
        System.out.println(Arrays.toString(new p92342().solution(n, info)));
    }
}

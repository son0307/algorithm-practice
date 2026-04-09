package programmers.lv2;

public class p340212 {
    public int solution(int[] diffs, int[] times, long limit) {
        int min = 1;
        int max = 100000;

        while(true) {
            int level = (min + max) / 2;
            long time = times[0];
            for (int i = 1; i < diffs.length; i++) {
                if (diffs[i] > level) {
                    long wrongTimes = diffs[i] - level;
                    time += wrongTimes * (times[i - 1] + times[i]);
                }
                time += times[i];
            }

            System.out.println("level: " + level + " min: " + min + " max: " + max);
            System.out.println("time: " + time);

            if (time > limit)
                min = level + 1;
            else
                max = level;

            if (min >= max)
                return min;
        }
    }

    public static void main(String[] args) {
        int[] diffs = {1, 5, 3};
        int[] times = {2, 4, 7};
        long limit = 30;
        System.out.println(new p340212().solution(diffs, times, limit));
    }
}

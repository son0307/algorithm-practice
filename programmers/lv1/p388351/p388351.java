package programmers.lv1.p388351;

public class p388351 {
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int[] recognize_times = new int[schedules.length];
        int result = 0;

        for (int i = 0; i < recognize_times.length; i++) {
            int schedule = schedules[i] + 10;
            if (schedule % 100 >= 60)
                schedule += 40;

            recognize_times[i] = schedule;
        }

        for (int i = 0; i < schedules.length; i++) {
            Boolean isSave = true;

            for (int j = 0; j < 7; j++) {
                int currentDay = startday + j;
                if (currentDay % 7 == 6 || currentDay % 7 == 0)
                    continue;

                if (timelogs[i][j] > recognize_times[i]) {
                    isSave = false;
                    break;
                }
            }

            if(isSave) result++;
        }

        return result;
    }

    public static void main(String[] args) {
        p388351 p = new p388351();
        int[] schedules = new int[]{750, 855, 700, 720};
        int[][] timelogs = new int[][]{{710, 700, 650, 735, 700, 931, 912},
                {908, 901, 805, 815, 800, 831, 835},
                {705, 701, 702, 705, 710, 710, 711},
                {707, 731, 859, 913, 934, 931, 905}};
        int startday = 1;
        System.out.println(p.solution(schedules, timelogs, startday));
    }
}

package programmers.lv3;

public class p43238 {
    public long solution(int n, int[] times) {
        long answer;
        
        long min = 1;
        long max = 1000000000000000000L;
        
        while (min < max) {
            answer = 0;
            long mid = (min + max) / 2;
            
            for (int time : times) {
                answer += mid / time;
            }
            if (answer >= n) max = mid;
            else min = mid + 1;
        }
        
        return min;
    }

    public static void main(String[] args) {
        p43238 p = new p43238();
        System.out.println(p.solution(6, new int[]{3, 6, 7, 11}));
    }
}

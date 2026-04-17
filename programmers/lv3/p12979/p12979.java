package programmers.lv3.p12979;

public class p12979 {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        int current = 1;
        int range = 2 * w + 1;

        for (int station : stations) {
            int start = station - w;
            if (current < start) {
                int dist = start - current;
                answer += dist / range;
                if (dist % range != 0)
                    answer++;
            }

            current = station + w + 1;
        }

        if (current <= n) {
            int dist = n - current + 1;
            answer += dist / range;
            if (dist % range != 0)
                answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 11;
        int[] stations = {9};
        int w = 2;
        System.out.println(new p12979().solution(n, stations, w));
    }
}

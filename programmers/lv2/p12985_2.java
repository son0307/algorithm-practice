package programmers.lv2;

public class p12985_2 {
    public int solution(int n, int a, int b)
    {
        int round = 0;
        while (a != b) {
            if (a % 2 != 0) a++;
            if (b % 2 != 0) b++;

            a /= 2;
            b /= 2;
            round++;
        }

        return round;
    }

    public static void main(String[] args) {
        p12985_2 p = new p12985_2();
        int answer = p.solution(16, 7, 8);
        System.out.println(answer);
    }
}

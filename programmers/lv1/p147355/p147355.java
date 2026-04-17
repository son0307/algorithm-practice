package programmers.lv1.p147355;

public class p147355 {
    public int solution(String t, String p) {
        int answer = 0;

        for (int i = 0; i <= t.length() - p.length(); i++) {
            String s = t.substring(i, i + p.length());
            long num = Long.parseLong(s);
            if (num <= Long.parseLong(p)) {
                answer++;
                System.out.println(num);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        String t = "123456789123456789123456789123456789";
        String p = "123456789123456789";
        System.out.println(new p147355().solution(t, p));
    }
}

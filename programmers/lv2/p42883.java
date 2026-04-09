package programmers.lv2;

public class p42883 {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        int len = number.length();

        for (int i = 0; i < len; i++) {
            char c = number.charAt(i);

            while (k > 0 && sb.length() > 0 && sb.charAt(sb.length() - 1) < c) {
                sb.deleteCharAt(sb.length() - 1);
                k--;
            }
            sb.append(c);
        }

        if (k > 0) {
            sb.setLength(sb.length() - k);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String number = "10";
        int k = 1;
        System.out.println(new p42883().solution(number, k));
    }
}

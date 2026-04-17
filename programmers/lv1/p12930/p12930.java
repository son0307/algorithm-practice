package programmers.lv1.p12930;

public class p12930 {
    public String solution(String s) {
        int n = 0;
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if(Character.isAlphabetic(c)) {
                if(n == 0) {
                    sb.append(Character.toUpperCase(c));
                    n++;
                } else {
                    sb.append(Character.toLowerCase(c));
                    n--;
                }
            } else {
                sb.append(c);
                n = 0;
            }
        }

        String answer = sb.toString();
        return answer;
    }

    public static void main(String[] args) {
        p12930 p = new p12930();
        System.out.println(p.solution("try hello world"));
    }
}

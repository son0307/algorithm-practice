package programmers.lv1;

public class p12926 {
    public String solution(String s, int n) {
        char[] chars = s.toCharArray();
        for(int i=0; i<chars.length; i++) {
            char c = chars[i];
            if(c == ' ') continue;

            int codeInt = c + n;
            if (c >= 'a' && c <= 'z' && codeInt > 'z') {
                codeInt = 'a' + (codeInt - 'z') - 1;
            } else if (c >= 'A' && c <= 'Z' && codeInt > 'Z') {
                codeInt = 'A' + (codeInt - 'Z') - 1;
            }

            chars[i] = (char) codeInt;
        }

        String answer = String.valueOf(chars);
        return answer;
    }

    public static void main(String[] args) {
        p12926 p = new p12926();
        System.out.println(p.solution("XYZA", 4));
    }
}

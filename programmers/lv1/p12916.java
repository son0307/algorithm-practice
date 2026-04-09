package programmers.lv1;

public class p12916 {
    boolean solution(String s) {
        int p = 0;
        int y = 0;

        for(char c : s.toLowerCase().toCharArray()) {
            if(c == 'p') {
                p++;
            } else if(c == 'y') {
                y++;
            }
        }

        return p == y;
    }

    public static void main(String[] args) {
        p12916 p = new p12916();
        System.out.println(p.solution("pyyppPYy"));
    }
}

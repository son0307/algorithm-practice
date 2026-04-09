package programmers.lv1;

public class p12918 {
    public boolean solution(String s) {
        return s.matches("[0-9]{4}|[0-9]{6}");
    }

    public boolean solution2(String s) {
        boolean answer = false;
        if (s.length() == 4 || s.length() == 6) {
            answer = true;
            for(char c : s.toCharArray()) {
                if (!Character.isDigit(c)) {
                    answer = false;
                    break;
                }
            }
        }

        return answer;
    }
}

package programmers.lv2.p76502;

import java.util.Stack;

public class p76502 {
    public boolean check(int start, String s) {
        Stack<Character> st = new Stack<>();

        for (int j = 0; j < s.length(); j++) {
            int offset = ((start + j) % s.length());
            char c = s.charAt(offset);

            if (c == ']' || c == ')' || c == '}') {
                if (st.empty()) return false;

                if (c == ']' && st.peek() == '[') st.pop();
                else if (c == ')' && st.peek() == '(') st.pop();
                else if (c == '}' && st.peek() == '{') st.pop();
            }
            else
                st.push(c);

        }

        return st.empty();
    }

    public int solution(String s) {
        int answer = 0;

        for (int i = 0; i < s.length(); i++) {
            if (check(i, s)) answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        p76502 p = new p76502();
        String s1 = "[](){}";
        String s2 = "}]()[{";
        String s3 = "[)(]";
        String s4 = "{{{";

        System.out.println(p.solution(s4));
    }
}

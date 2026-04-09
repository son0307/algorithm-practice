package programmers.lv2;

import java.util.Stack;

public class p12909 {
    boolean solution(String s) {
        Stack<String> st = new Stack<>();
        for (String str : s.split("")) {
            if (str.equals(")")) {
                if (st.empty())
                    return false;
                else
                    st.pop();
            } else {
                st.push(str);
            }
        }

        return st.empty();
    }

    public static void main(String[] args) {
        p12909 p = new p12909();
        System.out.println(p.solution("(())()"));
    }
}

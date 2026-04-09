package p1918;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] mid = sc.nextLine().split("");
        StringBuilder sb = new StringBuilder();
        Stack<String> st = new Stack<>();

        for (String m : mid) {
            switch (m) {
                case "(": st.push(m); break;
                case ")": {
                    while (!st.isEmpty() && !st.peek().equals("("))
                        sb.append(st.pop());
                    st.pop();
                } break;
                case "+":
                case "-": {
                    while (!st.isEmpty() && !st.peek().equals("("))
                        sb.append(st.pop());
                    st.push(m);
                } break;
                case "*":
                case "/" : {
                    while (!st.isEmpty() && (st.peek().equals("*") || st.peek().equals("/")))
                        sb.append(st.pop());
                    st.push(m);
                } break;
                default: sb.append(m);
            }
        }

        while (!st.isEmpty())
            sb.append(st.pop());

        System.out.println(sb);
    }
}

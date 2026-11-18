package swea.p4874;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        sc.nextLine();

        for(int tc = 1; tc <= T; tc++) {
            Stack<Integer> st = new Stack<>();
            StringTokenizer tokens = new StringTokenizer(sc.nextLine());

            boolean error = false;
            int answer = 0;

            while(tokens.hasMoreTokens()) {
                String s = tokens.nextToken();

                if(s.equals(".")) {
                    if(st.size() != 1) {
                        error = true;
                    } else {
                        answer = st.pop();
                    }
                    break;
                }

                if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                    if(st.size() < 2) {
                        error = true;
                        break;
                    }

                    int right = st.pop();
                    int left = st.pop();

                    switch(s) {
                        case "+":
                            st.push(left + right);
                            break;
                        case "-":
                            st.push(left - right);
                            break;
                        case "*":
                            st.push(left * right);
                            break;
                        case "/":
                            if (right == 0) {
                                error = true;
                            } else {
                                st.push(left / right);
                            }
                            break;

                    }

                    if (error) {
                        break;
                    }
                } else {
                    st.push(Integer.parseInt(s));
                }
            }

            System.out.println("#" + tc + " " + (error ? "error" : answer));
        }
    }
}

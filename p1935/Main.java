package p1935;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        char[] chars = br.readLine().toCharArray();

        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = Double.parseDouble(br.readLine());
        }

        Stack<Double> st = new Stack<>();
        for (char c : chars) {
            switch (c) {
                case '+': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 + num1);
                } break;
                case '-': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 - num1);
                } break;
                case '*': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 * num1);
                } break;
                case '/': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 / num1);
                } break;
                default:
                    // 'A'의 아스키코드를 빼면 0부터 시작하는 인덱스로 사용 가능
                    st.push(values[c - 'A']);
            }
        }

        // 소수 두번째 자리까지 표기
        System.out.printf("%.2f\n", st.pop());
    }
}

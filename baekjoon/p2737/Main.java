package baekjoon.p2737;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static int solution1(int n) {
        int result = 1;
        int sum = 1;
        int start = 1;
        int end = 1;

        while (start <= n/2) {
            if (sum < n) {
                sum += ++end;
            }
            else if (sum == n) {
                result++;
                sum -= start++;
            }
            else {
                sum -= start++;
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            bw.write(solution1(a) + "\n");
        }

        bw.flush();
        bw.close();
    }
}

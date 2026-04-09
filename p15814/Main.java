package p15814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));

        String[] s = br.readLine().split("");
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            swap(s, a, b);
        }
        for (String str : s) {
            System.out.print(str);
        }
    }

    private static void swap(String[] str, int a, int b) {
        String temp = str[a];
        str[a] = str[b];
        str[b] = temp;
    }
}

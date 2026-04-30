package codetree.hashMap01.theSumOfTheElementsIs0;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arrA = new int[n];
        int[] arrC = new int[n];

        HashMap<Integer, Integer> sumMap = new HashMap<>();

        // A + B
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrA[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int b = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int sumAB = b +     arrA[j];
                sumMap.put(sumAB, sumMap.getOrDefault(sumAB, 0) + 1);
            }
        }

        // C + D
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrC[i] = Integer.parseInt(st.nextToken());
        }

        long answer = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int d = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int sumCD = d + arrC[j];
                answer += sumMap.getOrDefault(-sumCD, 0);
            }
        }

        System.out.println(answer);
    }
}

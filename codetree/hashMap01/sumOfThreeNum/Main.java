package codetree.hashMap01.sumOfThreeNum;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int result = 0;

        int[] arr = new int[n];
        HashMap<Integer, Integer> map = new HashMap<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            arr[i] = num;
            int diff = k - num;

            if (map.containsKey(diff)) {
                result += map.get(diff);
            }

            for (int j = 0; j < i; j++) {
                int sum = num + arr[j];
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        System.out.println(result);
    }
}

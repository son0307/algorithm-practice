package codetree.hashMap01.sumOfTwoNum;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        HashMap<Integer, Integer> map = new HashMap<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            arr[i] = num;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int result = 0;
        for (int num : arr) {
            int otherNum = K - num;
            if (map.containsKey(otherNum)) {
                result += map.get(otherNum);
                if (num == otherNum)
                    result--;
            }
        }

        System.out.println(result / 2);
    }
}

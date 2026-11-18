package swea.p3307;

import java.util.*;

public class Solution2 {
    static int getIndex(ArrayList<Integer> lens, int num) {
        int left = 0;
        int right = lens.size()-1;

        while(left < right) {
            int mid = (left + right) / 2;

            if(lens.get(mid) < num) {
                left = mid+1;
            }
            else
                right = mid;
        }

        return left;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            ArrayList<Integer> lens = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                int num = sc.nextInt();

                if(lens.isEmpty() || lens.get(lens.size() -1) < num) {
                    lens.add(num);
                } else {
                    int idx = getIndex(lens, num);
                    lens.set(idx, num);
                }
            }

            System.out.println("#" + tc + " " + lens.size());
        }
    }
}

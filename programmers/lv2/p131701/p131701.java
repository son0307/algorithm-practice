package programmers.lv2.p131701;

import java.util.HashSet;

public class p131701 {
    public int solution(int[] elements) {
        HashSet<Integer> set = new HashSet<>();

        // 수열을 한번 더 복사해 붙임으로써 모듈러 연산을 사용하지 않고 간단히 계산
        int[] newElements = new int[elements.length * 2];
        for (int i = 0; i < newElements.length; i++) {
            newElements[i] = elements[i % elements.length];
        }

        // 첫 번째 원소부터 선택하는 원소의 개수를 늘려가며 합 계산
        for (int i = 0; i < elements.length; i++) {
            for (int j = 1; j <= elements.length; j++) {
                int sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += newElements[i + k];
                }
                set.add(sum);
            }
        }

        // 계산된 합들은 Set에 저장. Set라 자동적으로 중복 제거가 이루어짐.
        return set.size();
    }

    public static void main(String[] args) {
        int[] elements = new int[] {7,9,1,1,4};
        System.out.println(new p131701().solution(elements));
    }
}

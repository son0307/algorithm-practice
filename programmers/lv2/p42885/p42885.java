package programmers.lv2.p42885;

import java.util.Arrays;

public class p42885 {
    public int solution(int[] people, int limit) {
        int answer = 0;

        Arrays.sort(people);
        int point1 = 0;
        int point2 = people.length - 1;
        while (point1 <= point2) {
            if (people[point1] + people[point2] <= limit) {
                point1++;
            }
            point2--;
            answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] people = {70, 30, 50, 20};
        int limit = 100;
        System.out.println(new p42885().solution(people, limit));
    }
}

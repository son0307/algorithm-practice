package programmers.lv2.p43165;

public class p43165 {
    private int answer = 0;

    private void dfs(int result, int idx, int[] numbers, int target) {
        if (idx == numbers.length) {
            if (result == target)
                answer++;
            return;
        }

        dfs(result + numbers[idx], idx + 1, numbers, target);
        dfs(result - numbers[idx], idx + 1, numbers, target);
    }

    public int solution(int[] numbers, int target) {
        dfs(0, 0, numbers, target);
        return answer;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{4,1,2,1};
        int target = 4;
        System.out.println(new p43165().solution(numbers, target));
    }
}

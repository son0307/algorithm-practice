package programmers.lv2.pccpTest121684;

public class pccpTest121684 {
    int max = 0;

    public void getMaxScore(int index, int score, int[][] ability, boolean[] checked) {
        if (index == ability[0].length) {
            max = Math.max(max, score);
            return;
        }

        for (int i = 0; i < ability.length; i++) {
            if (checked[i]) continue;

            checked[i] = true;
            getMaxScore(index + 1, score + ability[i][index], ability, checked);
            checked[i] = false;
        }
    }

    public int solution(int[][] ability) {
        boolean[] checked = new boolean[ability.length];

        getMaxScore(0, 0, ability, checked);

        return max;
    }

    public static void main(String[] args) {
        int[][] ability = {{20, 30}, {30, 20}, {20, 30}};
        System.out.println(new pccpTest121684().solution(ability));
    }
}

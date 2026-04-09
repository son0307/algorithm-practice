package programmers.lv2;

public class p49993 {
    private boolean inspect(String skill, String skill_tree) {
        int skillIndex = 0;

        for (char c : skill_tree.toCharArray()) {
            int index = skill.indexOf(c);

            if (index == -1) {
                continue;
            }

            if (index == skillIndex)
                skillIndex++;
            else
                return false;
        }

        return true;
    }

    public int solution(String skill, String[] skill_trees) {
        int answer = 0;

        for (String tree : skill_trees) {
            if (inspect(skill, tree))
                answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        String skill = "CBD";
        String[] skill_tress = new String[]{"BACDE", "CBADF", "AECB", "BDA"};
        System.out.println(new p49993().solution(skill, skill_tress));
    }
}

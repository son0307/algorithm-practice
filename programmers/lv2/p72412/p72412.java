package programmers.lv2.p72412;

import java.util.*;
import java.util.function.Consumer;

public class p72412 {
    // 지원자 정보를 바탕으로 가능한 모든 조합 생성
    private void forEachKey(int idx, String[] tokens, String prefix, Consumer<String> consumer) {
        if (idx == tokens.length - 1) {
            consumer.accept(prefix);
            return;
        }

        forEachKey(idx + 1, tokens, prefix + tokens[idx], consumer);
        forEachKey(idx + 1, tokens, prefix + "-", consumer);
    }

    // 지원자 정보를 바탕으로 조합 - 점수 정보 생성
    private Map<String, List<Integer>> buildScoreMap(String[] info) {
        Map<String, List<Integer>> scoreMap = new HashMap<>();

        for (String s : info) {
            String[] tokens = s.split(" ");
            int score = Integer.parseInt(tokens[tokens.length - 1]);
            forEachKey(0, tokens, "", key -> {
                scoreMap.putIfAbsent(key, new ArrayList<>());
                scoreMap.get(key).add(score);
            });
        }

        for (List<Integer> score : scoreMap.values()) {
            Collections.sort(score);
        }

        return scoreMap;
    }

    // 이진 탐색 메서드
    private int binarySearch(int score, List<Integer> scores) {
        int start = 0;
        int end = scores.size() - 1;

        while (start < end) {
            int mid = (start + end) / 2;
            if (scores.get(mid) >= score) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        if (scores.get(start) < score) {
            return scores.size();
        }
        return start;
    }

    // 전달된 쿼리 내용으로 ScoreMap에서 기준에 할당하는 경우 수 찾기
    private int count(Map<String, List<Integer>> scoreMap, String query) {
        String[] tokens = query.split(" (and )?");
        String key = String.join("", Arrays.copyOfRange(tokens, 0, tokens.length - 1));

        if (!scoreMap.containsKey(key)) return 0;

        List<Integer> scores = scoreMap.get(key);
        int score = Integer.parseInt(tokens[tokens.length - 1]);

        return scores.size() - binarySearch(score, scores);
    }

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];

        Map<String, List<Integer>> scoreMap = buildScoreMap(info);

        for (int i = 0; i < query.length; i++) {
            answer[i] = count(scoreMap, query[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        p72412 p = new p72412();
        System.out.println(Arrays.toString(p.solution(new String[] {"java backend junior pizza 150", "python frontend senior chicken 210", "python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80", "python backend senior chicken 50"},
                new String[] {"java and backend and junior and pizza 100", "python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250", "- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150"})));
    }
}

package programmers.lv3.p42579;

import java.util.*;

public class p42579 {
    static class Song implements Comparable<Song> {
        private final int id;
        private final int play;

        public Song (int id, int play) {
            this.id = id;
            this.play = play;
        }

        @Override
        public int compareTo(Song o) {
            if (this.play == o.play) {
                return this.id - o.id;
            }

            return o.play - this.play;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        ArrayList<Integer> answer = new ArrayList<>();

        HashMap<String, ArrayList<Song>> genreMap = new HashMap<>();
        HashMap<String, Integer> totalPlays = new HashMap<>();

        // 장르 별 그룹화 및 총 재생 횟수 계산
        for(int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            int play = plays[i];

            if (!genreMap.containsKey(genre)) {
                genreMap.put(genre, new ArrayList<>());
            }
            genreMap.get(genre).add(new Song(i, play));
            totalPlays.put(genre, totalPlays.getOrDefault(genre, 0) + play);
        }

        // 총 재생 횟수를 기준으로 장르 정렬
        List<String> keys = totalPlays.keySet().stream()
                .sorted((k1, k2) -> totalPlays.get(k2) - totalPlays.get(k1))
                .toList();

        // 장르 별로 상위 2곡 선택
        for (String k : keys) {
            genreMap.get(k).stream()
                    .sorted()
                    .limit(2)
                    .forEach(song -> answer.add(song.id));
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        p42579 p = new p42579();
        String[] genres = {"classic", "pop", "classic", "classic", "pop"};
        int[] plays = {500, 600, 150, 800, 2500};
        System.out.println(Arrays.toString(p.solution(genres, plays)));
    }
}

package programmers.lv2.p81302;

public class p81302 {
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};

    private boolean isDistanced(char[][] room) {
        for(int y=0; y<room.length; y++) {
            for(int x=0; x<room[y].length; x++) {
                if(room[y][x] != 'P') continue;

//                System.out.println("y = " + y + ", x = " + x);

                // P이면 상하좌우 검사
                for (int d=0; d<4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];

//                    System.out.println("ny = " + ny + ", nx = " + nx);

                    // 범위 초과 시 계산 생략
                    if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || room[ny][nx] == 'X') continue;

                    // 검사 시 P가 있으면 false 반환
                    if(room[ny][nx] == 'P') return false;

                    if(isNextTo(room, ny, nx, y, x)) return false;
                }
            }
        }

        return true;
    }

    private boolean isNextTo(char[][] room, int y, int x, int py, int px) {
        for (int d=0; d<4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(ny < 0 || ny >= room.length || nx < 0 || nx >= room[0].length || (py == ny && px == nx)) continue;

            if(room[ny][nx] == 'P') return true;
        }

        return false;
    }

    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        for(int i=0; i<answer.length; i++) {
            // 대기실 세팅
            String[] place = places[i];
            char[][] room = new char[place.length][];
            for(int j=0; j<place.length; j++) {
                room[j] = place[j].toCharArray();
            }

            // 거리두기 검사
            if(isDistanced(room)) {
                answer[i] = 1;
            } else {
                answer[i] = 0;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        p81302 p = new p81302();
        String[][] room = new String[][] {{"PXXXX", "XPXXX", "XXXPP", "XXXXX", "XXXXX"}, {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"}, {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"},
                {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"}, {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}};
        int[] answer = p.solution(room);
        for(int i : answer) {
            System.out.println(i);
        }
    }
}

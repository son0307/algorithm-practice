package codetree.p2024m1.FirstHalf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int BOARD_SIZE = 5;
    static int CENTRAL_SIZE = 3;

    static class Board {
        int[][] b;

        public Board() {
            b = new int[BOARD_SIZE][BOARD_SIZE];
        }

        public Board copy() {
            Board newBoard = new Board();
            for(int i = 0; i < BOARD_SIZE; i++)
                System.arraycopy(this.b[i], 0, newBoard.b[i], 0, BOARD_SIZE);
            return newBoard;
        }

        public Board rotate(int y, int x, int cnt) {
            Board result = copy();

            for(int i = 0; i < cnt; i++) {
                int[][] temp = new int[CENTRAL_SIZE][CENTRAL_SIZE];
                for(int ty = 0; ty < CENTRAL_SIZE; ty++) {
                    for(int tx = 0; tx < CENTRAL_SIZE; tx++) {
                        temp[ty][tx] = result.b[y + ty - 1][x + tx - 1];
                    }
                }

                for(int ty = 0; ty < CENTRAL_SIZE; ty++) {
                    for(int tx = 0; tx < CENTRAL_SIZE; tx++) {
                        result.b[y + tx - 1][x + (CENTRAL_SIZE) - 2 - ty] = temp[ty][tx];
                    }
                }
            }

            return result;
        }

        public boolean inRange(int y, int x) {
            return y >= 0 && y < BOARD_SIZE && x >= 0 && x < BOARD_SIZE;
        }

        public int calcScore() {
            int score = 0;
            boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
            int[] dy = {1,-1,0,0};
            int[] dx = {0,0,1,-1};

            for(int y = 0; y < BOARD_SIZE; y++) {
                for(int x = 0; x < BOARD_SIZE; x++) {
                    if(b[y][x] != 0 && !visited[y][x]) {
                        Queue<int[]> q = new ArrayDeque<>();
                        List<int[]> trace = new ArrayList<>();

                        visited[y][x] = true;
                        q.add(new int[]{y,x});
                        trace.add(new int[]{y,x});

                        int currentNum = b[y][x];

                        while(!q.isEmpty()) {
                            int[] cur = q.poll();
                            int curY = cur[0];
                            int curX = cur[1];

                            for(int i = 0; i < 4; i++) {
                                int newY = curY + dy[i];
                                int newX = curX + dx[i];

                                if(inRange(newY, newX) && !visited[newY][newX] && currentNum == b[newY][newX]) {
                                    q.add(new int[]{newY, newX});
                                    trace.add(new int[]{newY, newX});
                                    visited[newY][newX] = true;
                                }
                            }
                        }

                        if(trace.size() >= 3) {
                            score += trace.size();
                            for(int[] t : trace) {
                                b[t[0]][t[1]] = 0;
                            }
                        }
                    }
                }
            }

            return score;
        }

        public void fill(Queue<Integer> nextArtifacts) {
            for(int x = 0; x < BOARD_SIZE; x++) {
                for(int y = BOARD_SIZE - 1; y >= 0; y--) {
                    if (b[y][x] == 0) {
                        b[y][x] = nextArtifacts.poll();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int K = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Board board = new Board();
        for(int i = 0; i < BOARD_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < BOARD_SIZE; j++) {
                board.b[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<Integer> nextArtifacts = new ArrayDeque<>();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            nextArtifacts.add(Integer.parseInt(st.nextToken()));
        }

        StringBuilder sb = new StringBuilder();

        for(int turn = 0; turn < K; turn++) {
            int maxScore = 0;
            Board maxScoredBoard = null;

            for(int cnt = 1; cnt <=3; cnt++) {
                for(int x = 1; x <= 3; x++) {
                    for(int y = 1; y <= 3; y++) {
                        Board rotatedBoard = board.rotate(y, x, cnt);
                        int currentScore = rotatedBoard.calcScore();

                        if(currentScore > maxScore) {
                            maxScore = currentScore;
                            maxScoredBoard = rotatedBoard;
                        }
                    }
                }
            }

            if (maxScore == 0) break;

            board = maxScoredBoard;
            int turnScore = maxScore;
            while (true) {
                board.fill(nextArtifacts);
                int newScore = board.calcScore();

                if(newScore == 0) break;

                turnScore += newScore;
            }

            sb.append(turnScore).append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}

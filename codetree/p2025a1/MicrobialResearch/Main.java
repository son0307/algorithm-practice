package codetree.p2025a1.MicrobialResearch;

import java.util.*;
import java.io.*;

public class Main {
    static int MAX_N = 15;
    static int MAX_MICRO = 51;

    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    static int n, q;

    static int[][] board = new int[MAX_N][MAX_N];
    static int[][] newBoard = new int[MAX_N][MAX_N];

    static boolean[][] visited = new boolean[MAX_N][MAX_N];

    static int[] microGroupCnt = new int[MAX_MICRO];
    static int[] microSize = new int[MAX_MICRO];

    static Pos[] startPoses;
    static Pos[] endPoses;

    static class Pos {
        int y, x;

        public Pos(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class OrderMicro {
        int id;
        int size;

        public OrderMicro(int id, int size) {
            this.id = id;
            this.size = size;
        }
    }

    static boolean inRange(int y, int x) {
        return 0 <= y && 0 <= x && y < n && x < n;
    }

    static void bfs(int y, int x, int id) {
        Queue<Pos> q = new ArrayDeque<>();
        visited[y][x] = true;
        q.add(new Pos(y, x));

        while(!q.isEmpty()) {
            Pos p = q.poll();
            for(int d = 0; d < 4; d++) {
                int ny = p.y + dy[d];
                int nx = p.x + dx[d];

                if(inRange(ny, nx) && !visited[ny][nx] && board[ny][nx] == id) {
                    visited[ny][nx] = true;
                    q.add(new Pos(ny, nx));
                }
            }
        }
    }

    static void removeMicro(int microId) {
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                if(board[y][x] == microId)
                    board[y][x] = 0;
            }
        }
    }

    static void checkBoard(String log) {
        System.out.println("--------- " + log + " ----------");
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        for(int id = 1; id <= q; id++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            // 1. 미생물 투입
            for(int y = y1; y < y2; y++) {
                for(int x = x1; x < x2; x++) {
                    board[y][x] = id;
                }
            }

//            checkBoard("미생물 투입 후");

            // 순회하며 두 개로 나뉘어진 미생물이 있는지 탐색
            visited = new boolean[n][n];
            microGroupCnt = new int[q + 1];
            for(int y = 0; y < n; y++) {
                for(int x = 0; x < n; x++) {
                    if(board[y][x] == 0) continue;
                    if(visited[y][x]) continue;

                    int targetId = board[y][x];
                    microGroupCnt[targetId]++;
                    bfs(y, x, targetId);
                }
            }

            // 집단 개수가 2개 이상인 미생물은 제거
            for(int targetId = 1; targetId < q; targetId++) {
                if(microGroupCnt[targetId] >= 2) {
                    removeMicro(targetId);
                }
            }

//            checkBoard("2개 이상의 집단 검사 및 제거 후");

            // 2. 새 배양 용기로 이동
            newBoard = new int[n][n];
            microSize = new int[q + 1];
            startPoses = new Pos[q + 1];
            endPoses = new Pos[q + 1];
            for(int i = 1; i <= q; i++) {
                startPoses[i] = new Pos(n, n);
                endPoses[i] = new Pos(0, 0);
            }

            // 각 미생물 별로 개수 카운트 및 이동 영역 지정
            for(int y = 0; y < n; y++) {
                for(int x = 0; x < n; x++) {
                    if(board[y][x] == 0) continue;

                    microSize[board[y][x]]++;
                    // 왼쪽 위 좌표 갱신
                    startPoses[board[y][x]].x = Math.min(startPoses[board[y][x]].x, x);
                    startPoses[board[y][x]].y = Math.min(startPoses[board[y][x]].y, y);
                    // 오른쪽 아래 좌표 갱신
                    endPoses[board[y][x]].x = Math.max(endPoses[board[y][x]].x, x);
                    endPoses[board[y][x]].y = Math.max(endPoses[board[y][x]].y, y);
                }
            }

            // 배치할 순서 정하기
            ArrayList<OrderMicro> relocationOrder = new ArrayList<>();
            for(int target = 1; target <= q; target++) {
                if(microSize[target] != 0)
                    relocationOrder.add(new OrderMicro(target, microSize[target]));
            }

            Collections.sort(relocationOrder, (o1, o2) -> {
                if(o1.size == o2.size) return o1.id - o2.id;
                else return o2.size - o1.size;
            });

//            System.out.println("배치 순서: " + relocationOrder.stream().map(o -> o.id).toList());

            // 정렬된 순서대로 배치
            for(OrderMicro o : relocationOrder) {
                int curMicroId = o.id;

                Pos start = startPoses[curMicroId];
                Pos end = endPoses[curMicroId];

                int height = end.y - start.y + 1;
                int width = end.x - start.x + 1;

//                System.out.println("id: " + o.id + ", start: " + start.y + "," + start.x + ", end: " + end.y + "," + end.x);

                for(int newX = 0; newX <= n - width; newX++) {
                    boolean canPlaceThisX = false;
                    for(int newY = 0; newY <= n - height; newY++) {
                        boolean canPlace = true;
                        for(int dy = 0; dy < height; dy++) {
                            for(int dx = 0; dx < width; dx++) {
                                int originY = start.y + dy;
                                int originX = start.x + dx;

                                // 원본 영역의 동일한 미생물만 검사
                                if(board[originY][originX] != curMicroId)
                                    continue;

                                // 새로 배치할 구역에 다른 미생물이 이미 있으면 놓을 수 없음
                                if(newBoard[newY + dy][newX + dx] != 0) {
                                    canPlace = false;
                                    break;
                                }
                            }
                            // 현재 행에 놓지 못하면 탐색할 필요가 없음
                            if(!canPlace) break;
                        }

                        // 탐색이 모두 완료된 후 놓을 수 있으면 배치
                        if(canPlace) {
                            for(int dy = 0; dy < height; dy++) {
                                for(int dx = 0; dx < width; dx++) {
                                    int originY = start.y + dy;
                                    int originX = start.x + dx;

                                    if(board[originY][originX] == curMicroId)
                                        newBoard[newY + dy][newX + dx] = board[originY][originX];
                                }
                            }
                            // 배치를 완료 했으므로 플래그 올리고 탈출
                            canPlaceThisX = true;
                            break;
                        }
                    }
                    // 이번 행에서 배치가 완료되었으면 더 이상 탐색 필요 x
                    if(canPlaceThisX)
                        break;
                }
            }

            board = newBoard;

//            checkBoard("새로운 용기로 이동 후");

            // 3. 결과 기록
            // 새로운 용기를 순회하며 미생물 발견시 좌우 4방향 확인 -> 다른 생물 발견 시 인접 기록
            boolean[][] closeMicros = new boolean[q+1][q+1];

            for(int y = 0; y < n; y++) {
                for(int x = 0; x < n; x++) {
                    if(board[y][x] == 0) continue;

                    for(int d = 0; d < 4; d++) {
                        int ny = y + dy[d];
                        int nx = x + dx[d];

                        if(inRange(ny, nx) && board[ny][nx] != 0 && board[y][x] != board[ny][nx]) {
                            int id1 = board[y][x];
                            int id2 = board[ny][nx];
                            closeMicros[id1][id2] = true;
                            closeMicros[id2][id1] = true;
                        }
                    }
                }
            }

            // 인접 쌍을 검사하며 점수 계산
            int score = 0;
            for (int id1 = 1; id1 <= q; id1++) {
                for (int id2 = id1 + 1; id2 <= q; id2++) {
                    if (closeMicros[id1][id2]) {
                        score += microSize[id1] * microSize[id2];
                    }
                }
            }

            System.out.println(score);
        }
    }
}

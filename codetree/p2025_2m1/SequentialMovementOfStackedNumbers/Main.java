package codetree.p2025_2m1.SequentialMovementOfStackedNumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Pos {
    int r, c;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static final int MAX_N = 20;

    static int n, m;
    static ArrayList<Integer>[][] grid = new ArrayList[MAX_N][MAX_N];

    static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    static boolean inRange(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    // 주어진 번호의 위치 찾기
    static Pos getPos(int num) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < grid[i][j].size(); k++) {
                    if(grid[i][j].get(k) == num)
                        return new Pos(i, j);
                }
            }
        }

        return new Pos(-1, -1);
    }

    // 이동할 위치 찾기
    static Pos getNextPos(Pos pos) {
        int r = pos.r, c = pos.c;

        // 인접한 8개의 칸 중 가장 큰 값을 가지고 있는 칸 찾기
        int max = -1;
        Pos maxPos = new Pos(-1, -1);
        for(int i = 0; i < 8; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            if(inRange(nr, nc)) {
                for(int j = 0; j < grid[nr][nc].size(); j++) {
                    if(grid[nr][nc].get(j) > max) {
                        max = grid[nr][nc].get(j);
                        maxPos = new Pos(nr, nc);
                    }
                }
            }
        }

        return maxPos;
    }

    // 다음에 이동할 위치로 이동
    static void move(Pos currentPos, Pos nextPos, int num) {
        int cr = currentPos.r, cc = currentPos.c;
        int nr = nextPos.r, nc = nextPos.c;

        // 값 복사
        int movedCnt = 0;
        boolean flag = false;
        for(int i = 0; i < grid[cr][cc].size(); i++) {
            if(num == grid[cr][cc].get(i))
                flag = true;

            if(flag) {
                movedCnt++;
                grid[nr][nc].add(grid[cr][cc].get(i));
            }
        }

        // 복사한 수 만큼 제거
        for(int i = 0; i < movedCnt; i++) {
            grid[cr][cc].remove(grid[cr][cc].size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = new ArrayList<>();
                grid[i][j].add(sc.nextInt());
            }
        }

        for(int r = 0; r < m; r++) {
            int move = sc.nextInt();
            Pos pos = getPos(move);
            Pos nextPos = getNextPos(pos);
            if(nextPos.c != -1) move(pos, nextPos, move);
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j].isEmpty())
                    System.out.print("None");
                else {
                    for (int k = grid[i][j].size() - 1; k >= 0; k--)
                        System.out.print(grid[i][j].get(k) + " ");
                }
                System.out.println();
            }
        }
    }
}

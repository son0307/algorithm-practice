package codetree.p2025_2a1.CollisionExperimentWithWall;

import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    static int n, m;

    static int charToIndex(char dirChar) {
        switch (dirChar) {
            case 'U':
                return 0;
            case 'D':
                return 2;
            case 'R':
                return 3;
            case 'L':
                return 1;
            default:
                return -1;
        }
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    static class Marble {
        int r, c;
        int dir;

        public Marble(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }

        void move() {
            int nr = r + dr[dir], nc = c + dc[dir];

            if (inRange(nr, nc)) {
                r = nr;
                c = nc;
            } else {
                dir = (dir + 2) % 4;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int i = 0; i < T; i++) {
            List<Marble> marbles = new ArrayList<>();
            n = sc.nextInt();
            m = sc.nextInt();
            for(int j = 0; j < m; j++) {
                int r = sc.nextInt() - 1;
                int c = sc.nextInt() - 1;
                int dir = charToIndex(sc.next().charAt(0));
                marbles.add(new Marble(r, c, dir));
            }

            for(int j = 0; j < 2*n; j++) {
                List<Marble> nextMarbles = new ArrayList<>();
                int[][] marblesMap = new int[n][n];

                for(Marble m : marbles) {
                    m.move();
                    marblesMap[m.r][m.c]++;
                }

                for(Marble m : marbles) {
                    if(marblesMap[m.r][m.c] == 1)
                        nextMarbles.add(m);
                }

                marbles = nextMarbles;
            }

            System.out.println(marbles.size());
        }
    }
}

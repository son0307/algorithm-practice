package programmers.lv2;

    import java.util.ArrayList;
    import java.util.Arrays;

public class p87377 {
    private static class Point {
        public final long x;
        public final long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private Point getMeet(long a, long b, long e, long c, long d, long f) {
        long denominator = (a * d - b * c);

        // 분모가 0이면 교차점 없음(평행 또는 일치)
        if (denominator == 0) {
            return null;
        }

        double x = (double) (b * f - e * d) / denominator;
        double y = (double) (e * c - a * f) / denominator;

        if (x % 1 == 0 && y % 1 == 0)
            return new Point((long) x, (long) y);
        else
            return null;
    }



    private String[] drawBoard(char[][] board, ArrayList<Point> points, long minX, long maxY) {
        String[] boardString = new String[board.length];

        for(Point p : points) {
            long x = (p.x - minX);
            long y = -(p.y - maxY);

            board[(int) y][(int) x] = '*';
        }

        for(int i=0; i<board.length; i++) {
            boardString[i] = String.valueOf(board[i]);
        }

        return boardString;
    }

    public String[] solution(int[][] line) {
        ArrayList<Point> points = new ArrayList<>();

        for(int i=0; i<line.length; i++) {
            for (int j=i+1; j<line.length; j++) {
                Point meet = getMeet(line[i][0], line[i][1], line[i][2], line[j][0], line[j][1], line[j][2]);

                if(meet == null) continue;
                if(points.contains(meet)) continue;
                points.add(meet);
            }
        }

        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;

        for(Point p : points) {
            minX = Math.min(p.x, minX);
            maxX = Math.max(p.x, maxX);
            minY = Math.min(p.y, minY);
            maxY = Math.max(p.y, maxY);
        }

        char[][] board = new char[(int) (maxY-minY+1)][(int) (maxX-minX+1)];
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }

        return drawBoard(board, points, minX, maxY);
    }

    public static void main(String[] args) {
        p87377 p = new p87377();
        // int[][] prob = {{2, -1, 4}, {-2, -1, 4}, {0, -1, 1}, {5, -8, -12}, {5, 8, 12}};
        // int[][] prob = {{0, 1, -1}, {1, 0, -1}, {1, 0, 1}};
        // int[][] prob = {{1, -1, 0}, {2, -1, 0}};
        int[][] prob = {{1, -1, 0}, {2, -1, 0}, {4,-1,0}};

        String[] s = p.solution(prob);
        System.out.println(Arrays.toString(s));
    }
}



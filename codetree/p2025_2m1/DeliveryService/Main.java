package codetree.p2025_2m1.DeliveryService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int MAX_N = 50;

    static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
    static int n, m;

    static class Box {
        int k;
        int w, h;
        int r, c;

        public Box(int k, int w, int h, int r, int c) {
            this.k = k;
            this.w = w;
            this.h = h;
            this.r = r;
            this.c = c;
        }

        public boolean drop() {
            int minDist = MAX_N;

            int bottom = r + h - 1;

            // 택배가 차지하는 각 열 확인
            for(int col = c; col < c + w; col++) {
                int dist = 0;

                for(int row = bottom + 1; row <= n; row++) {
                    if (grid[row][col] != 0)
                        break;

                    dist++;
                }

                minDist = Math.min(minDist, dist);
            }

            // 못 내려가면 종료
            if(minDist == 0) {
                for(int row = r; row < r + h; row++) {
                    for(int col = c; col < c + w; col++) {
                        grid[row][col] = k;
                    }
                }

                return false;
            }

            // 기존 위치 정리
            for(int row = r; row < r + h; row++) {
                for(int col = c; col < c + w; col++) {
                    grid[row][col] = 0;
                }
            }

            // 현재 위치 갱신
            r += minDist;

            // 새로운 위치에 배치
            for(int row = r; row < r + h; row++) {
                for(int col = c; col < c + w; col++) {
                    grid[row][col] = k;
                }
            }

            return true;
        }

        // 좌/우에서 빼낼 수 있는지 검사(-1: 좌, 1: 우)
        public boolean canPickUp(int direction) {
            switch(direction) {
                case -1:
                    for(int row = r; row < r + h; row++) {
                        for(int col = c - 1; col > 0; col--) {
                            if(grid[row][col] != 0)
                                return false;
                        }
                    }
                    return true;
                case 1:
                    for(int row = r; row < r + h; row++) {
                        for(int col = c + w; col <= n; col++) {
                            if(grid[row][col] != 0)
                                return false;
                        }
                    }
                    return true;
            }

            return false;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        ArrayList<Box> boxes = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int r = 1;
            int c = Integer.parseInt(st.nextToken());

            Box box = new Box(k,w,h,r,c);
            box.drop();
            boxes.add(box);
        }

//        System.out.println("----------Initial Grid------------");
//        for(int row = 1; row <= n; row++) {
//            for(int col = 1; col <= n; col++) {
//                System.out.print(grid[row][col] + " ");
//            }
//            System.out.println();
//        }

        int direction = -1;
        while(!boxes.isEmpty()) {
            // 빼낼 상자 선택
            Box targetBox = null;
            for(Box box : boxes) {
                if(box.canPickUp(direction)) {
                    if(targetBox == null || box.k < targetBox.k)
                        targetBox = box;
                }
            }

            // 상자 빼내기
            boxes.remove(targetBox);
            for(int row = targetBox.r; row < targetBox.r + targetBox.h; row++) {
                for(int col = targetBox.c; col < targetBox.c + targetBox.w; col++) {
                    grid[row][col] = 0;
                }
            }

            // 빼낸 후 중력 적용
            boolean isMoved;
            do {
                isMoved = false;
                for(Box box : boxes) {
                    if(!isMoved && box.drop())
                        isMoved = true;
                }
            } while(isMoved);

            direction *= -1;
            System.out.println(targetBox.k);
//
//            System.out.println("----------After Box Remove------------");
//            for(int row = 1; row <= n; row++) {
//                for(int col = 1; col <= n; col++) {
//                    System.out.print(grid[row][col] + " ");
//                }
//                System.out.println();
//            }
        }
    }
}

import java.util.*;
import java.io.*;

class Line implements Comparable<Line>{
    int start;
    int end;

    public Line(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int compareTo(Line o) {
        return start - o.start;
    }
}

public class Main {
    static int max = Integer.MIN_VALUE;
    static ArrayList<Line> lines = new ArrayList<>();
    static int n;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            lines.add(new Line(start, end));
        }
        
        Collections.sort(lines);

        dfs(0, 0, -1);
        
        System.out.println(max);
    }

    public static void dfs(int idx, int count, int lastEnd) {
        if (idx == n) {
            max = Math.max(max, count);
            return;
        }

        Line current = lines.get(idx);

        // 현재 선분과 마지막 선분이 겹치지 않는 경우
        if (current.start > lastEnd) {
            dfs(idx + 1, count + 1, current.end);
        }
        
        // 현재 선분을 선택하지 않는 경우
        dfs(idx + 1, count, lastEnd);
    }
}
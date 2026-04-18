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

        ArrayList<Line> selectedLines = new ArrayList<>();
        dfs(0, selectedLines);
        System.out.println(max);
    }

    public static void dfs(int idx, ArrayList<Line> selected) {
        if (idx == n) {
            max = Math.max(max, selected.size());

            return;
        }

        Line current = lines.get(idx);
        boolean isAvailable = true;
        for (Line line : selected) {
            if (line.end >= current.start) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            selected.add(current);
            dfs(idx + 1, selected);
            selected.remove(selected.size() - 1);
        }

        dfs(idx + 1, selected);
    }
}
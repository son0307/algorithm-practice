package programmers.lv1;

public class p340213 {
    int converter(String time) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]);
        int second = Integer.parseInt(times[1]);

        return minute * 60 + second;
    }

    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int lenSecond = converter(video_len);
        int posSecond = converter(pos);
        int opStartSecond = converter(op_start);
        int opEndSecond = converter(op_end);

        if (opStartSecond <= posSecond && posSecond <= opEndSecond ) {
            posSecond = opEndSecond;
        }

        for (String command : commands) {
            switch (command) {
                case "next":
                    posSecond += 10;
                    if (posSecond > lenSecond)
                        posSecond = lenSecond;
                    break;
                case "prev":
                    posSecond -= 10;
                    if (posSecond < 0)
                        posSecond = 0;
                    break;
            }

            if (opStartSecond <= posSecond && posSecond <= opEndSecond ) {
                posSecond = opEndSecond;
            }
        }

        int minute = posSecond / 60;
        int second = posSecond % 60;

        return String.format("%02d:%02d", minute, second);
    }

    public static void main(String[] args) {
        String video_len = "07:22";
        String pos = "04:05";
        String op_start = "00:15";
        String op_end = "04:07";
        String[] commands = {"next"};
        System.out.println(new p340213().solution(video_len, pos, op_start, op_end, commands));
    }
}

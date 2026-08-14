package programmers.lv2.p176962;

import java.util.*;

public class p176962 {
    public static class Subject {
        String name;
        int startTime;
        int duration;

        public Subject(String name, int startTime, int duration) {
            this.name = name;
            this.startTime = startTime;
            this.duration = duration;
        }
    }

    public String[] solution(String[][] plans) {
        ArrayList<String> answer = new ArrayList<>();
        PriorityQueue<Subject> subjects = new PriorityQueue<>((s1, s2) -> s1.startTime - s2.startTime);
        Stack<Subject> stoppedSubjects = new Stack<>();

        for (String[] plan : plans) {
            String name = plan[0];

            String[] time = plan[1].split(":");
            int startTime = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);

            int duration = Integer.parseInt(plan[2]);

            Subject s = new Subject(name, startTime, duration);
            subjects.add(s);
        }

        Subject currentSubject = subjects.poll();
        int currentTime = currentSubject.startTime;

        while(!subjects.isEmpty()) {
            Subject nextSubject = subjects.poll();
            int currentEndTime = currentTime + currentSubject.duration;

            // 다음 과제를 시작하기 전에 이전 과제를 끝낸 경우
            if (currentEndTime <= nextSubject.startTime) {
                answer.add(currentSubject.name);
                currentTime = currentEndTime;

                // 시간이 남았고, 덜한 과제가 남아 있는 경우 처리
                while (!stoppedSubjects.isEmpty() && currentTime < nextSubject.startTime) {
                    Subject stoppedSubject = stoppedSubjects.pop();

                    // 멈춘 과제를 다음 과제 시작 전까지 끝낼 수 있는 경우
                    if (currentTime + stoppedSubject.duration <= nextSubject.startTime) {
                        answer.add(stoppedSubject.name);
                        currentTime += stoppedSubject.duration;
                    }
                    // 멈춘 과제를 끝내지 못하는 경우
                    else {
                        stoppedSubject.duration -= nextSubject.startTime - currentTime;
                        stoppedSubjects.push(stoppedSubject);
                        break;
                    }
                }
            }
            // 다음 과제를 시작하기 전까지 이전 과제를 끝내지 못 한 경우
            else {
                currentSubject.duration -= nextSubject.startTime - currentTime;
                stoppedSubjects.push(currentSubject);
            }

            // 다음 과제 시작
            currentTime = nextSubject.startTime;
            currentSubject = nextSubject;
        }

        // 마지막으로 진행중인 과제 완료 처리
        answer.add(currentSubject.name);

        // 남은 과제들 최근에 멈춘 순서대로 처리ㅍ
        while (!stoppedSubjects.isEmpty()) {
            answer.add(stoppedSubjects.pop().name);
        }

        return answer.toArray(String[]::new);
    }

    public static void main(String[] args) {
        p176962 p = new p176962();

        String[][] plans1 = {{"korean", "11:40", "30"}, {"english", "12:10", "20"}, {"math", "12:30", "40"}};
        String[][] plans2 = {{"science", "12:40", "50"}, {"music", "12:20", "40"}, {"history", "14:00", "30"}, {"computer", "12:30", "100"}};
        String[][] plans3 = {{"aaa", "12:00", "20"}, {"bbb", "12:10", "30"}, {"ccc", "12:40", "10"}};

        System.out.println("plans1: " + Arrays.toString(p.solution(plans1)));
        System.out.println("plans2: " + Arrays.toString(p.solution(plans2)));
        System.out.println("plans3: " + Arrays.toString(p.solution(plans3)));
    }
}

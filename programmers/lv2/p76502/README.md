## 문제 요약

- `()`, `[]` , `{}` 로 이루어진 문자열이 주어졌을 때 이들을 회전시키며 올바른 괄호 문자열이 되는 경우의 수를 구하는 문제
- 올바른 괄호 문자열이란 각 괄호들이 모두 쌍을 완전히 맞추었을 때의 경우이다.
- 첫번째 예제를 풀어보면 나올 수 있는 경우의 수는 다음과 같다.
    - [](){} - 올바른 괄호
    - ](){}[
    - (){}[] - 올바른 괄호
    - ){}[](
    - {}[]() - 올바른 괄호
    - }[](){

  총 3개의 경우가 올바른 괄호를 생성하므로 답은 3이다.


## 핵심 포인트

이 문제는 괄호의 짝을 맞추는 문제에 살짝 변형을 준 것으로, 문제의 결은 크게 다르지 않다. 괄호의 짝을 맞추는 부분에서 종류까지 맞아야 한다는 것과 괄호들을 하나씩 회전시켜야 하기 때문에 가능한 모든 경우를 바탕으로 검사를 수행해야 한다는 것이 추가된 것이다.

우선 괄호로 이루어진 문자열이 주어졌을 때 짝이 맞는지부터 검사하는 함수를 먼저 구축해도록 하자. 가장 마지막에 나온 괄호의 짝을 먼저 맞춰야 정상적인 괄호 문자열이 된다는 것을 생각하면 스택을 활용할 수 있다.

- ‘(’, ‘{’, ‘[’ 문자가 주어진 경우 : 스택에 추가
- ‘)’, ‘}’, ‘]’ 문자가 주어진 경우 : 스택에 가장 최근에 들어간 괄호가 현재 괄호와 같은 종류인지 검사, 스택에 아무것도 없으면 잘못된 괄호 문자열
    - 같은 종류 괄호 : pop()을 통해 스택에서 제거 (올바른 괄호 짝) 후 검사 재개
    - 다른 종류 괄호 : 올바른 괄호 짝이 아님. 잘못된 괄호 문자열
- 각 문자들을 모두 검사한 후 스택에 남아있는 문자열이 없으면 각 괄호들이 짝을 맞춘것이므로 올바른 괄호 문자열이다.

위 조건을 바탕으로 주어진 문자열을 검사해 올바른 괄호 문자열인지 아닌지를 판별하는 함수를 구성하면 된다. 아래는 위 조건을 바탕으로 구성한 코드이다.

```java
public boolean check(int start, String s) {
        Stack<Character> st = new Stack<>();

        for (int j = 0; j < s.length(); j++) {
            int offset = ((start + j) % s.length());
            char c = s.charAt(offset);

            if (c == ']' || c == ')' || c == '}') {
                if (st.empty()) return false;

                if (c == ']' && st.peek() == '[') st.pop();
                else if (c == ')' && st.peek() == '(') st.pop();
                else if (c == '}' && st.peek() == '{') st.pop();
            }
            else
                st.push(c);

        }

        return st.empty();
    }
```

문자열을 순회하며 맨 뒤의 문자를 앞으로 보낸 문자열을 매번 만드는 방법보다 offset을 직접 계산하여 처리하는 방법이 더욱 효율적이기에 해당 방식으로 코드를 구성하였다.

### 전체 코드

```java
import java.util.Stack;

class Solution {
    public boolean check(int start, String s) {
        Stack<Character> st = new Stack<>();

        for (int j = 0; j < s.length(); j++) {
            int offset = ((start + j) % s.length());
            char c = s.charAt(offset);

            if (c == ']' || c == ')' || c == '}') {
                if (st.empty()) return false;

                if (c == ']' && st.peek() == '[') st.pop();
                else if (c == ')' && st.peek() == '(') st.pop();
                else if (c == '}' && st.peek() == '{') st.pop();
            }
            else
                st.push(c);

        }
        
        return st.empty();
    }

    public int solution(String s) {
        int answer = 0;

        for (int i = 0; i < s.length(); i++) {
            if (check(i, s)) answer++;
        }

        return answer;
    }
}
```
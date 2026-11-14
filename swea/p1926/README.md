### 1. 문제 요약
   1부터 N까지 차례로 출력한다. 숫자에 3, 6, 9가 들어 있다면 그 숫자 대신 등장 횟수만큼 -를 붙여 출력한다.
### 2. 핵심 포인트
   숫자를 한 자리씩 확인하며 3, 6, 9의 개수를 세는 것이 핵심이다. 예를 들어 363에는 해당 숫자가 세 개 있으므로 ---를 출력한다.
### 3. 동작 원리
   각 숫자 i를 10으로 나눈 나머지로 한 자리씩 확인한다. 확인한 자리가 3, 6, 9라면 sb369에 -를 추가한다.

모든 자리를 확인한 뒤 sb369가 비어 있으면 원래 숫자를, 비어 있지 않으면 sb369를 출력한다. 현재 코드의 sb는 일반 숫자를 다시 조립하는 데 쓰이며, 이 경우에는 i를 그대로 출력해도 된다.
#### 4. 전체 코드
    import java.util.*;

    public class Solution {
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    
            int n = sc.nextInt();
            for (int i = 1; i <= n; i++) {
                StringBuilder sb369 = new StringBuilder();
                StringBuilder sb = new StringBuilder();
    
                int nums = i;
                while (nums != 0) {
                    int num = nums % 10;
    
                    if (num == 3 || num == 6 || num == 9)
                        sb369.insert(0, "-");
                    else
                        sb.insert(0, num);
    
                    nums /= 10;
                }
    
                if (sb369.isEmpty())
                    System.out.print(sb + " ");
                else
                    System.out.print(sb369 + " ");
            }
        }
    }

### 5. 시간 복잡도
   N은 최대 1,000이므로 각 숫자에서 확인하는 자릿수는 최대 4개다. 따라서 시간 복잡도는 \(O(N)\), 숫자 하나를 처리할 때 사용하는 **추가 공간은 \(O(1)\)** 이다.
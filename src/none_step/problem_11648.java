/*
 * @since 2022-12-22
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package none_step;

import java.io.*;

public class problem_11648 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        
        int len = str.length();
        int answer = 0;
        
        // 길이가 1이 될 때까지 (키파가 슬퍼질때까지)
        while(len > 1) {
            int temp = 1;
            // 나머지 연산을 이용해서도 한자리씩 곱할 수 있다.
            for(int i=0; i<len; i++) {
                temp = temp * Integer.parseInt(str.substring(i, i+1));
            }
            
            str = String.valueOf(temp);
            len = str.length();
            answer++;
        }
        
        // 정답 출력
        System.out.println(answer);
    }
}

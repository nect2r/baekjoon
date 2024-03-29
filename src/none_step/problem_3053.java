/*
 * @since 2023-02-13
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package none_step;

import java.io.*;
import java.util.StringTokenizer;

public class problem_3053 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());

        // 1번 유클리드 기하학에서의 원의 넓이
        // 파이 * r^2
        System.out.println(Math.round(Math.PI * (Math.pow(R, 2)) * 1000000) / 1000000.0);

        // 2번 택시 기하학에서의 원의 넓이
        // 2 * r^2
        System.out.println(2 * (Math.pow(R, 2)));
    }
}

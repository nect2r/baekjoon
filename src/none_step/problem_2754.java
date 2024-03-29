/*
 * @since 2022-11-13
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package none_step;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

public class problem_2754 {
	public static void main(String[] args) throws IOException{
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String strs = bf.readLine();
		double answer = 0.0;

		char a = strs.charAt(0);
		
		if(a == 'A') {
			answer = 4.0;
		}else if(a == 'B'){
			answer = 3.0;
		}else if(a == 'C'){
			answer = 2.0;
		}else if(a == 'D'){
			answer = 1.0;
		}else{
			answer = 0.0;
		}

		if(a != 'F') {
			char b = strs.charAt(1);

			if(b == '+') {
				answer += 0.3;
			}else if(b == '-'){
				answer -= 0.3;
			}
		}

		System.out.println(answer);
	}
}

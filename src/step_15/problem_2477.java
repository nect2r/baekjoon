/*
 * @since 2022-11-17
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package step_15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class problem_2477 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
 
		int K = Integer.parseInt(st.nextToken());
 
		int hMax = 0, wMax = 0; 
		int hMaxIdx = -1, wMaxIdx = -1;
 
		int[] dirs = new int[6]; 
		int[] dist = new int[6];
 
		for (int i = 0; i < 6; i++) {
			st = new StringTokenizer(br.readLine());
			dirs[i] = Integer.parseInt(st.nextToken());
			dist[i] = Integer.parseInt(st.nextToken());
			if (dirs[i] == 1 || dirs[i] == 2) { 
				if (hMax < dist[i]) {
 					hMax = dist[i];
					hMaxIdx = i;
				}
			} else {
				if (wMax < dist[i]) {
					wMax = dist[i];
					wMaxIdx = i;
				}
			}
		}
		int maxSquare = wMax * hMax; 
		int minSquare = dist[(wMaxIdx + 3) % 6] * dist[(hMaxIdx + 3) % 6]; 
 
		System.out.println((maxSquare-minSquare)*K);
    }
}

/*
 * @since 2022-11-13
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package step_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class problem_2738 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] size = br.readLine().split(" ");
		int firstSize = Integer.parseInt(size[0]);
		int SecondSize = Integer.parseInt(size[1]);

		int[][] hang = new int[firstSize][SecondSize];
		int fi = 0;
		for(int i=0; i<firstSize*2; i++) {
			String[] strs = br.readLine().split(" ");
			for(int j=0; j<strs.length; j++) {
				hang[fi][j] = hang[fi][j] + Integer.parseInt(strs[j]);
			}
			fi++;
			if(fi == firstSize){
				fi = 0;
			}
		}

		for(int i=0; i<firstSize; i++) {
			for(int j=0; j<SecondSize; j++) {
				System.out.print(hang[i][j] + " ");
			}
			System.out.println();
		}
	}
}

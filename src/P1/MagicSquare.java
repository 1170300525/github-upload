package P1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.*;

public class MagicSquare {

	public static void main(String[] args) throws IOException {
		int n;
		String[] str = {"src\\P1\\txt\\1.txt", "src\\P1\\txt\\2.txt", "src\\P1\\txt\\3.txt", "src\\P1\\txt\\4.txt", "src\\P1\\txt\\5.txt", "src\\P1\\txt\\6.txt"};
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < 5; i++) {
			System.out.print(i + 1 + ".txt······");
			if(isLegalMagicSquare(str[i]))
				System.out.println("true");
		}
		System.out.print("请输入n:	");
		n = in.nextInt();
		if(generateMagicSquare(n)) {
			System.out.print("6.txt······");
			if(isLegalMagicSquare(str[5]))
				System.out.println("true");
		}
		else
			System.out.println("false n不是奇数");
			
	}
	
	static int read(String str, int[][] a) throws IOException {
		int j = 0, length = 0, row = 0, col = 0;
		String line;
		Scanner in = new Scanner(Paths.get(str),"UTF-8");
		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] ptr = line.split("\t");
			length = ptr.length;
			if (col != length && row != 0) {
				in.close();
				return -2;
			}
			for (j = 0; j < length; j++) {
				try {
					a[row][j] = Integer.valueOf(ptr[j]);
				} catch (Exception e) {
					in.close();
					return -3;
				}
			}
			col = j;
			row++;
		}
		in.close();
		if (row != col)
			return -1;
		return length;
	}
	
	static boolean isLegalMagicSquare(String fileName) throws IOException {
		int length, i, j, sum1 = 0, sum2 = 0;
		int[][] a = new int[10000][10000];
		for (i = 0; i < 100; i++) {
			for (j = 0; j < 100; j++) {
				a[i][j] = 0;
			}
		}
		length = read(fileName, a);
		if (length == -1) {
			System.out.println("false 该矩阵行列数不相等");
			return false;
		}
		else if (length == -2) {
			 System.out.println("false 不是矩阵");
			 return false;
		}
		else if (length == -3) {
			System.out.println("false 矩阵中的某些数字并非正整数或数字之间并非使用\\t分割");
			return false;
		}
		else {
			for (j = 0; j < length; j++)
				sum1 = sum1 + a[0][j];
			for (i = 0; i < length; i++) {
				for (j = 0, sum2 = 0; j < length; j++) 
					sum2 = sum2 + a[i][j];
				if (sum1 != sum2)
					return false;
			}
			for (j = 0; j < length; j++) {
				for (i = 0, sum2 = 0; i < length; i++)
					sum2 = sum2 + a[i][j];
				if (sum1 != sum2)
					return false;
			}
			for (i = 0, sum2 = 0; i < length; i++) 
				sum2 = sum2 + a[i][i];
			if (sum1 != sum2)
				return false;
			for (j = length - 1, sum2 = 0; j >= 0; j--) 
				sum2 = sum2 + a[length - j - 1][j];
			if (sum1 != sum2)
			return false;
		}
		return true;
	}
	
	public static boolean generateMagicSquare(int n) throws FileNotFoundException, UnsupportedEncodingException { 
		 
		  int magic[][] = new int[n][n];   
		  int row = 0, col = n / 2, i, j, square = n * n; 
		  if (n % 2 == 0 || n < 0)
			  return false;
		  for (i = 1; i <= square; i++) {    
			  magic[row][col] = i;    //将第一个数放在第一行正中，将每一个数放在前一个数的右上方
			  if (i % n == 0)     //如果将要放数的格非空，将数放在上一个数的正下方
				  row++;    
			  else {     
				  if (row == 0)      //如果已经放到顶行，下一次从底行放
					  row = n - 1;
				  else     
					  row--; 
				  if (col == (n - 1)) //如果已经放到末列，下一次从起始列放，否则就放在上一个数的右上角
					  col = 0;    
				  else     
					  col++;  
			  }
		  } 
		  for (i = 0; i < n; i++) {    
			  for (j = 0; j < n; j++)     
				  System.out.print(magic[i][j] + "\t");   
			  System.out.println();  
		 }
		  write(magic, n);
		return true; 
	} 
	
	public static void write(int[][] magic, int n) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter out = new PrintWriter("src\\P1\\txt\\6.txt","UTF-8");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j < n - 1)
					out.print(magic[i][j] + "\t");
				else {
					out.println(magic[i][j]);
				}
			}
		}
		out.close();
	}
}
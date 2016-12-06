/*
Write a program to read user input and write it to a txt file
 */

package _001_High_Low_Level_Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class _001_Issue {
	public static void main(String[] args) throws IOException{
		Copy.fileWriter(args);
	}
}

class Copy {
	public static void fileWriter(String[] args) throws IOException{
		String sb = new String();
		for (int i=0 ; i<args.length ; i++){
			sb=sb + args[i];
		}
		FileWriter writer = new FileWriter(new File("Hello.txt"));
		writer.write(sb); 
		writer.flush();
		writer.close();
	}
}

/*
Issues:
1. Copy (high level class) is deeply coupled with low level classes like FileWriter and can only read from console.
2. absence of object orientation, since we are only using static methods and not using any objects
*/
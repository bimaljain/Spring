/*
Modify Copy so that the caller can choose whether the input are written to a txt file or xml file
 */
package _001_High_Low_Level_Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class _002_Issue {
	public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException{
		Copy2.fileWriter(args, Target.xml);
	}
}

class Copy2 {
	public static void fileWriter(String[] args, Target target) throws IOException, TransformerException, ParserConfigurationException{
		String sb = new String();
		for (int i=0 ; i<args.length ; i++){
			sb=sb + args[i];
		}

		if (Target.File == target){
			FileWriter writer = new FileWriter(new File("Hello.txt")); 
			writer.write(sb); 
			writer.flush();
			writer.close();
		} else if (Target.xml == target){
			FileWriter writer = new FileWriter(new File("Hello.xml")); 
			writer.write(sb); 
			writer.flush();
			writer.close();
		}
	}
}

enum Target{
	File, xml
}

/*
Issues:
Enhancement increased complexity of code as compared to original code. 
When additional output targets are added, then this will increase the number of else if statements
User input can change in future.
There can be lot of initialization and clean up code for each different target.

So this code works but the structure is not suitable for future changes and it lacks flexibility.

Original code was dependent on two objects, FileWriter and File objects, but with the introduction of another output target, xml, the Copy class 
has lot many dependencies, plus Target enum. Increase in number of output target will increase the number of dependencies of Copy class.
We need to keep the dependencies of the Copy class stable with any change.
*/


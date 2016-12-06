/*
Lets use DIP to solve the issue:
• High-level modules should not depend on low-level modules. Both should depend on abstractions.
• Abstractions should not depend on details. Details should depend on abstractions.
*/

package _001_High_Low_Level_Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class _003_Solution {

}

interface IReader{
	public String readIt();
}

interface IWriter{
	public void writeIt(String input);
}

class ConsoleReader implements IReader{
	private String[] args;
	ConsoleReader(String[] args){
		this.args = args;
	}
	public String readIt(){
		String sb = new String();
		for (int i=0 ; i<args.length ; i++){
			sb=sb + args[i];
		}
		return sb;
	}
}

class TxtWriter implements IWriter{
	private FileWriter writer;
	public TxtWriter(String fileName) throws IOException {
		writer = new FileWriter(new File(fileName));
	}
	public void writeIt(String input){
		try {            
			writer.write(input); 
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

class XmlWriter implements IWriter{
	private FileWriter writer;
	public XmlWriter(String fileName) throws IOException {
		writer = new FileWriter(new File(fileName));
	}
	public void writeIt(String input){
		try {            
			writer.write(input); 
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

class Copy3 {
	//    private IReader reader = new ConsoleReader(args);  // THIS IS TIGHT COUPLING. WE DONT WANT THIS
	//    private IWriter writer = new XmlWriter("Hello.xml");
	private IReader reader;
	private IWriter writer;

	Copy3(IReader reader, IWriter writer){
		this.reader = reader;
		this.writer = writer;
	}

	public void fileWriter() {
		String input = reader.readIt();
		writer.writeIt(input);

	}
}

class Program{        
	public static void main(String[] args) throws IOException{
		IReader reader = new ConsoleReader(args); // DEPENDENCIES OF COPY CLASS ARE CREATED BY AN EXTERNAL SOURCE
		IWriter writer = new XmlWriter("Hello.xml");
		//IWriter writer = new TxtWriter("Hello.txt");
		Copy3 copy = new Copy3(reader, writer);
		copy.fileWriter();
	}
}

/*
High level module, Copy, is highly readable, and highly flexible. Because now Copy has the ability to work with different readers and writers, 
without making any change to itself at all.
Copy Does not care about lifecycle of low level modules (or dependencies) like reader and writer. Main() method handles this.

All these points address DIP#1. However we are still violating DIP #2. Iwriter & IReader only works on String, So we have brought specific 
detail in the abstraction.  Lets change them to work on Object object instead of String only.
Interfaces are tailored to the client and not to the suppliers. We need below:

interface IReader{
	public Object readIt();
}
 
interface IWriter{
	public void writeIt(Object input);
}
*/

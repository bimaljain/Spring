package _001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class _038_ResourceLoader_InjectResourceLoader {
	public static void main(String[] args) throws IOException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_036_ResourceLoader.xml");
		_038_ResourceLoader_InjectResourceLoader y = (_038_ResourceLoader_InjectResourceLoader)ctx.getBean("injectResourceBean");
		y.readResource();		
	}

	Resource resource;

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void readResource() throws IOException{
		InputStream in = resource.getInputStream();
		 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            System.out.println(line);
        }
        reader.close();		
	}

}

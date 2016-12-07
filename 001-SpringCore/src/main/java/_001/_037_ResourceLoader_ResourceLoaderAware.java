package _001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class _037_ResourceLoader_ResourceLoaderAware implements ResourceLoaderAware{
	public static void main(String[] args) throws IOException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_036_ResourceLoader.xml");
	
		_037_ResourceLoader_ResourceLoaderAware z = (_037_ResourceLoader_ResourceLoaderAware)ctx.getBean("resourceLoaderAwareImpl");
		z.readResource();		
	}

	ResourceLoader resourceLoader;
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader=resourceLoader;	
	}
	
	public void readResource() throws IOException{
		Resource resource = resourceLoader.getResource("classpath:_036_file.txt");
		System.out.println(resource.getFile().getAbsolutePath() + "\n");
		
		Resource resource1 = resourceLoader.getResource("file:_036_file.txt");
		System.out.println(resource1.getFile().getAbsolutePath() + "\n");
		
		Resource resource3 = resourceLoader.getResource("url:http://google.com/readme.txt");
		System.out.println(resource3.getURL().getPath() + "\n");
		
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

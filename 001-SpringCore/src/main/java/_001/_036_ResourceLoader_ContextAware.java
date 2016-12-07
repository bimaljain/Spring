package _001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class _036_ResourceLoader_ContextAware implements ApplicationContextAware{
	
	public static void main(String[] args) throws IOException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_036_ResourceLoader.xml");
		
		_036_ResourceLoader_ContextAware x = (_036_ResourceLoader_ContextAware)ctx.getBean("applicationContextAwareImpl");
		x.readResource();
	}

	ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx=applicationContext;
		
	}	
	public void readResource() throws IOException{
		Resource resource = ctx.getResource("classpath:_036_file.txt");
		System.out.println(resource.getFile().getAbsolutePath() + "\n");
		
		Resource resource1 = ctx.getResource("file:_036_file.txt");
		System.out.println(resource1.getFile().getAbsolutePath() + "\n");
		
		Resource resource3 = ctx.getResource("url:http://google.com/readme.txt");
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

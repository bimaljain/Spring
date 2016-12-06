/*
 * uncomment all @Component before running the program
 */
package _001;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

public class _013_BeanLifeCycle_Annotation {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		A3 a = (A3)ctx.getBean("a3");	
		System.out.println(a.getName());
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}


//@Component
class A3 implements 
	InitializingBean, DisposableBean,
	ApplicationContextAware, BeanFactoryAware, BeanNameAware, 
	MessageSourceAware, ResourceLoaderAware,	
	ApplicationEventPublisherAware
//	ServletContextAware, ServletConfigAware // TO DO:research
	
	{
	
	@Value("BIMAL")
	private String name;
	private B3 b;
	
	private ApplicationContext applicationContext;
	private BeanFactory beanFactory;
	private String beanName;
	private MessageSource messageSource; // more in later projects
	private ResourceLoader resourceLoader; // more in later projects
	private ApplicationEventPublisher publisher; // more in later projects
	
	@Autowired
	public A3(B3 b){
		System.out.println("class-A: inside constructor");
		this.b=b;
	}

	public String getName() {
		return name;
	}	
	public void setName(String name) {
		System.out.println("class-A: set property");
		this.name = name;
	}
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		System.out.println("class-A: setApplicationContext");
		this.applicationContext=ctx;
		System.out.println("applicationContext.getBeanDefinitionCount(): " + applicationContext.getBeanDefinitionCount());
	}
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("class-A: setBeanFactory");
		this.beanFactory=beanFactory;	
		System.out.println("beanFactory.isPrototype(\"beanA\"): " + beanFactory.isPrototype("a3"));
	}
	public void setBeanName(String name) {
		System.out.println("class-A: inject spring bean name");
		this.beanName = name;	
		System.out.println(this.beanName);
	}
	public void setMessageSource(MessageSource arg0) {
		System.out.println("class-A: setMessageSource");
		this.messageSource=arg0;		
	}
	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		System.out.println("class-A: setApplicationEventPublisher");
		this.publisher=arg0;		
	}	
	public void setResourceLoader(ResourceLoader arg0) {
		System.out.println("class-A: setResourceLoader");
		this.resourceLoader=arg0;		
	}
	
	// initialization and destruction
	public void afterPropertiesSet() throws Exception {
		System.out.println("class-A: inside spring provided init (afterPropertiesSet) method");		
	}
	public void destroy() throws Exception {
		System.out.println("class-A: inside spring provided destroy method");		
	}
	
	@PostConstruct
	public void customInit(){
		System.out.println("class-A: customInit");
	}	
	@PreDestroy
	public void customDestroy(){
		System.out.println("class-A: customDestroy");
	}	
}


//@Component
class B3 {
	
	public B3(){
		System.out.println("class-B: inside constructor");
	}

	@PostConstruct
	public void customInit(){
		System.out.println("class-B: customInit");
	}	
	@PreDestroy
	public void customDestroy(){
		System.out.println("class-B: customDestroy");
	}
}


//@Component
class BeanPostProcessorImpl3 implements BeanPostProcessor, Ordered{
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + ": inside postProcessBeforeInitialization");
		return bean;
	}
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + ": inside postProcessAfterInitialization");
		return bean;
	}
	
	//method for ordered interface
	public int getOrder() {
		return 1; //0 means highest priority, next lower priority is 1 and so on..
	} 
}


//@Component
class BeanPostProcessorImpl4 implements BeanPostProcessor, Ordered{
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + ": inside postProcessBeforeInitialization-2");
		return bean;
	}
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + ": inside postProcessAfterInitialization-2");
		return bean;
	}
	
	//method for ordered interface
	public int getOrder() {
		return 0;
	} 
}


//@Component
class FactoryPostProcessorImpl3 implements BeanFactoryPostProcessor {
	private BeanFactory beanFactory; 

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		System.out.println("Factory post processor is called");
		beanFactory = factory;
		System.out.println("beanFactory.containsBean(\"beanA\"): " + beanFactory.containsBean("a"));
	}
}
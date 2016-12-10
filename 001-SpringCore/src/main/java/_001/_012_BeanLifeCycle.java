/*
-----------------------
Spring Bean Life Cycle:
-----------------------
The most important feature of Spring is the bean based approach.  The Spring bean is created, managed and dispensed by the Spring IoC container. 
Each Spring bean has a lifecycle and understanding the spring bean lifecycle enables better coding.
Life of traditional java objects starts on calling new operator which instantiates the object and finalize() method is getting called when the object 
is eligible for garbage collection. Life cycle of Spring beans are different as compared to traditional java objects. 
The life cycle of a Spring bean is very easy to understand. When a bean is instantiated, it may be required to perform some initialization to get 
it into a usable state. Similarly, when the bean is no longer required and is removed from the container, some cleanup may be required. There are also 
other activities  between initialization and destruction of the bean. These  activities  take place behind the scenes. . The following are the stages 
in a bean’s lifecycle. 

1) Spring container looks for the definition of the bean in the spring configuration xml file

2) [Factory Post Processor is called]

3) Spring instantiate the bean by calling no argument default constructor of that class. If there is only parameterized constructor in the class , 
then bean must be defined in spring xml file with constructor injection using which container will instantiate the bean otherwise it will throw bean 
creation exception.

4) Spring injects the values and references if any into bean’s properties.

5) If the bean implements BeanNameAware interface, Spring passes the bean’s ID to the setBeanName () method and executes this method.

6) If the bean implements BeanFactoryAware interface, Spring calls the setBeanFactory () method, passing in the bean factory itself and executes 
this method.

7) If the bean implements ApplicationContextAware interface, Spring will call the setApplicationContext () method, passing in a reference to the 
current application context and executes this method.

8) [At this point, Spring will also inject message source, resource loader and event publisher]

9) If the bean implements the BeanPostProcessor interface, Spring calls their postProcessBeforeInitialization () method

10)If the bean implements the InitializingBean interface, Spring calls afterPropertiesSet() method after all the properties of that bean are set.
Similarly, if the bean is declared with an init-method, then the specified initialization method will be called

11) If the bean implements BeanPostProcessor, Spring will call their postProcessAfterInitialization() method.

12) At this point, the bean is ready to be used by the application and will remain in the application context until the application context is destroyed.

13) If the bean implements the DisposableBean interface, then Spring will call the destroy() method. Likewise, if any bean was declared with a 
destroy-method, then the specified method will be called.

-------------------------------------------------------
InitializingBean and DisposbleBean callback interfaces:
-------------------------------------------------------
InitalizingBean interface is defined under org.springframework.beans.factory package and declares a single method where it can be used to add any 
initialization related code. Any bean implementing InitalizingBean needs to provide an implementation of afterPropertiesSet() method. Signature of 
method is: 

void afterPropertiesSet() throws Exception; 

Similarly DisposableBean interface is defined under the org.springframework.beans.factory and declares a single method which gets executed when bean 
is destroyed and can be used to add any cleanup related code. Any bean implementing DisposableBean needs to provide an implementation of destroy() 
method. Signature of method is : 

void destroy() throws Exception; 

---------------------------------------------------------------
Custom init() and destroy() methods in bean configuration file:
---------------------------------------------------------------
Implementing InitalizingBean and DisposableBean interface is simple to use but create tight coupling with the Spring framework in our bean 
implementations. Alternatively we can init-method and destroy-method attribute values for the bean in the spring bean configuration file. This is the 
recommended approach because of no direct dependency to spring framework and we can create our own methods. 

Both post-init and pre-destroy methods should have no arguments but they can throw Exceptions 

-----------------------------------------------------------
Global init and destroy methods in bean configuration file:
-----------------------------------------------------------
If there are too many beans having initialization and / or destroy methods with the same name, there is no need to  declare init-method and 
destroy-method on every individual bean. Instead, the framework provides the facility to configure the init and destroy methods using default-init-method
and default-destroy-method attributes on the <beans> element. Default-init-method & default-destroy-method will check for the init & destroy methods 
in all the beans defined and if found will call those methods.

-------------------------------------------
@PostConstruct and @PreDestroy annotations:
-------------------------------------------
These annotations are used to call the bean life cycle methods just like init and destroy methods. 
@PostConstruct : is called after the bean has been initialized and before this bean is returned to the requested object. 
@PreDestroy : is called just before the bean is removed from the container 

-------------------------
BeanFactoryPostProcessor:
-------------------------
If you want to run some code before bean factory creates all the singletons defined in the xml, you can use bean post factory post processor.

---------------------
Bean Post Processors:
---------------------
BeanPostProcessor is used to perform some operations before and after creating a bean,this allows us to add some code before and after creating the bean.
BeanPostProcessor is applicable for all the beans, which means its methods will be executed for each bean we define in the xml.
We can use the BeanPostProcessor to execute some logic for all the beans in the application context before and after their initialization
BeanPostProcessor interface has 2 methods postProcessBeforeInitialization() and postProcessAfterInitialization() where former is called after the bean 
is created and before it is initialized And the latter is called after the bean initialization.
You can configure multiple BeanPostProcessor interfaces and you can control the order in which these BeanPostProcessor interfaces execute by setting 
the order property provided the BeanPostProcessor implements the Ordered interface.

----------------------------------
Spring Aware Interfaces for beans:
---------------------------------- 
Several times functionality requires infrastructure information in a bean. To achieve such functionalities ,Spring framework  provides  a range of 
Aware interfaces Each interface requires us to implement a method to inject the dependency in bean. Most commonly used are:

1) ApplicationContextAware 
Bean implementing this interface can get the current application context and this can be used to call any service from the application context 

2) BeanFactoryAware 
Bean implementing this interface can get the current bean factory and this can be used to call any service from the bean factory 

3) BeanNameAware 
Bean implementing this interface can get its name defined in the Spring container. 

4) MessageSourceAware 
Bean implementing this interface can get the access to message source object which is used to achieve internationalization 

5) ServletContextAware 
Bean implementing this interface can get the access to ServeltContext which is used to access servlet context parameters and attributes 

6) ServletConfigAware 
Bean implementing this interface can get the access to ServletConfig object which is used to get the servlet config parameters 

7) ApplicationEventPublisherAware 
Bean implementing this interface can publish the application events and we need to create listener which listen this event. 

8) ResourceLoaderAware 
Bean implementing this interface can load the resources from the classpath or any external file. 

 */
package _001;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
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

public class _012_BeanLifeCycle {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_012_BeanLifeCycle.xml");
		A2 a = (A2)ctx.getBean("beanA");	
		System.out.println(a.getName());
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class A2 implements 
InitializingBean, DisposableBean,
ApplicationContextAware, BeanFactoryAware, BeanNameAware, 
MessageSourceAware, ResourceLoaderAware,	
ApplicationEventPublisherAware
//ServletContextAware, ServletConfigAware // TO DO:research

{

	private String name;
	private B2 b;

	private ApplicationContext applicationContext;
	private BeanFactory beanFactory;
	private String beanName;
	private MessageSource messageSource; // more in later projects
	private ResourceLoader resourceLoader; // more in later projects
	private ApplicationEventPublisher publisher; // more in later projects

	public A2(B2 b){
		System.out.println("class-A: inside constructor");
		this.b=b;
	}

	public void setName(String name) {
		System.out.println("class-A: set property");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBeanName(String name) {
		this.beanName = name;	
		System.out.println("class-A: inject spring bean name \t " + this.beanName);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.print("class-A: inject bean factory");
		this.beanFactory=beanFactory;	
		System.out.println("\t beanFactory.isPrototype(\"beanA\"): " + beanFactory.isPrototype("beanA"));
	}

	public void setResourceLoader(ResourceLoader arg0) {
		System.out.println("class-A: inject resource loader");
		this.resourceLoader=arg0;		
	}

	public void setMessageSource(MessageSource arg0) {
		System.out.println("class-A: inject messsage source");
		this.messageSource=arg0;		
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		System.out.println("class-A: inject event publisher");
		this.publisher=arg0;
		//	publisher.publishEvent(null);
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		System.out.print("class-A: inject context");
		this.applicationContext=ctx;
		System.out.println("\t applicationContext.getBeanDefinitionCount(): " + applicationContext.getBeanDefinitionCount());
	}	

	// initialization and destruction
	public void afterPropertiesSet() throws Exception {
		System.out.println("class-A: inside spring provided init (afterPropertiesSet) method");		
	}
	public void customInit(){
		System.out.println("class-A: customInit");
	}
	public void destroy() throws Exception {
		System.out.println("class-A: inside spring provided destroy method");		
	}	
	public void customDestroy(){
		System.out.println("class-A: customDestroy");
	}	
}

class B2 {
	public B2(){
		System.out.println("class-B: inside constructor");
	}

	public void customInit(){
		System.out.println("class-B: customInit");
	}	
	public void customDestroy(){
		System.out.println("class-B: customDestroy");
	}
}

class BeanPostProcessorImpl implements BeanPostProcessor, Ordered{
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
		return 0; //0 means highest priority, next lower priority is 1 and so on..
	} 
}

class BeanPostProcessorImpl2 implements BeanPostProcessor, Ordered{
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
		return 1;
	} 
}

class FactoryPostProcessorImpl implements BeanFactoryPostProcessor {
	private BeanFactory beanFactory; 

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		System.out.println("Factory post processor is called");
		beanFactory = factory;
		System.out.println("beanFactory.containsBean(\"beanA\"): " + beanFactory.containsBean("beanA"));
		System.out.println("beanFactory.containsBean(\"beanB\"): " + beanFactory.containsBean("beanB"));
	}
}



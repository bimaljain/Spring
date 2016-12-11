/*
------------
Bean Scopes:
------------
When defining a <bean> in Spring, you have the option of declaring a scope for that bean. The Spring Framework supports following five scopes, 
three of which are available only if you use a web-aware ApplicationContext.
 
1.) singleton: This scopes the bean definition to a single instance per Spring IoC container (default scope). If scope is set to singleton, the Spring 
IoC container creates exactly one instance of the object defined by that bean definition. This single instance is stored in a cache of such 
singleton beans, and all subsequent requests and references for that named bean return the cached object.
2.) prototype: This scopes a single bean definition to have any number of object instances. If scope is set to prototype, the Spring IoC container 
creates new bean instance of the object every time a request (using getBean()) or reference (ref element inside <property> & <constructor-arg>) 
for that specific bean is made. As a rule, use the prototype scope for all state-full beans and the singleton scope for stateless beans.
3.) request: This scopes a bean definition to an HTTP request. Only valid in the context of a web-aware Spring ApplicationContext.
4.) session: This scopes a bean definition to an HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.
5.) global-session: This scopes a bean definition to a global HTTP session. Only valid in the context of a web-aware Spring ApplicationContext. 
(used in portlet context)

1. By default, application context initializes all the singleton beans (and not the prototype) when context is initialized. Context hands-over 
the same singleton bean for every request or reference of the named bean.
2. Spring singleton is different from singleton pattern. Singleton pattern means one instance per JVM, however in case of spring, singleton scope 
means one instance per spring container. And we can have muliple spring container inside a JVM.
3. If we have a singleton bean which refers to prototype beans, all the dependencies of the singleton will still be satisfied when the singleton 
is created (or context is initialized) and they will not change with every request & reference.
4. The lifecycle of beans is generally managed by the Spring container. But, when a bean is a prototype, Spring does not handle the destruction of 
the beans. means Spring does not call the destruction callback methods of prototype beans. we must write explicit code to clean up any prototype 
beans. As Per the Spring documentation, “the Spring container’s role in regard to a prototype-scoped bean is a replacement for the Java new operator.
All lifecycle management past that point must be handled by the client.” This means Spring does not call destroy() or customDestroy() for prototype 
beans. However if a prototype is dependent on singleton bean, destroy() or customDestroy() of singleton will still be called.

 */

package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _100_BeanScope {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_100_BeanScope.xml");
		System.out.println((Singleton)ctx.getBean("singleton") == (Singleton)ctx.getBean("singleton"));
		System.out.println((Prototype)ctx.getBean("prototype") == (Prototype)ctx.getBean("prototype"));
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

//@Component
@Scope("singleton")
class Singleton {	
	public Singleton(){
		System.out.println("singleton bean is created");
	}
}

//@Component
@Scope("prototype")
class Prototype {
	public Prototype(){
		System.out.println("prototype bean is created");
	}	
}

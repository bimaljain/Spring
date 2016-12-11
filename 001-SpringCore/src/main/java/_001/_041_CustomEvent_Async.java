/*
Publishing Events asynchronously:
The default listener that Spring implements is synchronous. The publish method will wait until the onApplicationEvent methods of all the listeners 
have completed successfully. If there was any transactional context, then all our listener methods would have executed within the same. It may often 
be necessary that we need an asynchronous kind of flow. The Container publishes the event and simply moves on. It does not wait for the listener 
methods to complete execution. This can be achieved by the SimpleApplicationEventMulticaster class.

Check xml for more details...
 */
package _001;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _041_CustomEvent_Async {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_041_CustomEvent_Async.xml");
		String userId = "admin";
		String password = "admin";
		LoginManager loginManager = (LoginManager) context.getBean("loginManager");
		User user = loginManager.login(userId, password);

		LoginHistoryDAO loginHistoryDao = (LoginHistoryDAO) context.getBean("loginHistoryDao");
		List<LoginHistory> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory loginHistory : loginHistoryList) {
			System.out.println(Thread.currentThread().getName()+ ": " + loginHistory);
		}
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

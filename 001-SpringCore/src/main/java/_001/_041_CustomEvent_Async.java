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

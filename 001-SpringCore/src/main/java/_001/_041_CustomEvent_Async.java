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
		System.out.println("Login userId " + userId);
		User user = loginManager.login(userId, password);

		System.out.println("Log-in again");
		loginManager.login(userId, password);

		System.out.println("Login History");
		LoginHistoryDAO loginHistoryDao = (LoginHistoryDAO) context.getBean("loginHistoryDao");
		List<LoginHistory> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory loginHistory : loginHistoryList) {
			System.out.println(loginHistory);
		}
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

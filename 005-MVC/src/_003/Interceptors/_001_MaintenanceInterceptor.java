package _003.Interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class _001_MaintenanceInterceptor extends HandlerInterceptorAdapter{

	private int maintenanceStartTime;
	private int maintenanceEndTime;
	public void setMaintenanceStartTime(int maintenanceStartTime) {
		this.maintenanceStartTime = maintenanceStartTime;
	}
	public void setMaintenanceEndTime(int maintenanceEndTime) {
		this.maintenanceEndTime = maintenanceEndTime;
	}

	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(cal.HOUR_OF_DAY);
		if (hour >= maintenanceStartTime && hour <= maintenanceEndTime) {
			//maintenance time, send to maintenance page
			response.getWriter().write("Site Maintenance in progress. Please come later.");;
			return false;
		} else {
			return true;
		}
	}
}

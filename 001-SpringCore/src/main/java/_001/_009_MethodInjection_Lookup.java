/*
Lookup method injection will help you override a method on the bean which would return the lookup result of another bean. 
For eg, consider the abstract DAOFactory having a method AppDAO getDAO() to return one of the concrete implementation from 
DAOs(FileAppDAO (for File), DBAppDAO (for DB) etc...) based on the actual DAOFactory implementation.

If you want to support two different family of products, say DB and File, then the usual practice is to have two separate implementation 
for DAOFactory - (1) for DB (DBDAOFactory), (2) for File (FileDAOFactory), where each implementation will return the respective AppDAO 
implementation when getDAO() is invoked. Let's see how we can do this in Spring using Lookup method Injection.
 */
package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _009_MethodInjection_Lookup {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_009_MethodInjection_Lookup.xml");

		IDAOFactory daoFactory1 = (IDAOFactory)ctx.getBean("dbDAOFactory"); 
		IDAOFactory daoFactory2 = (IDAOFactory)ctx.getBean("fileDAOFactory");
		System.out.println(daoFactory1.getDAO().save("bimal"));
		System.out.println(daoFactory2.getDAO().save("bimaljain"));
	}
}

abstract class IDAOFactory {
    public abstract IAppDAO getDAO();
}

abstract class IAppDAO {	 
    public abstract String save (String data);
}

class FileAppDAO extends IAppDAO {	 
	public String save(String data) {
        return data + " saved to File";
    }
}

class DBAppDAO extends IAppDAO {	 
    public String save(String data) {
        return data + " updated to DB";
    }
}

package _001;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional()
	public void createUser(User user){
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Transactional
	public List<User> getUsers(){
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM USER_DETAILS ORDER BY ID DESC");
		query.addEntity(User.class); // this is important otherwise the result is in object form, and creates issue later while casting to User
		List<User> user = query.list();
		return user;
	}	

	@Transactional
	public List<User> getUserById(int id){
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM USER_DETAILS WHERE ID=?");
		query.addEntity(User.class);
		query.setInteger(0, 1);
		List<User> user = query.list();
		return user;
	}
	
	@Transactional
	public void updateUser(User user){
		Session session = sessionFactory.getCurrentSession();
		User exUser = (User) session.get(User.class, user.getId());
		exUser.setPassword(user.getPassword());
		exUser.setUserid(user.getUserid());
		session.update(exUser);
	}
	
	@Transactional
	public void deleteUser(int id){
		Session session = sessionFactory.getCurrentSession();
		User exUser = (User) session.get(User.class, id);
		session.delete(exUser);
	}
}

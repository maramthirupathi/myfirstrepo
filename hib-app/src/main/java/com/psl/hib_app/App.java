package com.psl.hib_app;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.psl.model.Phone;
import com.psl.model.Sim;
import com.psl.model.User;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties());
		SessionFactory sessionFactory = config.buildSessionFactory(builder.build());
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		// insertData(session, transaction);
		queryData(session);
		System.out.println("Done");

	}

	private static void queryData(Session session) {
		Criteria criteria = session.createCriteria(User.class, "user");
		criteria.createAlias("user.phones", "ph").add(Restrictions.eq("ph.type", "Home"));
		criteria.createAlias("ph.sims", "sim").add(Restrictions.eq("sim.type", "micro"));
		List<Sim> simList = (List<Sim>) criteria.list();
		System.out.println("*******\t" + simList);
	}

	private static void insertData(Session session, Transaction transaction) {
		Sim sim1 = new Sim();
		sim1.setType("micro");

		Sim sim2 = new Sim();
		sim2.setType("regular");

		Sim sim3 = new Sim();
		sim3.setType("reqular");

		Sim sim4 = new Sim();
		sim4.setType("micro");

		Phone phone1 = new Phone();
		phone1.setType("Office");
		phone1.setNumber(020334455L);
		phone1.getSims().add(sim1);
		phone1.getSims().add(sim2);

		Phone phone2 = new Phone();
		phone2.setType("Home");
		phone2.setNumber(9172987942L);
		phone2.getSims().add(sim3);
		phone2.getSims().add(sim4);

		User user = new User();
		user.setFirstname("Maram");
		user.setLastname("Thirupathi");

		user.getPhones().add(phone1);
		user.getPhones().add(phone2);

		session.save(user);

		transaction.commit();
	}
}

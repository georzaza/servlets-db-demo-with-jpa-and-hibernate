package util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import models.Product;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				String username = "temp";
				String password = "admin_pass";
				String connect = "jdbc:mysql://localhost:3306/demo_jpa_db";
				connect += "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EET";

				Configuration configuration = new Configuration();
				Properties settings = new Properties();
				
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, connect);
				settings.put(Environment.USER, username);
				settings.put(Environment.PASS, password);
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL57Dialect");
				settings.put(Environment.SHOW_SQL, "true");
//				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Product.class);
				
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				System.out.println("Catch error block inside HibernateUtil---------------");
				System.out.println("Catch error block inside HibernateUtil---------------");
				System.out.println("Catch error block inside HibernateUtil---------------");
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}

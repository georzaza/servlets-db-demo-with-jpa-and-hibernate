package dao;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import models.Product;
import util.HibernateUtil;


public class ProductDao {
	
	public void saveProduct(Product product) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			transaction = session.beginTransaction();
			session.save(product);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		finally	{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts() {

		Transaction transaction = null;
		List<Product> listOfProducts = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Product");
			listOfProducts = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		finally	{
			session.close();
		}
		return listOfProducts;
	}
}

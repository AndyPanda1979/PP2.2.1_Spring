package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
      if (user.getCar() != null)  {
         user.getCar().setUser(user);
         sessionFactory.getCurrentSession().save(user.getCar());
      }

   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List <User> findUsersByCar(String carModel, int carSeries) {
      System.out.print("Trying to find user(s) with car model: " + carModel + " and car series: " + carSeries + " -> ");
      TypedQuery <User> query = sessionFactory.getCurrentSession().createQuery("FROM User as u where u.car.model = '" + carModel + "' and u.car.series = " + carSeries);
      return query.getResultList();
   }
}

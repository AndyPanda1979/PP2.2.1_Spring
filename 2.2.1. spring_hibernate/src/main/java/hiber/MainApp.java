package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);


      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      for (int i = 1; i < 5; i++) {
         User newUser = new User("UserWithCar" + i, "LastName" + i, "someMail" + i + "@mail.ru");
         newUser.setCar(new Car("carModel#" + i , i));
         userService.add(newUser);
      }

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("==== Car information below ====");
         if (user.getCar() == null) {
            System.out.println("This user don't have car");
         } else {
            System.out.println("Car -> model : " + user.getCar().getModel() + ", series : " + user.getCar().getSeries() + ";");
         }
         System.out.println();
      }
      System.out.println("Выборка 1");

      // exist value carModel <String> "CarModel#1", carSeries <Integer> = 1
      List <User> carOwners = userService.findUsersByCar("CarModel#1", 1);
      if (carOwners.size() == 0) {
         System.out.println(" Fault there are no cars with such parameters");
      } else {
         System.out.println(" Done with " + carOwners.size() + " results below:");
         for (User carOwner: carOwners) {
            System.out.println(carOwner.toString() + " ... " + carOwner.getCar().toString());
         }
         System.out.println();
      }

      System.out.println("Выборка 2");

      // non-exist value carModel <String> "NoModel", carSeries <Integer> = 0
      List <User> carOwners2 = userService.findUsersByCar("NoModel", 0);
      if (carOwners2.size() == 0) {
         System.out.println(" Failure, there are no cars with such parameters");
      } else {
         System.out.println(" Done with " + carOwners.size() + " results below:");
         for (User carOwner: carOwners2) {
            System.out.println(carOwner.toString() + " ... " + carOwner.getCar().toString());
         }
         System.out.println();
      }


      context.close();
   }
}

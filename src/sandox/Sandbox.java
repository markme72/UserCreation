package sandox;


import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class Sandbox {
	
	private Scanner input = new Scanner(System.in);
	private String userInput = null;
	
	public Sandbox() {
	}
	
	public static void main(String[] args) {
		Sandbox aSandbox = new Sandbox();
		aSandbox.menu();
		

	}

	private void menu() {
			System.out.println("Choose an option:\n"
					+ "\t1:Create New User\n"
					+ "\t2:Update/Modify a User\n"
					+ "\tX:Exit program");
		
		
			this.userInput = input.next();

				switch (this.userInput){
					case "1":
						addNewUsers();
						break;
					case "2":
						modifyUser();
						break;
					case "X":
						System.exit(0);
				} 
	}
	
	private void addNewUsers() {
		Session session = HibernateUtilSingleton.getSessionFactory().getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		UserBean aUser = new UserBean();
		
		System.out.println("Create username: ");
		aUser.setUname(this.input.next());
		
		System.out.println("Create password for " + aUser.getUname() + ": ");
		aUser.setPword(this.input.next());
		
		session.save(aUser);
		transaction.commit();
		
		System.out.println("aUser generated ID is: " + aUser.getId());
	}
	
	private void modifyUser() {
		Session session = HibernateUtilSingleton.getSessionFactory().getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
			
		System.out.println("Which user do you want to modify?");
		String uname = input.next();
		
		
		System.out.println("What do you want to modify?\n"
				+ "1:Username\n"
				+ "2:Password\n"
				+ "X:Exit program");
		userInput = input.next();
		
		Query singleUserQuery = session.createQuery("select u from UserBean as u where u.uname='" + uname + "'");
		UserBean aUser = (UserBean)singleUserQuery.uniqueResult();

		switch (this.userInput) {
		case "1":
			System.out.println("What's the new username?");
			aUser.setUname(input.next());
			break;
		case "2":
			System.out.println("What's the new password?");
			aUser.setPword(input.next());
			break;
		case "X":
			System.exit(0);
		}	
		session.merge(aUser);
		
		transaction.commit();
		return;
	}
}

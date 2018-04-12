package com.facebook;

//import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.facebook.controller.UserController;
import com.facebook.controller.ValidationController;
public class App 
{
	private static ValidationController validationController;
	private static ApplicationContext context;
    public static void main( String[] args )
    {
    	//BasicConfigurator.configure();
    	context = new AnnotationConfigApplicationContext("com.facebook.*");
    	validationController = context.getBean(ValidationController.class);
    	try {
	    	start1();
    	} finally {
    	}
    }
    public static void start1() {
    	System.out.println("Enter your option");
    	System.out.println("Press 1. TO Login");
    	System.out.println("Press 2. To register your Address");
    	System.out.println("Press any other key to Close App...");
    	int input1 = validationController.validInt();
    	execute1(input1);
  
    }
    private static void execute1(int input1) {
		switch (input1) {
		case 1:
			context.getBean(UserController.class).login();
			System.out.println("_______________________________");
			start1();
			break;
		case 2:
			context.getBean(UserController.class).register();
			System.out.println("_______________________________");
			start1();
			break;
		
		default:
			System.err.println("App closed");
			System.exit(0);
			break;
		}
		
    }
}
package mycompro.mycomprobackend.model;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import mycompro.mycomprobackend.dao.UserDetailsDAO;

public class test {
	public static void main(String ar[]){
	AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
	System.out.print("sdkgsdj");
	context.scan("mycompro.mycomprobackend");
	System.out.print("sdkgsdj");
	context.refresh();
	context.close();
	}
}

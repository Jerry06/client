//package com.lazicoder.office;
//
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.sql.SQLException;
//import java.util.Locale;
//
//public class SpringDemo {
//	public static void main(String[] args) throws SQLException {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(AppConf.class);
//		ctx.refresh();
//		MessageSource resources = ctx.getBean(MessageSource.class);
//	   	String admin = resources.getMessage("user.admin", null, "Default", new Locale("de"));
//	   	System.out.println(admin);
//	   	String superadmin = resources.getMessage("user.superadmin", null, "Default", new Locale("de"));
//	   	System.out.println(superadmin);
//    	String name = resources.getMessage("errormsg.name", null, "Default", new Locale("de"));
//	   	System.out.println(name);
//    	String pwd = resources.getMessage("errormsg.pwd", null, "Default", new Locale("de"));
//	   	System.out.println(pwd);
//		ctx.registerShutdownHook();
//	}
//}
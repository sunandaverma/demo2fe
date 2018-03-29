package mycompro.mycomprobackend.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import mycompro.mycomprobackend.model.*;

@Configuration
@ComponentScan("mycompro.mycomprobackend")
@EnableTransactionManagement
public class DBConfig {
	
	@Bean(name="dataSource")
	public DataSource getDataSource(){
		DriverManagerDataSource datasource=new DriverManagerDataSource();
		datasource.setDriverClassName("oracle.jdbc.OracleDriver");
		datasource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		datasource.setUsername("project2");
		datasource.setPassword("root");
		return datasource;
	}
	
	@Bean(name="sessionfact")
	public SessionFactory getSessionFactory(){
		Properties prop = new Properties();
//		prop.put("hibernate.hbm2ddl.auto","update");
		prop.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
		prop.put("hibernate.show_sql","true");
		LocalSessionFactoryBuilder sessionFactBuilder = new LocalSessionFactoryBuilder(getDataSource());
		sessionFactBuilder.addProperties(prop);
		sessionFactBuilder.addAnnotatedClasses(new Class[]{UserDetails.class,Job.class,BlogPost.class,BlogComment.class,ProfilePicture.class,Friends.class,ApplyForJob.class});
		return sessionFactBuilder.buildSessionFactory();
	}

	@Bean(name="txManager")
	public HibernateTransactionManager getTransactionManager(){
		HibernateTransactionManager txm = new HibernateTransactionManager(getSessionFactory());
		return txm;	
	}
	
	

}
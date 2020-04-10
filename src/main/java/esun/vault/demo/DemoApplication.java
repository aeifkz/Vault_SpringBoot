package esun.vault.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	@Autowired
	DataSource dataSource;	

	@PostConstruct
	private void postConstruct() throws Exception {
		
		logger.info("run me??");
		
		for(int i=0; i<20; i++) {

		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {
				
			ResultSet resultSet = statement.executeQuery("SELECT col1 from tempdb.dbo.test");
			resultSet.next();
			
			logger.info(i +  ": Connection works with User: " + resultSet.getString(1));
			
			resultSet.close();
			
			//休息五秒，一共停20次
			//大約12次左右就會失敗了
			//還有6次...
			Thread.sleep(5000);
		}
		
		}
	}

}

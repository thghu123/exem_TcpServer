package com.tcpServer.tcpServer;

import TcpServerListener.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class TcpServerApplication {
	public static void main(String[] args) {


		SpringApplication springApplication = new SpringApplication(TcpServerApplication.class);
		springApplication.addListeners(new TcpServer());
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);


	}
}

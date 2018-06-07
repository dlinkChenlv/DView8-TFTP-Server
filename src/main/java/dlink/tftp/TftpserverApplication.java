package dlink.tftp;

import dlink.tftp.server.TftpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Chenlv on 2018.5.25.
 */
@SpringBootApplication
public class TftpserverApplication implements ApplicationRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TftpserverApplication.class);
	@Value("${tftp.default_server_port}")
	private String port;
	@Override
	public void run(ApplicationArguments var1) throws Exception{
		LOGGER.info("tftp server is running now ++++++++++++++++++++++++++++++++++++++++");
		TftpServer server = new TftpServer(Integer.valueOf(port));
		server.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(TftpserverApplication.class, args);
	}
}

package dlink.tftp;

import dlink.tftp.server.TftpServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TftpserverApplicationTests {
	@Value("${spring.mail.username}")
	public  String USER_NAME;
	@Test
	public void contextLoads() {
		System.out.print(USER_NAME+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		TftpServer server = new TftpServer(Integer.valueOf(USER_NAME));
		server.start();
	}

}

package dlink.tftp.server;

import dlink.tftp.server.core.ServerWRQHandler;
import dlink.tftp.server.core.TFTPUDPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by 91680 on 2018.5.29.
 */
@Component
@Order(value = 1)
public class ApplicationServerRunner implements ApplicationRunner{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationServerRunner.class);
    public static String port;
    @Value("${tftp.default_server_port}")
    private String portTmp;

    @PostConstruct
    public void init() {
        port = portTmp;
    }
    @Override
    public void run(ApplicationArguments var1) throws Exception{
        init();
        logger.info("tftp server is running now ++++++++++++++++++++++++++++++++++++++++");
        TFTPUDPServer server = new TFTPUDPServer(Integer.valueOf(port));
        server.start();
    }

}

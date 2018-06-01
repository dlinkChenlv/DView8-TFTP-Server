package dlink.tftp.server.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration类 定义了TFTP-UDP-Server开启tftp服务的一些参数
 */
@Component
public class TftpConfiguration {

    private static int default_server_port,max_data_length,max_timeouts,max_invalids,timeout,max_packet_length;
    private static String current_directory;
    @Value("${tftp.current_directory}")
    public void setCurrent_directory(String current_directory) {
        TftpConfiguration.current_directory = current_directory;
    }
    @Value("${tftp.default_server_port}")
    public void setDefault_server_port(String default_server_port_tmp) {
        TftpConfiguration.default_server_port = Integer.valueOf(default_server_port_tmp);;
    }
    @Value("${tftp.max_packet_length}")
    public void setMax_packet_length(String max_packet_length) {
        TftpConfiguration.max_packet_length = Integer.valueOf(max_packet_length);;
    }
    @Value("${tftp.max_data_length}")
    public void setMax_data_length(String max_data_length_tmp) {
        TftpConfiguration.max_data_length = Integer.valueOf(max_data_length_tmp);;
    }
    @Value("${tftp.max_timeouts}")
    public void setMax_timeouts(String max_timeouts) {
        TftpConfiguration.max_timeouts = Integer.valueOf(max_timeouts);;
    }
    @Value("${tftp.max_invalids}")
    public void setMax_invalids(String max_invalids) {
        TftpConfiguration.max_invalids = Integer.valueOf(max_invalids);;
    }
    @Value("${tftp.timeout}")
    public void setTimeout(String setTtimeout) {
        TftpConfiguration.timeout = Integer.valueOf(setTtimeout);;
    }

    public static String getCurrent_directory() {return current_directory;}

    public static int getDefault_server_port() {
        return default_server_port;
    }

    public static int getMax_data_length() {
        return max_data_length;
    }

    public static int getMax_timeouts() {
        return max_timeouts;
    }

    public static int getMax_invalids() {
        return max_invalids;
    }

    public static int getTimeout() {
        return timeout;
    }

    public static int getMax_packet_length() {
        return max_packet_length;
    }
}

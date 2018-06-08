package dlink.tftp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 91680 on 2018.6.7.
 */
public class propertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(propertiesUtil.class);
    private static final String path=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/application-tftp.properties";
    private static Properties prop;

    public static void load(String path){
        prop= new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            prop.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 更改配置文件
     *
     * @param key
     * @param value
     */
    public static Boolean updatePro(String key,String value){
        if(prop==null){
            load(path);
        }
        LOGGER.info("property values before or before adding or modifying："+key+"=" + prop.getProperty(key));
        prop.setProperty(key, value);
        try {
            FileOutputStream fos = new FileOutputStream(path);
            prop.store(fos, "Copyright (c) Boxcode Studio");
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        LOGGER.info("added or modified attribute values："+key+"=" + prop.getProperty(key));
        return true;
    }
    /**
     * 获取配置文件值
     *
     * @param key
     */
    public static String getPro(String key){
        if(prop==null){
            load(path);
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            prop.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}

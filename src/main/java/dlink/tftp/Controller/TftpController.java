package dlink.tftp.Controller;

import dlink.tftp.util.FileUtil;
import dlink.tftp.util.TftpConfiguration;
import dlink.tftp.util.propertiesUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by Chenlv on 2018.5.25.
 */
@RestController
public class TftpController {
    //更改默认路径
    //@Autowired
    //private TftpConnectsRepository tftpConnectsRepository;
    @RequestMapping(value = "/updateBlockSizeValue", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> updateBlockSizeValue(@RequestParam("blockSizeValue") String current_directory){
        propertiesUtil.updatePro("tftp.current_directory","E://");
        TftpConfiguration.inint();
        Map<String, String> map=new HashMap<String, String>();
        map.put("code", "100");
        return map;
    }
    //更改tftserver 参数
    //@Autowired
    //private TftpConnectsRepository tftpConnectsRepository;
    @RequestMapping(value = "/updateTftpConfig", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> ajax(@RequestParam("current_directory") String current_directory,
                                   @RequestParam("default_server_port") String default_server_port,
                                   @RequestParam("max_packet_length") String max_packet_length,
                                   @RequestParam("max_data_length") String max_data_length,
                                   @RequestParam("max_timeouts") String max_timeouts,
                                   @RequestParam("max_invalids") String max_invalids,
                                   @RequestParam("timeout") String timeout){
        propertiesUtil.updatePro("tftp.current_directory",current_directory);
        propertiesUtil.updatePro("tftp.default_server_port",default_server_port);
        propertiesUtil.updatePro("tftp.max_packet_length",max_packet_length);
        propertiesUtil.updatePro("tftp.max_data_length",max_data_length);
        propertiesUtil.updatePro("tftp.max_timeouts",max_timeouts);
        propertiesUtil.updatePro("tftp.max_invalids",max_invalids);
        propertiesUtil.updatePro("tftp.timeout",timeout);
        TftpConfiguration.inint();
        Map<String, String> map=new HashMap<String, String>();
        map.put("code", "100");
        return map;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletResponse response,
                              HttpServletRequest request) throws MalformedURLException {
        return new ModelAndView("index");

    }



}


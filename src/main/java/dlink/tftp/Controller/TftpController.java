package dlink.tftp.Controller;

import dlink.tftp.util.FileUtil;
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
    //触发Put操作
    //@Autowired
    //private TftpConnectsRepository tftpConnectsRepository;
    @RequestMapping(value = "/putTftpServer", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> ajax(@RequestParam("blockSizeValue") String blockSizeValue,
                                   @RequestParam("remoteFileValue") String remoteFileValue,
                                   @RequestParam("clientInterfacePortValue") String clientInterfacePortValue,
                                   @RequestParam("clientInterfaceIpValue") String clientInterfaceIpValue){
//        TftpConnects tftpConnects=new TftpConnects();
//        tftpConnects.setBlockSize(blockSizeValue);
//        tftpConnects.setConnectsIp(clientInterfaceIpValue);
//        tftpConnects.setConnectsPort(clientInterfacePortValue);
//        tftpConnects.setLocalFile("");
//        tftpConnects.setRemoteFile(remoteFileValue);
//        OaTftpConnects oaEmail = new OaTftpConnects(tftpConnects);
//        tftpConnectsRepository.save(oaEmail);
        Map<String, String> map=new HashMap<String, String>();
        map.put("code", "100");
        return map;
    }
    //上传文件
    @ResponseBody
    @RequestMapping(value = "/apk_upload" ,method = RequestMethod.POST)
    public Map<String, Object> uploadApkFile(HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> json = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        FileUtil.uploadFile((MultipartHttpServletRequest) request);
        json.put("code", "200");
        return json;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletResponse response,
                              HttpServletRequest request) throws MalformedURLException {
        return new ModelAndView("index");

    }



}


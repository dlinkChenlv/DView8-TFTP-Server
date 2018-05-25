package dlink.tftp.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ChenLv on 2018.5.25.
 */
public class FileUtil {
    public static void uploadFile(MultipartHttpServletRequest multipartRequest){
        MultipartFile multipartFile = null;
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);
        }
        String filename = multipartFile.getOriginalFilename();
        String path = "D:/";
        File fileSourcePath = new File("D:/");
        File fileSource = new File(fileSourcePath, filename);
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdirs();
        }
        try {
            multipartFile.transferTo(fileSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

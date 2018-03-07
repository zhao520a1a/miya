package com.miya.controller;

import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.entity.User;
import com.miya.service.impl.UserServiceImpl;
import com.miya.utils.FastDFSClient;
import com.miya.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传controller
 *
 * @author 赵金鑫
 */
@Slf4j
@RestController
@RequestMapping("/pic")
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseModal handleFileUpload(@RequestPart(value = "file") MultipartFile uploadFile) {
        log.info("OriginalFilename{}", uploadFile.getOriginalFilename());
        ResponseModal resp = new ResponseModal();
        try {
            System.out.println("-------");
            //接收上传的文件 ＋ 取扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVER_URL + url;
            log.info("上传图片的URL:{}", url);
            resp = ResponseModal.successMsg(url);

            User user = new User();
            user.setAvatar(url);
            userService.update(user);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传图片异常：{}" + StringUtil.getTrace(e));
            resp = ResponseModal.failMsg("图片上传失败");
        }
        return resp;

    }


    //    @RequestMapping("/upload")
//    public RespObject<String> picUpload1(@RequestParam String id, @RequestBody MultipartFile uploadFile) {
//        log.info("id:{} file:{}",id,uploadFile);
//        try {
//            //接收上传文件 + 取扩展名
//            String originalFilename = uploadFile.getOriginalFilename();
//            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//            //上传到服务器上
//            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fdfs_client.conf");
//            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
//            url = IMAGE_SERVER_URL + url;
//            return new RespObject<String> (ResponseModal.successMsg("图片上传成功"),url);
//        } catch (Exception e) {
//            log.info("{}", StringUtil.getTrace(e));
//            return new RespObject<String> (ResponseModal.errorMsg("图片上传失败"),"");
//        }
//    }
    @RequestMapping("/upload1")
    public RespObject<String> picUpload1() {
        String filePath = "D:\\Download\\1.jpg";
        log.info("开始Upload1---{}", filePath);
        try {
            //接收上传文件 + 取扩展名
//            String originalFilename = uploadFile.getOriginalFilename();
//            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //上传到服务器上
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
            String url = fastDFSClient.uploadFile(filePath, "jpg");
            url = IMAGE_SERVER_URL + url;
            log.info("URL:{}", url);
            System.out.println(url);
            return new RespObject<String>(ResponseModal.successMsg("图片上传成功"), url);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<String>(ResponseModal.errorMsg("图片上传失败"), "");
        }
    }


    @Test
    public void test() {
        //上传到服务器上
        FastDFSClient fastDFSClient = null;
        String filePath = "D:\\Download\\1.jpg";
        String configPath = "D:\\OwnProjects\\miya\\miya-manage\\src\\main\\resources\\fdfs_client.conf";
        try {
            fastDFSClient = new FastDFSClient(configPath);

            String url = fastDFSClient.uploadFile(filePath, "jpg");
            url = IMAGE_SERVER_URL + url;

            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

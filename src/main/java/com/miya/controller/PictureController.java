package com.miya.controller;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.FastDFSClient;
import com.golden.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

/**
 * 图片上传controller
 *
 */
@Slf4j
@RestController
@RequestMapping("/content/pic")
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    private static Properties props;

    private FastDFSClient fastDFSClient = new FastDFSClient(props);

    static {
        props = new Properties();
        props.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "120.78.222.191:22122");
        props.setProperty(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, "2");
        props.setProperty(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, "30");
        props.setProperty(ClientGlobal.PROP_KEY_CHARSET, "UTF-8");
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, "no");
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, "FastDFS1234567890");
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, "80");
    }

    public PictureController() throws Exception {
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    RespObject<String>  handleFileUpload(@RequestPart(value = "file") MultipartFile uploadFile) {
        log.info("OriginalFilename{}", uploadFile.getOriginalFilename());
        String url = "";
        try {
            //接收上传的文件 ＋ 取扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传到图片服务器
            fastDFSClient = new FastDFSClient(props);
            url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVER_URL + url;
            log.info("上传图片的URL:{}", url);
        } catch (Exception e) {
            log.error("上传图片异常：{}" + StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("内容图片上传失败"), "");
        }
        return new RespObject<>(ResponseModal.successMsg("内容图片上传成功"), url);
    }

    @RequestMapping(value = "/delete")
    ResponseModal handleRemove(@RequestParam(value = "fileId") String fileId) {
        log.info("fileId:{}", fileId);

        try {
            if(!StringUtil.isEmpty(fileId)) {
//                http://120.78.222.191/  group1/M00/00/00/wKgBMFrN-GqAVWdxAAAGZNxGB6k602.png
                fileId = fileId.substring(22, fileId.length());
                log.info("删除图片的fileId:{}", fileId);
                //上传到图片服务器
                fastDFSClient = new FastDFSClient(props);
                if(fastDFSClient.deleteFile(fileId) == 0 ) {
                    return  ResponseModal.successMsg("图片删除成功") ;
                } else{
                    return  ResponseModal.failMsg("图片删除失败") ;
                }
            } else {
                return  ResponseModal.failMsg("获取图片信息失败") ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除图片异常：{}" + StringUtil.getTrace(e));
            return ResponseModal.errorMsg("图片删除失败") ;
        }
    }

    @RequestMapping(value = "/get" )
    RespObject<FileInfo> getFileInfo(@RequestParam(value = "fileId") String fileId) {
        FileInfo fileInfo = null;
        try {
            if(!StringUtil.isEmpty(fileId)) {
                fileId = fileId.substring(22, fileId.length());
                log.info("图片的fileId:{}", fileId);
                //上传到图片服务器
                fastDFSClient = new FastDFSClient(props);
                fileInfo = fastDFSClient.getFileInfo(fileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取图片信息异常：{}" + StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获得图片信息失败"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获得图片信息成功"), fileInfo);
    }

    @Test
    public void test() {
        FastDFSClient fastDFSClient = null;
        String filePath = "/Users/Golden/Downloads/1.jpg";
        String configPath = "/Users/Golden/Documents/OwnProjects/miya_code/miya-service/miya-content-service/src/main/resources/fdfs_client.conf";
        try {
            fastDFSClient = new FastDFSClient(configPath);

            //上传
            String url = fastDFSClient.uploadFile(filePath, "jpg");
            url = IMAGE_SERVER_URL + url;
            System.out.println(url);
            //下载
//           int i = fastDFSClient.deleteFile("group1/M00/00/00/wKgBMFrN_K2AFClBAAA0dbLBau8170.png");
//            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

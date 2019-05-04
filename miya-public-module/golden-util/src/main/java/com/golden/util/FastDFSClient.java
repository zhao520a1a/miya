package com.golden.util;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class FastDFSClient {

	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;
	
	public FastDFSClient(String conf) throws Exception {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
//       因打包运行后无法读取配置文件
//		ClientGlobal.init(conf);   fdfs_client.conf的配置方式--- tracker_server = 120.78.222.191:22122
		ClientGlobal.initByProperties(conf);       //  fdfs_client.conf的配置方式---     fastdfs.tracker_servers = 120.78.222.191:22122
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		//坑：配置自己StorageServer的IP
		storageServer = new StorageServer("120.78.222.191",23000,0);
		storageClient = new StorageClient1(trackerServer, storageServer);
	}


	public FastDFSClient(Properties props) throws Exception {
		ClientGlobal.initByProperties(props);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		//坑：配置自己StorageServer的IP
		storageServer = new StorageServer("120.78.222.191",23000,0);
		storageClient = new StorageClient1(trackerServer, storageServer);
	}


//	@Before()
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}


	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileName 文件全路径
	 * @param extName 文件扩展名，不包含（.）
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		return   storageClient.upload_file1(fileName, extName, metas);
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileContent 文件的内容，字节数组
	 * @param extName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
		return  storageClient.upload_file1(fileContent, extName, metas);
	}
	
	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}

	public int downloadFile(String file_id, String local_filename) throws IOException, MyException {
			return storageClient.download_file1(file_id, local_filename);
	}
	public FileInfo getFileInfo(String file_id ) throws IOException, MyException {
		FileInfo fi = storageClient.get_file_info1(file_id);
//		System.out.println(fi.getSourceIpAddr());
//		System.out.println(fi.getFileSize());
//		System.out.println(fi.getCreateTimestamp());
//		System.out.println(fi.getCrc32());
		return fi;
	}

	public NameValuePair[] getFileMate(String file_id ) throws IOException, MyException {
		NameValuePair nvps [] = storageClient.get_metadata1(file_id);
//		for(NameValuePair nvp : nvps){
//			System.out.println(nvp.getName() + ":" + nvp.getValue());
//		}
		return nvps;
	}

	public int  deleteFile(String file_id) throws IOException, MyException {
		return  storageClient.delete_file1(file_id);  //i==0 ? "删除成功" : "删除失败:
	}


	public static void main(String[] args) throws Exception {
		String filename = "D:\\Download\\1.jpg";
		//上传到服务器上
		FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
		NameValuePair nvp [] = new NameValuePair[]{
				new NameValuePair("age", "18"),
				new NameValuePair("sex", "male")
		};
		String url = fastDFSClient.uploadFile(filename, "jpg", nvp);
		log.info("上传文件URL：{}",url);

		//String local_filename = "/Users/golden/Pictures/" + UUID.randomUUID().toString() + ".jpg";
		//System.out.println(fastDFSClient.downloadFile(url,local_filename) == 0 ? "下载成功" : "下载失败");
		//fastDFSClient.getFileInfo(url);
		//fastDFSClient.getFileMate(url);
		//System.out.println(fastDFSClient.deleteFile(url) == 0 ? "删除成功" : "删除失败");
	}


}

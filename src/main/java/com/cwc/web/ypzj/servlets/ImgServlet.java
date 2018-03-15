package com.cwc.web.ypzj.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cwc.web.ypzj.DAO.ImageRepository;
import com.cwc.web.ypzj.jsonObj.ImgUploadResponseUnit;
import com.cwc.web.ypzj.jsonObj.ImgUploadResponser;
import com.cwc.web.ypzj.servletObj.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ImgServlet
 */
@MultipartConfig
public class ImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /** 总上传文件最大为10M*/
    private static final Long TOTAL_FILE_MAXSIZE = 10000000L;
    /** 单个上传文件最大为10M*/
    private static final int SINGLE_FILE_MAXSIZE = 2*1024*1024;
    /** 图片文件夹路径*/
    private static final String IMAGE_DIR_PATH = "img/";
    /** 临时图片文件夹路径*/
    private static final String TEMP_IMAGE_DIR_PATH = "img/temp/";
    private static final int BUFFER_LENGTH=1024;
    	private final SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(this.getServletContext().getResource("img").toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        ServletContext context=this.getServletContext();
        PrintWriter out = response.getWriter();
        ImgUploadResponser responser=new ImgUploadResponser();
		String imgDirPath=context.getRealPath(IMAGE_DIR_PATH);
		String tempImgDirPath=context.getRealPath(TEMP_IMAGE_DIR_PATH);
		File tempPathFile = new File(imgDirPath);
        File realPathFile = new File(tempImgDirPath);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
        if (!realPathFile.exists()) {
            realPathFile.mkdirs();
        }
        // 文件对象的工厂类
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置最大上传大小
        factory.setSizeThreshold(SINGLE_FILE_MAXSIZE);
        // 将临时文件夹交给文件对象的工厂类
        factory.setRepository(tempPathFile);
        // 创建一个上传文件的处理者
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置所有请求的总大小
         upload.setSizeMax(TOTAL_FILE_MAXSIZE);
		try {
			List<FileItem> items = upload.parseRequest(request);
            // 处理解析处理的请求对象
            Iterator<FileItem> iter = items.iterator();
            while(iter.hasNext()) {
            		FileItem item = iter.next();
            		if(!item.isFormField()) {
            			
            			String fileName=item.getName();
            			if(fileName!=null&&!"".equals(fileName)) {
            				String ext=fileName.substring(fileName.lastIndexOf("."));
            				fileName=df.format(new Date())+ext;
            				String ansName=dealMD5AndSaving(item.getInputStream(), imgDirPath+fileName);
            				ImgUploadResponseUnit unit;
            				if(ansName==null) {
            					unit=new ImgUploadResponseUnit(0, "add failed", null);
            				}
            				else if(fileName.equals(ansName)) {
            					unit=new ImgUploadResponseUnit(1, "add success",fileName );
            				}
            				else {
            					unit=new ImgUploadResponseUnit(2, "picture has exited", ansName);
            				}
            				responser.addUnit(unit);
            			}
            		}
            }
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ObjectMapper objectMapper=new ObjectMapper();
        String ans = objectMapper.writeValueAsString(responser);
        out.println(ans);
	}
	private String dealMD5AndSaving(InputStream in,String filePath) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest=MessageDigest.getInstance("MD5");
		FileOutputStream out=null;
		try {
			out = new FileOutputStream(filePath);
			byte[] buffer=new byte[BUFFER_LENGTH];
			int read=in.read(buffer);
			while(read>-1) {
				digest.update(buffer, 0, read);
				out.write(buffer, 0, read);
				read=in.read(buffer);
			}
			byte[] MD5=digest.digest();
			Image res=ImageRepository.findImageNameByMD5(MD5);
			//如果图片不存在
			if(res==null) {
				//插入图片
				res=ImageRepository.createNewImage(filePath.substring(filePath.lastIndexOf("/")+1), MD5);
			}
			else {
				File f=new File(filePath);
				f.delete();
			}
			//如果图片存在，返回已存在图片名
			return res.getImageName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(out!=null)out.close();
		}
		return null;
	}

}

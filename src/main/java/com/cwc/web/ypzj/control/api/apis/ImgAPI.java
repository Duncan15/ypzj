package com.cwc.web.ypzj.control.api.apis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.CarouselRepository;
import com.cwc.web.ypzj.model.obj.Carousel;
import com.cwc.web.ypzj.util.RequestParser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cwc.web.ypzj.model.DAO.ImageRepository;
import com.cwc.web.ypzj.control.api.obj.ImgUploadResponseUnit;
import com.cwc.web.ypzj.control.api.obj.ImgUploadResponser;
import com.cwc.web.ypzj.model.obj.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ImgAPI
 */
@MultipartConfig
public class ImgAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /** 总上传文件最大为10M*/
    private static final Long TOTAL_FILE_MAXSIZE = 10000000L;
    /** 单个上传文件最大为10M*/
    private static final int SINGLE_FILE_MAXSIZE = 2*1024*1024;
    /** 图片文件夹路径*/
    private static final String IMAGE_DIR_PATH = "img/";
    /** 临时图片文件夹路径*/
    private static final String TEMP_IMAGE_DIR_PATH = "img/temp/";

    private String absImageDirPath="";

    private static final int BUFFER_LENGTH=1024;
    private final SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
    private ServletFileUpload upload;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context=this.getServletContext();
		absImageDirPath=context.getRealPath(IMAGE_DIR_PATH);
		String tempImgDirPath=context.getRealPath(TEMP_IMAGE_DIR_PATH);
		File tempPathFile = new File(tempImgDirPath);
		File realPathFile = new File(absImageDirPath);
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
		upload = new ServletFileUpload(factory);
		// 设置所有请求的总大小
		upload.setSizeMax(TOTAL_FILE_MAXSIZE);
	}

	@Override
	public void destroy() {
		super.destroy();
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
		String[] param= RequestParser.parsePath(request.getRequestURI(),1);

        if(param==null||!ServletFileUpload.isMultipartContent(request)){
        	RespWrapper.failReturn(response,Errno.PARAMERR);
        	return;
		}
        if("article".equals(param[0])){
        	articlePicDeal(request,response);
		}else if("admin".equals(param[0])){
			carouselPicDeal(request,response);
		}
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
	private void articlePicDeal(HttpServletRequest request, HttpServletResponse response)throws IOException{
		PrintWriter out = response.getWriter();
		ImgUploadResponser responser=new ImgUploadResponser();
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
						String ansName=dealMD5AndSaving(item.getInputStream(), absImageDirPath+fileName);
						ImgUploadResponseUnit unit;
						if(ansName==null) {
							unit=new ImgUploadResponseUnit(0, "add failed", null);
						}else {
							if(fileName.equals(ansName)) {
								unit=new ImgUploadResponseUnit(1, "add success",fileName );
							}
							else {
								unit=new ImgUploadResponseUnit(2, "picture has exited", ansName);
							}
							HttpSession session=request.getSession();
							if(session.getAttribute("avatar_id")==null){
								//must delete this session attribute when store this id into model
								session.setAttribute("avatar_id",ansName);
							}
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
	private void carouselPicDeal(HttpServletRequest request, HttpServletResponse response){
		boolean flag=false;
		try{
			List<FileItem> items=upload.parseRequest(request);
			Iterator<FileItem> iter=items.iterator();
			while (iter.hasNext()){
				FileItem item=iter.next();
				if(!item.isFormField()){
					String fileName=item.getName();
					if(fileName!=null&&""!=fileName){
						String ext=fileName.substring(fileName.lastIndexOf("."));
						fileName=df.format(new Date())+ext;
						String ansName=dealMD5AndSaving(item.getInputStream(), absImageDirPath+fileName);
						if(ansName!=null){
							Carousel carousel=new Carousel();
							carousel.setImageName(ansName);
							if(CarouselRepository.saveCarousels(carousel)!=null){
								flag=true;
							}
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		if(!flag){
			RespWrapper.failReturn(response, Errno.SYSERR);
		}else{
			RespWrapper.successReturn(response,null);
		}
	}
}

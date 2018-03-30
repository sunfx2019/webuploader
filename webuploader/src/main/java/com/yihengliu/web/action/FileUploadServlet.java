package com.yihengliu.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 普通文件上传方式
 * 
 * @Description 
 * @author FeiXiangSun
 * @date 2018年3月13日 下午11:33:01
 */
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String serverPath = "c:\\temp";

    private Log log = LogFactory.getLog(getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //response.getWriter().append("Served at: ").append(request.getContextPath());

        // 1.创建DiskFileItemFactory对象，配置缓存用
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

        // 2. 创建 ServletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

        // 3. 设置文件名称编码
        servletFileUpload.setHeaderEncoding("utf-8");

        // 4. 开始解析文件
        // 文件md5获取的字符串
        String fileMd5 = null;
        // 文件的索引
        String chunk = null;
        
        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : items) {
                // >> 普通数据
                if (fileItem.isFormField()) { 
                    String fieldName = fileItem.getFieldName();
                    if ("info".equals(fieldName)) {
                        String info = fileItem.getString("utf-8");
                        log.debug("info:" + info);
                    }
                    if ("fileMd5".equals(fieldName)) {
                        fileMd5 = fileItem.getString("utf-8");
                        log.debug("fileMd5:" + fileMd5);
                    }
                    if ("chunk".equals(fieldName)) {
                        chunk = fileItem.getString("utf-8");
                        log.debug("chunk:" + chunk);
                    }
                } else { 
                    // 如果文件夹没有创建文件夹
                    File file = new File(serverPath + "/" + fileMd5);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    // 保存文件
                    File chunkFile = new File(serverPath + "/" + fileMd5 + "/" + chunk);
                    FileUtils.copyInputStreamToFile(fileItem.getInputStream(), chunkFile);
                    response.getWriter().append("Served at: ").append(chunkFile.getAbsolutePath());
                    log.debug(String.format("上传文件[%s]ok...", chunkFile.getAbsolutePath()));
                }
            }
        } catch (Exception e) {
            response.getWriter().append("Served is error: ").append(e.getMessage());
            log.debug(e.getMessage(), e);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

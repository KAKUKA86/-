package iblessing.xyz.offer.servlet;

import iblessing.xyz.offer.dao.ResumeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * 简历头像图片上传
 *
 * @author 14717
 */
@WebServlet(name = "ResumePicUploadServlet",value = "/ResumePicUploadServlet")
@MultipartConfig
public class ResumePicUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ResumePicUploadServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         this.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        //获取上传文件域
        Part part = req.getPart("headShot");
        //获取上传文件名称
        String fileName = part.getSubmittedFileName();
        //为了防止上传文件重名，对文件重命名
        String newFileName = System.currentTimeMillis()
                + fileName.substring(fileName.lastIndexOf("."));
        // 将上传的文件保存在服务器项目发布路径的applicant/images目录下
        String filepath = getServletContext().getRealPath("/applicant/images");
        System.out.println("头像保存路径为：" + filepath);
        File f = new File(filepath);
        if (!f.exists())
            f.mkdirs();
        part.write(filepath + "/" + newFileName);
        //从会话中获取当前用户简历标识
        int resumeID = (Integer)req.getSession().getAttribute("SESSION_RESUMEID");
        // 更新简历照片
        ResumeDAO dao = new ResumeDAO();
        // 此处模拟使用编号为1的简历（注意保证数据库中已有此编号的简历）进行照片的更新
        dao.updateHeadShot(resumeID, newFileName);
        // 照片更新成功，回到“我的简历”页面
        resp.sendRedirect("applicant/resume.html");
    }
}

package iblessing.xyz.offer.servlet;

import iblessing.xyz.offer.bean.Applicant;
import iblessing.xyz.offer.bean.ResumeBasicinfo;
import iblessing.xyz.offer.dao.ResumeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简历基本信息操作Servlet
 *
 * @author 14717
 */
@WebServlet(name = "ResumeBasicinfoServlet",value = "/ResumeBasicinfoServlet")
public class ResumeBasicinfoServlet extends HttpServlet {
    public ResumeBasicinfoServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求响应编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        //获取请求操作类型
        String type = req.getParameter("type");
        //简历添加操作
        if ("add".equals(type)) {
            //封装请求数据
            ResumeBasicinfo basicinfo = this.requestDataObj(req);
            //从会话对象中获取当前登录用户标识
            Applicant applicant = (Applicant)req.getAttribute("SESSION_APPLICANT");
            // 向数据库中添加当前用户的简历
            ResumeDAO dao = new ResumeDAO();
            int basicinfoID = dao.add(basicinfo, applicant.getApplicantId());
            // 将简历标识存入会话对象中
            req.getSession().setAttribute("SESSION_RESUMEID", basicinfoID);
            // 操作成功，跳回“我的简历”页面
            resp.sendRedirect("applicant/resume.html");
        }
    }

    /**
     * 将请求的简历数据封装成一个对象
     *
     * @param request
     * @return
     * @throws Exception
     */
    private ResumeBasicinfo requestDataObj(HttpServletRequest request) {
        ResumeBasicinfo basicinfo = null;
        // 获得请求数据
        String realName = request.getParameter("realName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String currentLoca = request.getParameter("currentLoc");
        String residentLoca = request.getParameter("residentLoc");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String jobIntension = request.getParameter("jobIntension");
        String jobExperience = request.getParameter("jobExperience");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdayDate = null;
        try {
            birthdayDate = sdf.parse(birthday);
        } catch (ParseException e) {
            birthdayDate = null;
        }
        // 将请求数据封装成一个简历基本信息对象
        basicinfo = new ResumeBasicinfo(realName, gender, birthdayDate, currentLoca, residentLoca, telephone, email
                , jobIntension, jobExperience);
        return basicinfo;
    }
}

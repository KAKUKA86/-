package iblessing.xyz.offer.servlet;

import iblessing.xyz.offer.dao.ApplicantDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 求职者注册功能实现
 * @author 14717
 */
@WebServlet(name = "ApplicantRegisterServlet", value = "/ApplicantRegisterServlet")
public class ApplicantRegisterServlet extends HttpServlet {
    private static final long serivalVersionUID = 1L;

    public ApplicantRegisterServlet(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求和响应编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //获取请求参数
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        //判断验证码是否正确
        String sessionValidateCode = (String)request.getSession().getAttribute("SESSION_VALIDATECODE");
        if(!sessionValidateCode.equals(verifyCode)) {
            out.print("<script type='text/javascript'>");
            out.print("alert('请正确输入验证码！');");
            out.print("window.location='register.html';");
            out.print("</script>");
        }else{
            //判断邮箱是否已经被注册
            ApplicantDAO dao = new ApplicantDAO();
            boolean flag = dao.isExistEmail(email);
            if(flag){
                // 邮箱已注册,进行错误提示
                out.print("<script type='text/javascript'>");
                out.print("alert('邮箱已被注册，请重新输入！');");
                out.print("window.location='register.html';");
                out.print("</script>");
            }else{
                // 邮箱未被注册，保存注册用户信息
                dao.save(email,password);
                // 注册成功，重定向到登录页面
                response.sendRedirect("login.html");
            }
        }


    }
}

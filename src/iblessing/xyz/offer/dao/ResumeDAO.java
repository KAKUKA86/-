package iblessing.xyz.offer.dao;

import iblessing.xyz.offer.bean.ResumeBasicinfo;
import iblessing.xyz.offer.util.DBUtil;

import java.sql.*;

public class ResumeDAO {
    /**
     * 简历基本信息添加和主键标识查询
     * @param basicinfo
     * @param applicantID
     * @return
     */
     public int add(ResumeBasicinfo basicinfo, int applicantID) {
         int basicinfoID = 0;
         String sql = "INSERT INTO jdbc_offer.tb_resume_basicinfo("
                 + "basicinfo_id, realname, gender, birthday, current_loc, "
                 + "resident_loc, telephone, email, job_intension, job_experience, head_shot,applicant_id) "
                 + "VALUES(SEQ_ITOFFER_RESUMEBASICINFO.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";
         Connection conn = DBUtil.getConnection();
         PreparedStatement pstmt = null;
         try {
             // 关闭自动提交
             conn.setAutoCommit(false);
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, basicinfo.getRealName());
             pstmt.setString(2, basicinfo.getGender());
             pstmt.setTimestamp(3, basicinfo.getBirthday() == null ? null
                     : new Timestamp(basicinfo.getBirthday().getTime()));
             pstmt.setString(4, basicinfo.getCurrentLoc());
             pstmt.setString(5, basicinfo.getResidentLoc());
             pstmt.setString(6, basicinfo.getTelephone());
             pstmt.setString(7, basicinfo.getEmail());
             pstmt.setString(8, basicinfo.getJobIntension());
             pstmt.setString(9, basicinfo.getJobExperience());
             pstmt.setString(10, basicinfo.getHeadShot());
             pstmt.setInt(11, applicantID);
             pstmt.executeUpdate();
             // 获取当前生成的简历标识
             String sql2 = "SELECT SEQ_ITOFFER_RESUMEBASICINFO.CURRVAL FROM dual";
             pstmt = conn.prepareStatement(sql2);
             ResultSet rs = pstmt.executeQuery();
             if(rs.next())
                 basicinfoID = rs.getInt(1);
             // 事务提交
             conn.commit();
         } catch (SQLException e) {
             try {
                 // 事务回滚
                 conn.rollback();
             } catch (SQLException e1) {
                 e1.printStackTrace();
             }
             e.printStackTrace();
         } finally {
             DBUtil.closeJDBC(null, pstmt, conn);
         }
         return basicinfoID;
     }
    /**
     * 简历照片更新
     *
     * @param basicinfoId
     * @param newFileName
     */
    public void updateHeadShot(int basicinfoId,String newFileName) {
        String sql = "UPDATE jdbc_offer.tb_resume_basicinfo SET head_shot=? WHERE basicinfo_id=?";
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newFileName);
            pstmt.setInt(2, basicinfoId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }
}

package iblessing.xyz.offer.dao;

import iblessing.xyz.offer.util.DBUtil;

import java.util.Date;
import java.sql.*;

public class ApplicantDAO {
    private Connection connection = DBUtil.getConnection();
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    /**
     * 验证Email是否已经被注册
     * @return
     */
    public boolean isExistEmail(String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM jdbc_offer.tb_applicant WHERE applicant_email=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.closeJDBC(resultSet,preparedStatement,connection);
        }
        return false;
    }
    /**
     * 求职者信息注册保存
     *
     * @param email
     * @param password
     */
    public void save(String email,String password) {
        String sql = "INSERT INTO jdbc_offer.tb_applicant(applicant_id,applicant_email,applicant_pwd,applicant_registdate) " +
                "VALUES(seq_itoffer_applicant.nextval,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            preparedStatement.setTimestamp(3,new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeJDBC(null,preparedStatement,connection);
        }
    }
    /**
     * 注册用户登录
     *
     * @param email
     * @param password
     * @return
     */
    public int login(String email,String password) {
        int applicantID = 0;
        String sql =  "SELECT applicant_id FROM jdbc_offer.tb_applicant WHERE applicant_email=? and applicant_pwd=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                applicantID = resultSet.getInt("applicant_id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(resultSet,preparedStatement,connection);
        }
        return applicantID;
    }
    /**
     * 判断是否已有简历
     *
     * @param email
     * @return
     */
    public int isExistResume(int applicantID) {
        int resumeID = 0;
        String sql = "SELECT basicinfo_id FROM jdbc_offer.tb_resume_basicinfo WHERE applicant_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,applicantID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                resumeID = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeJDBC(resultSet,preparedStatement,connection);
        }
        return resumeID;
    }
}

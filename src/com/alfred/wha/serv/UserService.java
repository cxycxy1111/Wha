package com.alfred.wha.serv;

import com.alfred.wha.dao.LogDao;
import com.alfred.wha.dao.UserDAO;
import com.alfred.wha.dao.UserLoginLogDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class UserService extends Service{

    public UserDAO userDAO;
    public UserLoginLogDAO userLoginLogDAO;

    public UserService() {
        userDAO = new UserDAO();
        userLoginLogDAO = new UserLoginLogDAO();
    }

    /**
     * ---------------------------------
     * 用户端
     * ---------------------------------
     */

    /**
     * 注册
     * @param username
     * @param pwd
     * @param email
     * @return
     */
    public String register(String username,String pwd,String email) {
        if (userDAO.isExist(username)) {
            return DUPLICATE;
        }
        if (userDAO.add(username, Tool.getMd5FromString(pwd),email)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public String delete(long id,long operator,int operator_type) {
        if (userDAO.isExist(id)) {
            if (userDAO.delete(id)) {
                LogDao.recordUserLog(id,LOG_OPERATE_DELETE,operator,operator_type,"");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 锁定用户
     * @param id
     * @return
     */
    public String lock(long id,long operator,int operator_type) {
        if (userDAO.isExist(id)) {
            if (userDAO.lock(id)) {
                LogDao.recordUserLog(id,LOG_OPERATE_LOCK,operator,operator_type,"");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 修改昵称与签名
     * @param id
     * @param nick_name
     * @param email
     * @param motto
     * @return
     */
    public String changeNickNameAndMotto(long id,int operator_type,String nick_name,String email,String motto) {
        if (userDAO.updateInfo(id,nick_name,email,motto)){
            LogDao.recordUserLog(id,LOG_OPERATE_EDIT,id,operator_type,"新昵称:" + nick_name + ",新邮箱:" + email + ",新签名:" + motto);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 修改密码
     * @param id
     * @param new_pwd
     * @return
     */
    public String changePwd(long id,int operator_type,String new_pwd) {
        if (userDAO.updatePwd(id,new_pwd)) {
            LogDao.recordUserLog(id,LOG_OPERATE_CHANGE_PWD,id,operator_type,"新昵称:" + Tool.getMd5FromString(new_pwd));
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 解锁用户
     * @param id
     * @return
     */
    public String unlock(long id,int operator_type) {
        if (userDAO.isExist(id)) {
            if (userDAO.unlock(id)) {
                LogDao.recordUserLog(id,LOG_OPERATE_EDIT,id,operator_type,"");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 登录检查
     * @param username
     * @param pwd
     * @return
     */
    public String loginCheck(String username,String pwd,String ip,String system_version,String system_model,String device_brand,String app_version) {
        if (!userDAO.isExist(username)) {
            userLoginLogDAO.recordLoginLog(username,pwd,0, Tool.getTime(),ip,system_version,system_model,device_brand,app_version);
            return QRY_RESULT_EMPTY;
        }
        if (userDAO.queryPwdByUsername(username).equals(Tool.getMd5FromString(pwd))) {
            userLoginLogDAO.recordLoginLog(username,pwd,1, Tool.getTime(),ip,system_version,system_model,device_brand,app_version);
            return String.valueOf(userDAO.queryIdByUserName(username));
        }
        userLoginLogDAO.recordLoginLog(username,pwd,0, Tool.getTime(),ip,system_version,system_model,device_brand,app_version);
        return FAIL;
    }

    /**
     * 查询已删除的列表
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryDeleted();
        if (arrayList.size() != 0) {
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询已锁定的列表
     * @return
     */
    public String queryLocked() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryLocked();
        if (arrayList.size() != 0) {
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询细节
     * @param id
     * @return
     */
    public String queryDetail(long id) {
        if (!userDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(userDAO.queryDetail(id));
    }
}

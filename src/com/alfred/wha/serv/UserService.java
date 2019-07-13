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
    public String register(String username,String pwd,String nick_name,String email,String motto) {
        if (userDAO.isExist(username)) {
            return DUPLICATE;
        }
        if (userDAO.isExistEmailAddress(email)) {
            return DUPLICATE;
        }
        if (userDAO.add(username, Tool.getMd5FromString(pwd),nick_name,email,motto)) {
            Tool.storeImage(Tool.getImage("default_icon",path_icon),username,path_icon);
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
                LogDao.recordUserLog(id,LOG_OPERATE_DELETE,operator,operator_type,"删除用户");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public String recover(long id,long operator,int operator_type) {
        if (userDAO.isExist(id)) {
            if (userDAO.recover(id)) {
                LogDao.recordUserLog(id,LOG_OPERATE_RECOVER,operator,operator_type,"恢复用户");
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
                LogDao.recordUserLog(id,LOG_OPERATE_LOCK,operator,operator_type,"锁定用户");
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
    public String changeNickNameAndMotto(long id,long operator,int operator_type,String nick_name,String email,String motto) {
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
     * 修改密码
     * @param user_name
     * @param new_pwd
     * @return
     */
    public String changePwdByUserName(String user_name,int operator_type,String new_pwd) {
        if (userDAO.queryByUserName(user_name).size() == 0) {
            return NO_SUCH_RECORD;
        }
        long id = userDAO.queryIdByUserName(user_name);
        if (userDAO.updatePwdByUsername(user_name,new_pwd)) {
            LogDao.recordUserLog(id,LOG_OPERATE_CHANGE_PWD,id,operator_type,"新昵称:" + Tool.getMd5FromString(new_pwd));
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 修改密码
     * @param user_name
     * @param new_pwd
     * @return
     */
    public String changePwdByUserNameAndEmail(String user_name,String email_address,int operator_type,String new_pwd) {
        if (userDAO.queryByUserName(user_name).size() == 0) {
            return NO_SUCH_RECORD;
        }
        if (userDAO.queryByUserNameAndEmail(user_name,email_address).size() == 0) {
            return NOT_MATCH;
        }
        long id = userDAO.queryIdByUserName(user_name);
        if (userDAO.updatePwdByUsername(user_name,new_pwd)) {
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
    public String unlock(long id,long operator,int operator_type) {
        if (userDAO.isExist(id)) {
            if (userDAO.unlock(id)) {
                LogDao.recordUserLog(id,LOG_OPERATE_UNLOCK,operator,operator_type,"解锁用户");
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
            return String.valueOf(userDAO.queryIdByUserName(username)) + "|" + String.valueOf(userDAO.querytypeByUserName(username));
        }
        userLoginLogDAO.recordLoginLog(username,pwd,0, Tool.getTime(),ip,system_version,system_model,device_brand,app_version);
        return FAIL;
    }

    /**
     * 查询已删除的用户列表
     * @return
     */
    public String queryDeleted(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryDeleted(page_no,length);
        if (arrayList.size() != 0) {
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询已删除的用户列表
     * @return
     */
    public String queryNormal(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryNormal(page_no,length);
        if (arrayList.size() != 0) {
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询已锁定的列表
     * @return
     */
    public String queryLocked(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryLocked(page_no,length);
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
        return Tool.transformFromCollection(userDAO.queryDetail(id,1,1));
    }

    /**
     * 用于用户自己查询详情
     * @param id
     * @return
     */
    public String queryDetailByUserSelf(long id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        if (!userDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        HashMap<String,Object> hashMap = userDAO.queryDetail(id,1,1).get(0);
        String user_name = String.valueOf(hashMap.get("user_name"));
        String image = Tool.getImage(user_name,path_icon);
        hashMap.put("icon",image);
        arrayList.add(hashMap);
        return Tool.transformFromCollection(arrayList);
    }


    public String changeIcon(long id,String imgStr) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = userDAO.queryDetail(id,1,1);
        String user_name = String.valueOf(arrayList.get(0).get("user_name"));
        Tool.storeImage(imgStr,user_name,path_icon);
        return SUCCESS;
    }
}

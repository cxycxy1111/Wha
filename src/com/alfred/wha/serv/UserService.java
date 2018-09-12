package com.alfred.wha.serv;

import com.alfred.wha.dao.UserDAO;
import com.alfred.wha.dao.UserLoginLogDAO;
import com.alfred.wha.util.MethodTool;
import com.alfred.wha.util.Ref;

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
        if (userDAO.add(username, MethodTool.getMd5FromString(pwd),email)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 登录检查
     * @param username
     * @param pwd
     * @return
     */
    public String loginCheck(String username,String pwd,String ip,String system_version,String system_model,String device_brand,String app_version) {
        if (!userDAO.isExist(username)) {
            userLoginLogDAO.recordLoginLog(username,pwd,0,MethodTool.getTime(),ip,system_version,system_model,device_brand,app_version);
            return NO_SUCH_RECORD;
        }
        if (userDAO.queryPwd(username).equals(MethodTool.getMd5FromString(pwd))) {
            userLoginLogDAO.recordLoginLog(username,pwd,1,MethodTool.getTime(),ip,system_version,system_model,device_brand,app_version);
            return MethodTool.transformFromCollection(userDAO.queryIdByUserName(username));
        }
        userLoginLogDAO.recordLoginLog(username,pwd,0,MethodTool.getTime(),ip,system_version,system_model,device_brand,app_version);
        return FAIL;
    }
}

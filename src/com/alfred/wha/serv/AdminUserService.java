package com.alfred.wha.serv;

import com.alfred.wha.dao.AdminUserDAO;
import com.alfred.wha.dao.LogDao;
import com.alfred.wha.util.Tool;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminUserService extends Service{

    private AdminUserDAO adminUserDAO;

    public AdminUserService() {
        super();
        adminUserDAO = new AdminUserDAO();
    }

    /**
     * 手动新增
     * @param company_id
     * @param username
     * @param pwd
     * @param type
     * @param email
     * @param creator
     * @return
     */
    public String add(long company_id,String username,String pwd,int type,String email,long creator) {
        if (adminUserDAO.isExist(username)) {
            return DUPLICATE;
        }
        if (adminUserDAO.add(company_id,username,pwd,type,email,creator)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 注册新增
     * @param company_id
     * @param username
     * @param pwd
     * @param type
     * @param email
     * @param creator
     * @return
     */
    public String register(long company_id,String username,String pwd,int type,String email,int creator) {
        if (adminUserDAO.isExist(username)) {
            return DUPLICATE;
        }
        if (adminUserDAO.add(company_id,username,pwd,type,email,creator)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    public String delete(long id,long operator,int operator_type) {
        if (adminUserDAO.isExist(id)) {
            if (adminUserDAO.delete(id)) {
                LogDao.recordAdminUserLog(id,LOG_OPERATE_DELETE,operator,operator_type,"");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 登录验证
     * @param user_name
     * @param pwd
     * @return
     */
    public String loginCheck(String user_name,String pwd) {
        if (!adminUserDAO.isExist(user_name)) {
            return QRY_RESULT_EMPTY;
        }
        if (Tool.getMd5FromString(pwd).equalsIgnoreCase(adminUserDAO.queryPwdByUserName(user_name))) {
            String id = adminUserDAO.queryIdByUserName(user_name);
            int type = adminUserDAO.queryTypeById(Long.parseLong(id));
            return id +"|" + String.valueOf(type);
        }
        return NOT_MATCH;
    }



    /**
     * 锁定
     * @param id
     * @return
     */
    public String lock(long id,long operator,int operator_type) {
        if (adminUserDAO.isDel(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (adminUserDAO.lock(id)) {
            LogDao.recordAdminUserLog(id,LOG_OPERATE_LOCK,operator,operator_type,"");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 解锁
     * @param id
     * @return
     */
    public String unlock(long id,long operator,int operator_type) {
        if (adminUserDAO.isDel(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (adminUserDAO.unlock(id)) {
            LogDao.recordAdminUserLog(id,LOG_OPERATE_UNLOCK,operator,operator_type,"");
            return SUCCESS;
        }
        return FAIL;
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
        if (adminUserDAO.updateInfo(id,nick_name,email,motto)){
            String log = "新头像:" + nick_name + ",新签名:" + motto + ",新email:" + email;
            LogDao.recordAdminUserLog(id,LOG_OPERATE_EDIT,id,operator_type,log);
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
    public String changePwd(long id,long operator,int operator_type,String new_pwd) {
        if (adminUserDAO.updatePwd(id,new_pwd)) {
            LogDao.recordAdminUserLog(id,LOG_OPERATE_CHANGE_PWD,operator,operator_type,"新密码:" + Tool.getMd5FromString(new_pwd));
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更改头像
     * @param id
     * @param inStream
     * @return
     * @throws IOException
     */
    public String changeIcon(long id,long operator,int operator_type,InputStream inStream) throws IOException {
        //写入新头像
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();

        byte[] data = outStream.toByteArray();

        String pic_name = Tool.getMd5FromString(String.valueOf(id) + "_" + Tool.getTime());
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("/usr/local/wha/admin_user/nickname/" + pic_name + ".jpeg");
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        //写入数据
        fileOutputStream.write(data);
        //关闭输出流
        fileOutputStream.close();
        adminUserDAO.changeIcon(id,pic_name);
        LogDao.recordAdminUserLog(id,LOG_OPERATE_CHANGE_ICON,operator,operator_type,"");
        return SUCCESS;
    }

    /**
     * 查询已通过的列表
     * @return
     */
    public String queryNormal() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = adminUserDAO.queryNormal();
        if (arrayList.size() != 0) {
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询已删除的列表
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = adminUserDAO.queryDeleted();
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
        arrayList = adminUserDAO.queryLocked();
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
        if (!adminUserDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(adminUserDAO.queryDetail(id));
    }

}

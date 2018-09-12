package com.alfred.wha.serv;

import com.alfred.wha.dao.AdminUserDAO;
import com.alfred.wha.util.MethodTool;
import com.alfred.wha.util.Ref;

import java.io.*;

public class AdminUserService extends Service{

    private AdminUserDAO adminUserDAO;

    public AdminUserService() {
        adminUserDAO = new AdminUserDAO();
    }

    /**
     * 注册验证
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
     * 登录验证
     * @param user_name
     * @param pwd
     * @return
     */
    public String login(String user_name,String pwd) {
        if (!adminUserDAO.isExist(user_name)) {
            return NO_SUCH_RECORD;
        }
        if (MethodTool.getMd5FromString(pwd).equalsIgnoreCase(adminUserDAO.queryPwdByUserName(user_name))) {
            return MethodTool.getMd5FromString(id_prefix+ adminUserDAO.queryIdByUserName(user_name) + datasuffix);
        }
        return NOT_MATCH;
    }

    /**
     * 新增
     * @param company_id
     * @param username
     * @param pwd
     * @param type
     * @param email
     * @param creator
     * @return
     */
    public String add(long company_id,String username,String pwd,int type,String email,int creator) {
        if (adminUserDAO.isExist(username)) {
            return DUPLICATE;
        }
        if (adminUserDAO.add(company_id,username,pwd,type,email,creator)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 检查链
     * @param checkType
     * @param id
     * @return
     */
    public String checkChain(int checkType,long id) {
        if (adminUserDAO.isDel(id)) {
            return NO_SUCH_RECORD;
        }
        String str = "";
        switch (checkType) {
            case 1:
                str = lock(id);
                break;
            case 2:
                str = unlock(id);
                break;
            default:;
        }
        return str;
    }

    /**
     * 锁定
     * @param id
     * @return
     */
    private String lock(long id) {

        if (adminUserDAO.lock(id)) {
            return SUCCESS;
        }
        return FAIL;
    }


    /**
     * 解锁
     * @param id
     * @return
     */
    private String unlock(long id) {
        if (adminUserDAO.unlock(id)) {
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
    public String changeIcon(long id,InputStream inStream) throws IOException {
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

        String pic_name = MethodTool.getMd5FromString(String.valueOf(id) + "_" + MethodTool.getTime());
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("/usr/local/wha/admin_user/nickname/" + pic_name + ".jpeg");
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        //写入数据
        fileOutputStream.write(data);
        //关闭输出流
        fileOutputStream.close();
        adminUserDAO.changeIcon(id,pic_name);
        return SUCCESS;
    }

}

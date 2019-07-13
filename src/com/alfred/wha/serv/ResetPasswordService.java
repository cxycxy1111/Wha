package com.alfred.wha.serv;

import com.alfred.wha.dao.ResetPasswordDAO;
import com.alfred.wha.dao.UserDAO;
import com.alfred.wha.util.Tool;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class ResetPasswordService extends Service{

    private ResetPasswordDAO resetPasswordDAO;
    private UserDAO userDAO;

    public ResetPasswordService() {
        resetPasswordDAO = new ResetPasswordDAO();
        userDAO = new UserDAO();
    }


    public String resetPasswordCodeInit(String email_address) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        if (userDAO.queryUserNamesByEmailAddress(email_address).size() == 0) {
            return NO_SUCH_RECORD;
        }
        list = resetPasswordDAO.queryValidList(email_address);
        //使历史记录失效
        if (list.size()>0) {
            resetPasswordDAO.invalidate(Tool.getLongFromArrayList(list,"id"));
        }
        String code = "";
        double d = Math.random();
        int temp = (int) (1000000*d);
        if (temp<10) {
            code = "00000" + temp;
        }else  if (temp>=10 && temp <100) {
            code = "0000" + temp;
        }else  if (temp>=100 && temp <1000) {
            code = "000" + temp;
        }else  if (temp>=1000 && temp <10000) {
            code = "00" + temp;
        }else  if (temp>=10000 && temp <100000) {
            code = "0" + temp;
        }else  if (temp>=100000 && temp <1000000) {
            code = String.valueOf(temp);
        }
        resetPasswordDAO.add(email_address,code);
        String user_name = userDAO.queryUserNameByEmailAddress(email_address);
        setEmail(email_address,"您好，您本次用于重置今日简讯账号的验证码是" + code + "，您的账户名是："+ user_name + "，请勿将账户名与验证码告诉其他人。");
        return SUCCESS;
    }

    public String matchCode(String email_address,String code) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = resetPasswordDAO.queryValidList(email_address);
        //过滤没有发送过的邮箱地址
        if (list.size() == 0) {
            return FAIL;
        }
        //超时1小时失效
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_sendCode = null;
        Date date_match = new Date();
        try {
            date_sendCode = dateFormat.parse(String.valueOf(list.get(0).get("create_time")));
            long diff = date_match.getTime() -date_sendCode.getTime();
            if (diff > 3600000) {
                return FAIL;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int total_match_times = Tool.getIntegerFromArrayList(list,"total_match_times");
        long id = Tool.getLongFromArrayList(list,"id");
        String c = String.valueOf(list.get(0).get("reset_code"));//开始匹配
        resetPasswordDAO.updateTotalMatchTimes(email_address);//增加匹配次数记录
        if (c.equals(code)) {
            //匹配成功即失效
            resetPasswordDAO.invalidate(id);
            return SUCCESS;
        }else {
            //连续匹配三次错误即失效
            if (total_match_times > 2) {
                resetPasswordDAO.invalidate(id);
                return FAIL;
            }
            return NOT_MATCH;
        }
    }

    private void setEmail(String email_address,String content) {
        //发件人账户名
        String senderAccount = "jinrijianxunserv@163.com";
        //发件人账户密码
        String senderPassword = "cxycxy11";
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = null;
        try {
            msg = getMimeMessage(session,email_address,content);
            //4、根据session对象获取邮件传输对象Transport
            Transport transport = session.getTransport();
            //设置发件人的账户名和密码
            transport.connect(senderAccount, senderPassword);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg,msg.getAllRecipients());

            //如果只想发送给指定的人，可以如下写法
            //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

            //5、关闭邮件连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     */
    private MimeMessage getMimeMessage(Session session, String email_address, String content) throws Exception{

        String senderAddress = "jinrijianxunserv@163.com";
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(email_address));
        //设置邮件主题
        msg.setSubject("重置您在今日简讯上的登录密码","UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

}

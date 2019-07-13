package com.alfred.wha.util;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tool {

    public static final String default_icon = "iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAYAAABV7bNHAAAH7UlEQVR4nO1cC2xUVRr+7ryfnZaW\\ntlBQHhGnZVhYqEZNWSlWWljLLlDtQqmuumrjA5R1ZbNxdVHXhGw0JEYJJgsmJPtQ4sIKCCK0wWWX\\nKK5ZBUFZTXCBPmgppbTznjHfqVMRcS+de890iPdLprm9M/Ofe77z///5H6dVFi5oTJ492wsD30ZO\\njheWzz8/hr6+foOei8DtdsFis9kQDIay7uGyAeTG9H0nQQ0GQSowCFKBQZAKDIJUYBCkAoMgFViG\\n+wGSySQS8QRi8RgSiaS4ZzIpsFgsMJlMUBRlWJ9v2AiKxWIIhcKwWMwoKMjH6FHF8OZ4SBmY+rS2\\ndqCz87T4nMNhF4QNBzI+KjWmvz+I0SXFqJxVgcrZFSgr8yM/P09ErkQkEhHkHDp4GM0t/0DLnn1o\\nbWuDy+XKuEYp115TlezpOZuRwaLRGGKxKBYvXohly5swenSxuM978XhCkCceSlFgNpsHteb48RNY\\ns2YdXv3rZths1oxpk8+XA3NJyYTfhcNh6YNFIlG43U488+zjWL68CS6XExyXJpRIfE0OUn4pkRDv\\n8ZWb60NNzU0oKh6Jf/7rPYRDEUGgbAjTlj7KV5rjcNjwwourMevGCoRCoW8QogaanElRsGRJHQoL\\nC/DQg79GNBrNiCZJ3+YHdqYEnn76N4KcYDA4JHIG5SST4rtVVbPw5KrHvqV1siCdoP7+PjQ03IpF\\ndfMRCgU1y6P2/ax+Ierrf5qROpZUgrjK9B+LG+qQTHLFtcuk1vDVsPRWeL1uMYZMSCWIJjF33s0o\\n9U8SMY9eoBYFAqWorpktvdgnkaCkcKKchBwomFtTBbNJrpeQJj0Wi6O4qBCByX4R5+gvP4bAFD8K\\niwrEtSzIJWhUEfLyckUQqDfi8TjyRuShuLhIXF92BNF5+nJzROQrYzumTLvNJlozMh21VANWoIif\\n9EdS5GcgL5NGEEsVPT09GEhj9J8IyaFsZv6KIm+dpUlmGaO1tR1MhHmtN5iLdXf3oK2tHVarvJRD\\nGkGcQFv7KRw6dAQWi1V3+QwhDh48jI6OLqmJqzSCaALRSBRvbn9b1hDYvu1txOPytnjIdtJOpwNv\\n7WrG0aP/hd1u002u3W7HkSOfYvfuvXA4nLrJvRikEkTV7+rsxiuv/AUmk1mXXYcyuAFsWP8ndHef\\ngdl8mUbSKfCEBCuB27bthMPh0CyPMrZs3o5Nm94QsmVDOkFccQZ1j/7yCTTv2Qun05mWJvE7/O6u\\nXc1YuXIVKOKyjoPOB3ecUCiCFSt+K5w2S5mpAv2lgP6Lfmfr1h341aNPiApjpurSGSvac7VZl2be\\ntGhRLR55pAljxpaI99SK9seO/Q9rnn8Jr/9tmwgZZMY954NF+4x2NXBe26eoaCRqa6tFOaSkZJRI\\nalNaxQiZQeDx4yexc8cebN26Ex0dnaLQn8m2T0YICocjSCTiYmI0k9QEU41DmhurjixbeDweMoje\\n3j50dJzCmTNnBVkXNg4j4QiisZiI0EmqLNJIkDRdJQHsPEwO+HHnzxfj8OFPsWHDn8VkOClO2OOx\\nCI3iAnV1nUaSBX5lII8bMDEzrFb3oExm7axDX3/9NZj/kxr8fcsOHDjwgfis1ap/tA5ZnVWWRFmG\\neGzlSixY8GO43QOTnD5jGp5a9QecPNkmtmiSlfI3aukCZTIpXbbsXtz/wN3wej2ov20Btr/5Fp79\\n/Rq0t58SJqg3dG8c0r9MnDgOa9c9h+o5A+VW7jp0zmwxz6muRGdnFz45clSYH7Xku0yE2sVnCwXD\\nKAv4sXr1k2i8vV4cbqBMxaQgEChDZWUFPvzPIXzxxQlRf9ILNG3dfBAnSfX3+yfij+tfwNixY0TR\\n/kJwy+bE9+8/gI0bX0NL8zvo7T0Hs9kyqEXsgMSicUHelB+UYUlDHebNq4LP57uoTMZH7OXfd+/D\\nePfdD+DxuHUp0unqpLmiPKWxceNaXO2/6qITSYFkMiImER999DFaWvbh3+9/iFMdnYgn4uKUR1mp\\nHzNnXocbbrgWLrdrUAu/C8z7aLqNS5vw2WfHxOprhW4EcbXolNe9/LzofP4/ci4kSpxFNpmEAw72\\nB8WO53A6YLV+veVfakmVmkSnvbShSZCptQxCgnSJpM+d68OiulpBDp3ppSLlY0ioiI6tFtjsdhE0\\n8h5fQ6k38/Pl5T/E3b9oFL5QD2gmiNHxhInjsGLF/UKL0rX91IkOrT13Et3UdAdmTJ+qS7NSM0GM\\nde65p1G0X3g93KBpeb1ePPDgXVAU7Y5aE0EkZNz4K3DLLdVZQU4K4UgYP7qxApPL/NAawmgiiIPf\\nNHsmRozIk9rdHCp4KJS7Wu38GnE2SQvSJoh+gtEwtScT53SGCpranOrZGFlYoKnzmjZBdIb+0kkI\\nTCkV19kGmvz48VegvHyqiNgzThAfoHzGNBHwyT6jkw4GtFoRia2W50ubIAZh06dP0Wk6ckCSpk4L\\nCFeQrhtIK5vninDQceOvFL9n4sRpOiApY0pKkJubg66u7rTKtGkRxIFpWlaLVfTGs2kHOx9MYeig\\nnS4Xkp2n05KRVi5GbWU/ismpSfIJL61gQsxiHLf7oVYe064ochya2YkTJzMwRe2gaaVbltVUUZRV\\n5swmGH8vpgKDIBUYBKnAIEgFBkEqMAhSgUGQCgyCVGAQpAKDIBUYBKnAIEgFBkEqMGVrsSsbQG4s\\n+fkjhu3/YmQ7fL4cfAlJ342HxK54zQAAAABJRU5ErkJggg==";

    public static String getMd5FromString(String string) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = string.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从集合中取出值转为int
     *
     * @param list
     * @param key
     * @return
     */
    public static int getIntegerFromArrayList(ArrayList<HashMap<String, Object>> list, String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = list.get(0);
        Object o = map.get(key);
        String s = String.valueOf(o);
        System.out.println("getIntegerFromArrayList:" + s);
        int i = Integer.parseInt(s);
        return i;
    }

    /**
     * 从集合中取出值转为long
     *
     * @param list
     * @param key
     * @return
     */
    public static long getLongFromArrayList(ArrayList<HashMap<String, Object>> list, String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = list.get(0);
        Object o = map.get(key);
        String s = String.valueOf(o);
        System.out.println("getLongFromArrayList:" + s);
        return Long.parseLong(s);
    }

    /**
     * 从集合中取出值转化为boolean
     *
     * @param list
     * @param key
     * @return
     */
    public static boolean getBooleanFromArrayList(ArrayList<HashMap<String, Object>> list, String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = list.get(0);
        Object o = map.get(key);
        String s = String.valueOf(o);
        boolean b = Boolean.valueOf(s);
        System.out.println("getBooleanFromArrayList:" + b);
        return b;
    }

    /**
     * 从集合中取出值转化为boolean
     *
     * @param list
     * @param key
     * @return
     */
    public static String getStringFromArrayList(ArrayList<HashMap<String, Object>> list, String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = list.get(0);
        Object o = map.get(key);
        String s = String.valueOf(o);
        System.out.println("getStringFromArrayList:" + s);
        return s;
    }

    /**
     * 从集合中取出值转化为String
     *
     * @param list
     * @param key
     * @return
     */
    public static String toString(ArrayList<HashMap<String, Object>> list, String key) {
        HashMap<String, Object> map = list.get(0);
        Object o = map.get(key);
        return String.valueOf(o);
    }

    /**
     * quickReturn Quick Return 快速返回
     *
     * @param string
     * @return
     */
    public static String quickReturn(String string) {
        return transformFromString(string);
    }

    /**
     * tfc transform form Collection 将集合转为json字符串
     *
     * @param collection
     * @return
     */
    public static String transformFromCollection(Collection<?> collection) {
        if (collection != null) {
            JSONArray ja = new JSONArray(collection);
            System.out.println(ja.toString());
            return ja.toString();
        } else {
            return printInternalError();
        }
    }

    /**
     * tfs transform form String，将字符串转为json字符串
     * 从字符串中转化
     *
     * @return
     */
    private static String transformFromString(String string) {
        if (string != null && !Objects.equals(string, "")) {
            JSONObject js = null;
            try {
                js = new JSONObject(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return js.toString();
        } else {
            return printInternalError();
        }
    }

    /**
     * tfo transform form Object 将对象转为json字符串
     * 从对象中转化
     *
     * @param object
     * @return
     */
    public static String transformFromObject(Object object) {
        if (object != null) {
            JSONObject jo = new JSONObject(object);
            return jo.toString();
        } else {
            return printInternalError();
        }
    }

    /**
     * 将session值转为long
     *
     * @param session
     * @param key
     * @return
     */
    public static long transformSessionValueToLong(HttpSession session, String key) {
        Object o = session.getAttribute(key);
        return Long.parseLong(String.valueOf(o));
    }

    /**
     * 将session值转为int
     *
     * @param session
     * @param key
     * @return
     */
    public static Integer transformSessionValueToInteger(HttpSession session, String key) {
        Object o = session.getAttribute(key);
        return Integer.parseInt(String.valueOf(o));
    }

    /**
     * 内部错误
     *
     * @return
     */
    private static String printInternalError() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\"status\":\"Interal Error\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j.toString();
    }

    /**
     * 将请求参数解释为long
     *
     * @param req
     * @param param
     * @return
     */
    public static long requestToLong(HttpServletRequest req, String param) {
        String s = req.getParameter(param);
        if (s == null) {
            return 0;
        }
        return Long.valueOf(req.getParameter(param));
    }

    /**
     * 将请求参数解释为int
     *
     * @param req
     * @param param
     * @return
     */
    public static int requestToInt(HttpServletRequest req, String param) {
        String s = req.getParameter(param);
        if (s == null) {
            return 0;
        }
        return Integer.parseInt(s);
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static boolean executeSql(String sql) {
        SQLHelper helper = new SQLHelper();
        boolean b = false;
        try {
            b = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static void writeFiles(long id,String path,String content) {
        InputStream inputStream = null;
        inputStream = new ByteArrayInputStream(content.getBytes());
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path+id);
            /**
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
             **/
            os = new FileOutputStream(tempFile, false);
            // 开始写入
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFiles(long id,String path) {
        String re = "";
        //得到数据文件
        File file = new File(path+id);
        StringBuilder builder = new StringBuilder();
        try {
            //建立数据通道
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "utf-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static String publicShareReadFiles(long id,String path) {
        String re = "";
        //得到数据文件
        File file = new File(path+id);
        StringBuilder builder = new StringBuilder();
        try {
            //建立数据通道
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "utf-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append("<p class=\"p1\">" + line + "\n" + "</p>");
            }
            br.close();
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static String readUtilFiles(String path) {
        String re = "";
        //得到数据文件
        File file = new File(path);
        StringBuilder builder = new StringBuilder();
        try {
            //建立数据通道
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "utf-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 转储图标
     *@param imgStr 图片字符串
     *@param filename 图片名
     *
     */
    public static void storeImage(String imgStr, String filename,String path) {
        if (imgStr != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                byte[] b = decoder.decodeBuffer(imgStr);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                String imgFilePath = path + filename + ".png";
                OutputStream out = new FileOutputStream(imgFilePath,false);
                out.write(b);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图标
     * @param icon_name 文件名
     * @return
     */
    public static String getImage(String icon_name,String path) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(path + icon_name + ".png");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

}

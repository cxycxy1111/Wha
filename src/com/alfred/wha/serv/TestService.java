package com.alfred.wha.serv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestService {


    private static String url = "https://blog.csdn.net";
    private static String blogName = "guoxiaolongonly";



    /**
     * 获取文章内容
     * @param detailurl
     */
    public static void getArticleFromUrl(String fileName,String detailurl) {
        try {
            Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0").timeout(3000).post();
            Elements titles = document.getElementsByClass("ftitle");
            Elements times = document.getElementsByAttributeValue("align","center");
            Element time = times.get(5);

            Element title = titles.get(0);
            Element element = document.getElementById("zoom");
            saveArticle( title.text() + "\n" + time.text() + "\n"+  element.text() + "\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void threadSave(String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        saveArticle(content);
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 保存文章到本地
     * @param content
     */
    private static void saveArticle(String content) {
        String path = "/Users/dengweixiong/Desktop/txt/text2.txt";//保存到本地的路径和文件名
        /**
        String str = content.replaceAll("\\s","\\n");
        for (int i = 0;i<5;i++) {
            if (str.contains("\n\n")) {
                str = str.replaceAll("\\n\\n","\\n");
            }
        }
         **/
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

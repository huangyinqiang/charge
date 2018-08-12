package net.inconnection.charge.push.utils;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUrlConnectionUtil {
    public HttpUrlConnectionUtil() {
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;

        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            Iterator var8 = map.keySet().iterator();

            while(var8.hasNext()) {
                String key = (String)var8.next();
                System.out.println(key + "--->" + map.get(key));
            }

            String line;
            for(in = new BufferedReader(new InputStreamReader(connection.getInputStream())); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var18) {
            System.out.println("发送GET请求出现异常！" + var18);
            var18.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var17) {
                var17.printStackTrace();
            }

        }

        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();

            String line;
            for(in = new BufferedReader(new InputStreamReader(conn.getInputStream())); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var16) {
            System.out.println("发送 POST 请求出现异常！" + var16);
            var16.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return result;
    }

    public static String connect(String connectUrl) throws HttpContentionBussException {
        String result = "";

        URL url;
        try {
            url = new URL(connectUrl);
        } catch (MalformedURLException var11) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "地址创建错误!", (String)null);
        }

        HttpURLConnection connection = null;
        boolean var4 = false;

        int code;
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            code = connection.getResponseCode();
        } catch (IOException var10) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "连接无法创建!", (String)null);
        }

        if (code == 404) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证无效，找不到此次认证的会话信息！", (String)null);
        } else if (code == 500) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证服务器发生内部错误！", (String)null);
        } else if (code != 200) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "发生其它错误，认证服务器返回 " + code, (String)null);
        } else {
            Object var6 = null;

            byte[] response;
            try {
                InputStream is = connection.getInputStream();
                response = new byte[is.available()];
                is.read(response);
                is.close();
            } catch (IOException var9) {
                throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "数据读取失败", (String)null);
            }

            if (response != null && response.length != 0) {
                try {
                    result = new String(response, "utf-8");
                    return result;
                } catch (UnsupportedEncodingException var8) {
                    throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "数据格式化有误!", (String)null);
                }
            } else {
                throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证无效，未任何任何信息！", (String)null);
            }
        }
    }

    public static String connectTimeout(String connectUrl, int timeout) throws HttpContentionBussException {
        String result = "";

        URL url;
        try {
            url = new URL(connectUrl);
        } catch (MalformedURLException var12) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "地址创建错误!", (String)null);
        }

        HttpURLConnection connection = null;
        boolean var5 = false;

        int code;
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.connect();
            code = connection.getResponseCode();
        } catch (IOException var11) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "连接无法创建!", (String)null);
        }

        if (code == 404) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证无效，找不到此次认证的会话信息！", (String)null);
        } else if (code == 500) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证服务器发生内部错误！", (String)null);
        } else if (code != 200) {
            throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "发生其它错误，认证服务器返回 " + code, (String)null);
        } else {
            Object var7 = null;

            byte[] response;
            try {
                InputStream is = connection.getInputStream();
                response = new byte[is.available()];
                is.read(response);
                is.close();
            } catch (IOException var10) {
                throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "数据读取失败", (String)null);
            }

            if (response != null && response.length != 0) {
                try {
                    result = new String(response, "utf-8");
                    return result;
                } catch (UnsupportedEncodingException var9) {
                    throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "数据格式化有误!", (String)null);
                }
            } else {
                throw new HttpContentionBussException(HttpUrlConnectionUtil.class, 7, "", "认证无效，未任何任何信息！", (String)null);
            }
        }
    }

    public static void main(String[] args) {
        String sendGet = null;
        String str = "http://cloud.nspay.net/weixin/tuser/sendCustMsg?openId=omPt_1CLfrL1r-Lia_tI6UC8ZrXs&msg=" + URLEncoder.encode("您的位置信息泄露，请及时检查");

        try {
            sendGet = connectTimeout(str, 5000);
        } catch (HttpContentionBussException var4) {
            System.out.println(var4);
        }

        System.out.println(sendGet);
    }
}


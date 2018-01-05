package net.chenlin.dp.common.utils;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;

/**
 * Http请求帮助类
 * Andy.Wang 2018-1-5
 */
public final class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String requestGet(String urlWithParams) throws Exception {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        // HttpGet httpget = new HttpGet("http://www.baidu.com/");
        HttpGet httpget = new HttpGet(urlWithParams);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
                .setSocketTimeout(5000).build();
        httpget.setConfig(requestConfig);

        CloseableHttpResponse response = httpclient.execute(httpget);
        System.out.println("StatusCode -> " + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httpget.releaseConnection();

        return jsonStr;
    }

    public static String requestPost(String url, List<NameValuePair> params) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();

        return jsonStr;
    }

    public static String requestPost(String url, String str) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        StringEntity strEntity = new StringEntity(str);
        strEntity.setContentEncoding("utf-8");
        strEntity.setContentType("application/json;charset=utf-8");
        httppost.setEntity(strEntity);

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();

        return jsonStr;
    }

    /**
     * 发起https请求并获取结果（忽略客户端验证SSL证书）
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET/POST）
     * @param data           提交的数据
     * @return 响应结果
     */
    public static String requestIgnoreSSL(String requestUrl, String requestMethod, String data) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpsRequest(requestUrl, requestMethod, data, buffer, ssf);
        } catch (ConnectException ce) {
            //System.out.println("API server connection error." + ce.getMessage());
            logger.error("API server connection error.", ce);
        } catch (Exception e) {
            //System.out.println("Https request error." + e.getMessage());
            logger.error("Https request error.", e);
        }
        return buffer.toString();
    }

    /**
     * 发起https请求并获取结果（客户端单向验证SSL证书）
     * @param requestUrl 请求URL
     * @param requestMethod 请求方式（GET/POST）
     * @param data 提交的数据
     * @param keyStorePath 证书文件路径
     * @param keyPassword 证书秘钥
     * @return 响应结果
     */
    public static String requestOneWaySSL(String requestUrl, String requestMethod, String data, String keyStorePath, String keyPassword) {
        StringBuffer buffer = new StringBuffer();
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File(keyStorePath));
            try {
                trustStore.load(instream, keyPassword.toCharArray());
            } finally {
                instream.close();
            }
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();

            httpsRequest(requestUrl, requestMethod, data, buffer, socketFactory);
        } catch (ConnectException ce) {
            //System.out.println("API server connection error." + ce.getMessage());
            logger.error("API server connection error.", ce);
        } catch (Exception e) {
            //System.out.println("Https request error." + e.getMessage());
            logger.error("Https request error.", e);
        }
        return buffer.toString();
    }

    //发起https请求
    private static void httpsRequest(String requestUrl, String requestMethod, String data, StringBuffer buffer, SSLSocketFactory socketFactory) throws IOException {
        URL url = new URL(requestUrl);
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
        httpUrlConn.setSSLSocketFactory(socketFactory);

        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);
        httpUrlConn.setUseCaches(false);

        // 设置请求方式（GET/POST）
        httpUrlConn.setRequestMethod(requestMethod);

        if ("GET".equalsIgnoreCase(requestMethod))
            httpUrlConn.connect();

        // 当有数据需要提交时
        if (null != data) {
            OutputStream outputStream = httpUrlConn.getOutputStream();
            // 注意编码格式，防止中文乱码
            outputStream.write(data.getBytes("utf-8"));
            outputStream.close();
        }

        // 将返回的输入流转换成字符串
        InputStream inputStream = httpUrlConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String strRead = null;
        while ((strRead = bufferedReader.readLine()) != null) {
            buffer.append(strRead);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        httpUrlConn.disconnect();
    }

    public static String postIgnoreSSL(String requestUrl, String data) {
        return requestIgnoreSSL(requestUrl, "POST", data);
    }

    public static String getIgnoreSSL(String requestUrl, String data) {
        return requestIgnoreSSL(requestUrl, "GET", data);
    }

    public static String postRequestSSL(String requestUrl, String data, String keyStorePath, String keyPassword) {
        return requestOneWaySSL(requestUrl, "POST", data, keyStorePath, keyPassword);
    }

    public static String getRequestSSL(String requestUrl, String data, String keyStorePath, String keyPassword) {
        return requestOneWaySSL(requestUrl, "GET", data, keyStorePath, keyPassword);
    }

	/*
    public static void main(String[] args) {
		try {
			String loginUrl = "http://localhost:8080/yours";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", "zhang"));
			params.add(new BasicNameValuePair("passwd", "123"));

			requestPost(loginUrl, params);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}

class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
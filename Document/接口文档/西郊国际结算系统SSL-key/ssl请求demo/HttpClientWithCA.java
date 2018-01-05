package whc.test.servletTest;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *  带证书认证请求处理
 * @author blue_coat
 * @date 2015-7-26
 */
public class HttpClientWithCA {

    public static final String KEYSTORE_FILE = "G:/keys/truststore.jks";
    public static final String KEYSTORE_PWD = "XjgjD208";

    public final static void main(String[] args) throws Exception {
        String result = new HttpClientWithCA().oneAuthSSL("https://xjgjapp.com:8443/app/memberSearchRequest", "", KEYSTORE_FILE, KEYSTORE_PWD);
        System.out.println("result  "+result);
    }

    public String oneAuthSSL(String url, String data, String keystore, String password) throws Exception {
        String result = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());        
        FileInputStream instream = new FileInputStream(new File(keystore)); 
        try {
            trustStore.load(instream, password.toCharArray());
        } finally {
            instream.close();
        }
        SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
        Scheme sch = new Scheme("https", socketFactory, 8443);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);
        HttpGet httpget = new HttpGet(url);
        System.out.println("executing: " + httpget.getRequestLine()); 
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response Content-length: " + entity.getContentLength());
            result = EntityUtils.toString(entity);
            entity.consumeContent();
        }
        // 关闭连接，腾出内存空间
        httpclient.getConnectionManager().shutdown();
        return result;
    }
}

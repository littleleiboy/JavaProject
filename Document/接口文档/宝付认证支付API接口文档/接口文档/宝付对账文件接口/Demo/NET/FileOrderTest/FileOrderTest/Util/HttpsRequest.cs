using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Net;
using System.IO;

namespace HttpSend
{
    class HttpsRequest
    {

        ///<summary>
        ///采用https协议访问网络
        ///</summary>
        ///<param name="URL">url地址</param>
        ///<param name="strPostdata">发送的数据</param>
        ///<returns></returns>
        public static string OpenReadWithHttps(string URL, string strPostdata)
        {
            try
            {
                ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls;
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(URL);
                request.Method = "post";
                request.Accept = "text/html, application/xhtml+xml, */*";
                request.ContentType = "application/x-www-form-urlencoded";
                byte[] buffer = Encoding.UTF8.GetBytes(strPostdata);
                request.ContentLength = buffer.Length;
                request.GetRequestStream().Write(buffer, 0, buffer.Length);
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                using (StreamReader reader = new StreamReader(response.GetResponseStream()))
                {
                    return Encoding.UTF8.GetString(Encoding.UTF8.GetBytes(reader.ReadToEnd()));
                }
            }
            catch (Exception ex)
            {
                return "[HttpsRequest.OpenReadWithHttps请求异常][" + ex.Message + "]";
            }
        }
        //请求参数字串
        public static String GetParamsStr(Dictionary<string,string> Params){

            String retult = "";
            int i = 1;
            foreach (string key in Params.Keys)
            {
                retult += key + "=" + Params[key];
                
                if(Params.Count>i){
                    retult += "&";
                }
                i++;
            }
            return retult;
        }

        public static String HtmlPost(String Url, Dictionary<string, string> Params)
        {
            String FormString = "<body onLoad=\"document.actform.submit()\">正在处理请稍候.....................<form  id=\"actform\" name=\"actform\" method=\"post\" action=\"" + Url + "\">";

            foreach (string key in Params.Keys)
            {
                FormString += "<input name=\"" + key + "\" type=\"hidden\" value='" + Params[key] + "'>";

            }
            FormString +="</form></body>";
            return FormString;        
        }


    }
}

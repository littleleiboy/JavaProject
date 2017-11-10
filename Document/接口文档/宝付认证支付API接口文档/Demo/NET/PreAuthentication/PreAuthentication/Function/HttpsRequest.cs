using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Text;
using System.IO;

namespace PreAuthentication.Function
{
    public class HttpsRequest
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
                Log.LogWrite("请求参数：" + URL + "?" + strPostdata);
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


        public static string GetParam(Dictionary<string, string> Param)
        {
            String AddString = "";
            int Start_N = 0;
            foreach (string key in Param.Keys)
            {

                AddString += key + "=" + Param[key];
                Start_N++;
                if(Start_N<Param.Count){
                
                    AddString +="&";
                
                }
            }


            return AddString;

        }
    }
}
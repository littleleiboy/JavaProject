using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Web.Script.Serialization;


namespace PreAuthentication.Function
{
    public class JXDConvert
    {
        public static String XmlToJson(String XmlString){
            XmlDocument XmlDoc = new XmlDocument();
            XmlDoc.LoadXml(XmlString);
            string json = Newtonsoft.Json.JsonConvert.SerializeXmlNode(XmlDoc);
            return json;
        }
    

    public static  Dictionary<string, object> JsonToDictionary(String jsonData){
        //实例化JavaScriptSerializer类的新实例
        JavaScriptSerializer jss = new JavaScriptSerializer();
        try{
            //将指定的 JSON 字符串转换为 Dictionary<string, object> 类型的对象            
            return jss.Deserialize<Dictionary<string, object>>(jsonData);
        }
        catch (Exception ex){
            throw new Exception(ex.Message);
            }
        }
    }
}
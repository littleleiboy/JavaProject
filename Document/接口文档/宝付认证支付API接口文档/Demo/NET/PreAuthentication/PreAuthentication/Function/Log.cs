using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Configuration;
using System.Web;

///本页面为日志记录方法
///作者：宝付（大圣）
///
namespace PreAuthentication.Function
{
    class Log
    {
        public static void LogWrite(string Messge)
        {

            string sPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["LogPath"]);
            FileStream fs = new FileStream(FilePath(sPath), FileMode.Append);
            StreamWriter sw = new StreamWriter(fs, Encoding.Default);
            sw.WriteLine(DateTime.Now.ToString("【HH:mm:ss】") + Messge);
            sw.Close();
            fs.Close();
        }
        private static string FilePath(string PathStr)
        {
            if (!Directory.Exists(PathStr))
            {
                Directory.CreateDirectory(PathStr);
            }
            return PathStr + "\\" + GetFileName();
        }

        private static string GetFileName()
        {
            return DateTime.Now.ToString("yyyyMMdd") + ".txt";
        }
    }
}

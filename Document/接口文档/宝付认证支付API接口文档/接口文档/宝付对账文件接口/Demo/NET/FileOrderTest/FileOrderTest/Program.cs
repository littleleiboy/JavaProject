using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using HttpSend;
using Org.BouncyCastle.Utilities.Encoders;
using System.IO;
using System.Text.RegularExpressions;

namespace FileOrderTest
{
    class Program
    {
        static void Main(string[] args)
        {

            String TestStr = "UEsDBBQACAgIAIKKLEkAAAAAAAAAAAAAAAAiAAAAZmlfZHpfMTAwMDAwMTc4XzIwMTYtMDktMTFfbmV3LnR4dJWYy44bRRSG90i8Ay8Q61zqVJ3qHeEisUkWRGJt97ifohfDAghECBCJEgkCUgQrckOKEshIPE1sZ96C0xdP3OVquz0jWd3tqvq/PnVu5dW9r9e3X61+eFVvLm5v/nrWXL15/cf6wc+bvy9Wv93pb1ZPfuzv1+cXm8d31/eeN1eX3/x0+ehhc7X+9s7m4snbF//W63++2jy9v77/8vL+i/ffQ2j+MGgNNQA7tM8aa5yJ3c/smgD9NYjXEHcHd1dgo5Gxm2ND0b4jsGlcx6OTI/oaeDs71A6EZgq18sy+OTpT+pn2kMjPWoIZ0vGJ2k9shkJLCTMaoq5ONfrq6cM3Fw/ePn20+v5eM7hbYH332eDp1vJ/rn/9ve4eb757uT7/sl9vu1tXW9Ut24s1C+wuePuX1cXrbht7vcvz8/Xj/97pDe53hh/eSAcoDoi1nguUgaVaVKxOMc49K1RujhWXoWLZsVnjMtibMzYfO999gK5wsbC9qScpi69vfX4DHfqg4L1CjFGdS9TGtKRALTBO1ApYc/BVOJPl3NuT0lULnHtZhjMbsVio90NdP/qSvhE2iYnC3fuYSyJ6u/Poo5Ow95JwQI9MT6fqyY4eA1Orl24hHBZkKGTqLoawK6gUD7zguKCa50wUtNRhbsPknTr29u9iQOU40W9CQbFoHk4R4z5hNG8H5MIg1ZmKaeC+BlHhQmGpbqIG7WrE9E1GNQQKclM1eEdD7NFUDSwk63qDrN6JYOT61mcf3/jkFjpLJ8TsXZMsko1p68W2aLRAbUaBd4vsIAAUQCPOOCgP29laX//w5qc3b5pH+IBivqhX7gmOWbxFoRdByzu676RdmcBROB34EhSOC+YcnEpM7UMorefakkokEITE+dQ8HcO4gZpVdhnIEm5BWV/LMZizGUNjBYrOgghAovCpDEQJg1gM+8kMrR1EzUtCtJUjedF8/B5iSO0gsXDZuM4ytMVHoq0bg7Sb6/R0O/gBA1tiKdxUZyWm+vpHX1y5pwOziKfEEHQEgYdb4axUmCUmI/AQwRY3hnAiAicIFhiUjYosgiQI0YGHvYp1jEFShmgJcjKDHzIQNL7pTjSDTxDIyujkwLS033RDFMAjqPVBjjFkUtQxiJhAiBtpVnJ2MNxdOwghtyNO24tB9W86NTPE9Cxp9cbisarscr4QchX7OAd/1vSGwaMuF/5Uo4gmQGx2yRolB2Qp2kVrMDSGqlJ0yyVrUCWxFsSdOVUtE0eRtsjJDGgEaJD1GyDnC8jW8iyQr5fgeXmmCy2XTf9TLsqyhDBHWVQlQrVMgPrK5mDcRn7ovdblNr4zFSmQnQStIQvL5SLEM2dnCF3Eai5hAVaGY1VVqY1cbyMdAQqUAFk7wtncmgOKsY7BlWWFEW2TnBlnwVqiX1ZVkGBHG58CcQ/kR4DiMLS0ID/Sl2dCi9uDaz/sKsDUXKh9ut8vd50IYB6GAfZh8i6dhcEsTOxg8qX4EAymMFaKs+3A4GS+nU05GEbh5tl+f3SkSWOghIatiZXJNJylYWxp9lLhcRpOadhCa6IbM7RVwU51loete7XDD7ETGlJEnQV/IP8xDD3Xzj9Y4NSEzAg9Q3TWLkUg+wiKJzIgpAxUwNTqyEgdgxdnbZpxWPtqfcKpDJQy8CkM3DEE0EAM6q0CWG06lYFThrECnWVwHYOi+YEKhaggGk5lcCmDVaBshGQZpGeIgnZ+sbJo2dUnDRv1FecAhKQQYS+d7vxWuT39U52E4OAXzE6Gm7zxbsLw+M++9/3/AVBLBwg4uZbxNAUAAHMVAABQSwECFAAUAAgICACCiixJOLmW8TQFAABzFQAAIgAAAAAAAAAAAAAAAAAAAAAAZmlfZHpfMTAwMDAwMTc4XzIwMTYtMDktMTFfbmV3LnR4dFBLBQYAAAAAAQABAFAAAACEBQAAAAA=";




            Dictionary<string, string> HeadPostParam = new Dictionary<string, string>();

            HeadPostParam.Add("version", "4.0.0.1");
            HeadPostParam.Add("member_id", "100000178");//商户号
            HeadPostParam.Add("client_ip", "116.236.217.150");//要与服务器IP保持一致
            HeadPostParam.Add("file_type", "fi");//收款：fi   出款：fo
            HeadPostParam.Add("settle_date", "2016-11-02");//指定日期的对帐文件（除当天）

            String RequestUrl = "https://vgw.baofoo.com/boas/api/fileLoadNewRequest";//测试请求地址

            String PostString  = HttpsRequest.GetParamsStr(HeadPostParam);
            Console.WriteLine("请求参数：" + RequestUrl + "?" + PostString);
            String ReturnStr = HttpsRequest.OpenReadWithHttps(RequestUrl,PostString);
            Console.WriteLine("返回结果：" + ReturnStr);

            if (String.IsNullOrEmpty(ReturnStr)) {
                Console.WriteLine("请求返回异常！");
                Console.ReadKey();
                return;
            }

            int sTRoF = ReturnStr.IndexOf("resp_code=0000");

            if (sTRoF < 0) {
                Console.WriteLine("下载失败！");
                Console.ReadKey();
                return;
            }
            
            String[] Splitstr = Regex.Split(ReturnStr, "resp_body=", RegexOptions.IgnoreCase);//解板返回的文件参数

            byte[] String64 = Base64.Decode(Splitstr[1]);//Base64编码  字符编码UF8
            try
            {
                String fileName = "d:/" + DateTime.Now.ToString("yyyy-MM-ddHHmmss") + "_" + HeadPostParam["file_type"] + ".zip";	//存在本地的路径（自行设置）
                FileStream fs = new FileStream(fileName, FileMode.Create);
                fs.Write(String64, 0, String64.Length);
                fs.Close();
                Console.Write("文件下载成功!路径：" + fileName);
            }
            catch(Exception e){
                Console.WriteLine("写入文件失败！");
                Console.Write(e.Message);
                Console.ReadKey();
            }

            Console.ReadKey();





        }
    }
}

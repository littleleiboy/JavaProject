using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Pkcs;
using Org.BouncyCastle.Security;
using Org.BouncyCastle.Pkix;
using Org.BouncyCastle.X509;
namespace RSACode
{
    /**
     * <b>Rsa加解密工具</b><br>
     * <br>
     * 公钥采用X509,Cer格式<br>
     * 私钥采用PKCS12加密方式的PFX私钥文件<br>
     * 加密算法为1024位的RSA\工作模式ECB\填充算法为PKCS1Padding<br>
     * 
     * @author 宝付_大圣
     * @version 1.0
     * 宝付网络科技（上海）有限公司
     * 引用加密动态库 BouncyCastle.Crypto.dll（必要）
     */
    class RsaReadUtil
    {
        /*
         * 
        ==================读取私钥==========================
         * path   证书路径
         * 
         * pwd  证书密码
         * 
         */
         public static AsymmetricKeyParameter getPrivateKeyFromFile( string path,string pwd)
        {
            FileStream fs = File.OpenRead(path);      //path路径下证书           
            char[] passwd = pwd.ToCharArray();
            
            Pkcs12Store store = new Pkcs12StoreBuilder().Build();  
            store.Load(fs, passwd); //加载证书  
            string alias = null;  
            foreach (string str in store.Aliases)  
            {  
                if (store.IsKeyEntry(str))  
                    alias = str;  
            }
            AsymmetricKeyEntry keyEntry = store.GetKey(alias);
            return keyEntry.Key;

        }

         /*
          ==================读取公钥==========================
          * 
          * path   证书路径
          * 
          */

         public static AsymmetricKeyParameter getPublicKeyFromFile(string path) 
         {
             System.Security.Cryptography.X509Certificates.X509Certificate CerPath = System.Security.Cryptography.X509Certificates.X509Certificate.CreateFromCertFile(path);
             Org.BouncyCastle.X509.X509Certificate serverCertificate = DotNetUtilities.FromX509Certificate(CerPath);//

             return serverCertificate.GetPublicKey(); 
         }

    }
}

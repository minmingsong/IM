package cn.song.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ListIP
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("本机的外网IP是：" + ListIP.getWebIp("http://www.ip138.com/"));
	}

	public static String getWebIp(String strUrl)
	{
		try
		{

			URL url = new URL(strUrl);

			BufferedReader br = new BufferedReader(new InputStreamReader(url

					.openStream()));

			String s = "";

			StringBuffer sb = new StringBuffer("");

			String webContent = "";

			while ((s = br.readLine()) != null)
			{
				sb.append(s + "\r\n");

			}

			br.close();
			webContent = sb.toString();
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			webContent = webContent.substring(start, end);

			return webContent;

		} catch (Exception e)
		{
			e.printStackTrace();
			return "error open url:" + strUrl;

		}
	}
}

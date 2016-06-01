package cn.song.Util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MUtil
{
	public static String getLocalIPList() throws SocketException
	{
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		NetworkInterface networkInterface;
		Enumeration<InetAddress> inetAddresses;
		InetAddress inetAddress;
		String ip;
		while (networkInterfaces.hasMoreElements())
		{
			networkInterface = networkInterfaces.nextElement();
			inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses.hasMoreElements())
			{
				inetAddress = inetAddresses.nextElement();
				if (inetAddress != null && inetAddress instanceof Inet4Address)
				{ // IPV4
					ip = inetAddress.getHostAddress();
					if (networkInterface.getName().equals("wlan1"))
					{
//						System.out.println(ip + "\n");
//						System.out.println(networkInterface.getName());
						
						return ip;
					}
				}

			}

		}
		return null;
	}

	public static void sendMsg(ObjectOutputStream oos, Message Msg) throws IOException
	{
		oos.writeObject(Msg);
	}

	
	
	public static class Message implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3887585876095560184L;
		String destName;
		String sourceName;
		String Text;
		
		public Message(String destName, String sourceName, String text)
		{
			super();
			this.destName = destName;
			this.sourceName = sourceName;
			Text = text;
		}
		public String getDestName()
		{
			return destName;
		}
		public void setDestName(String destName)
		{
			this.destName = destName;
		}
		public String getSourceName()
		{
			return sourceName;
		}
		public void setSourceName(String sourceName)
		{
			this.sourceName = sourceName;
		}
		public String getText()
		{
			return Text;
		}
		public void setText(String text)
		{
			Text = text;
		}
		
	}

	

}

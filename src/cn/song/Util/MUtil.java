package cn.song.Util;

import java.io.DataOutputStream;
import java.io.IOException;
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

	public static void sendMsg(DataOutputStream dos, String Msg) throws IOException
	{
		dos.writeUTF(Msg);
	}

}

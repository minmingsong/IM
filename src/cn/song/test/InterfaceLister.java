package cn.song.test;

import java.net.*;
import java.util.*;

import cn.song.Util.MUtil;

public class InterfaceLister
{
	public static void main(String[] args) throws Exception
	{
//		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//		while (interfaces.hasMoreElements())
//		{
//			NetworkInterface ni = interfaces.nextElement();
//			System.out.println(ni);
//			System.out.println("\n" + Arrays.toString(ni.getHardwareAddress()));
//		}
//		System.out.println(getLocalIPList());
//		getLocalIPList();
		MUtil.getLocalIPList();
	}

	public static List<String> getLocalIPList()
	{
		List<String> ipList = new ArrayList<String>();
		try
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
						System.out.println(ip+"\n");
						System.out.println(networkInterface.getName());
//						System.out.println(inetAddress.getHostName());
//						System.out.println(inetAddress.getCanonicalHostName());
						ipList.add(ip);
					}
					
				}
				
			}
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
		return ipList;
	}
}
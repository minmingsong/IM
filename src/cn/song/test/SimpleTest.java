package cn.song.test;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class SimpleTest
{

	public static void main(String[] args)
	{

//		StringBuffer str = new StringBuffer("123\n4567\n89");
//		System.out.println(str);
//		
//		ArrayList<String> ls = new ArrayList<>();
//		for (int i = 0; i<5; i++)
//		{
//			ls.add("fjfewfe"+i+"\n");
//		}
//		System.out.println(ls.get(0));
//		
		
		
		
		
		// get name representing the running Java virtual machine.  
//		String name = ManagementFactory.getRuntimeMXBean().getName();  
//		System.out.println(name);  
//		// get pid  
//		String pid = name.split("@")[0];  
//		System.out.println("Pid is:" + pid); 
		
		ArrayList<String> msglist = new ArrayList<>();
		
		int i = 20;
		while (i-->10)
		{
			msglist.add("nihao"+i);
		}
//		System.out.println(msglist);
		StringBuffer sb = new StringBuffer();
//		for (int j = 0; j<msglist.size(); j++)
//		{
//			sb.append(msglist.get(j)+'\n');
//		}
		
		for (String str : msglist)
		{
//			System.out.println(str);
			sb.append(str+'\n');
			
		}
		System.out.println(sb);
		System.out.println("长度"+sb.length());
		sb.delete(0, sb.length());
		System.out.println("长度"+sb.length());
		System.out.println(sb);
	}

}

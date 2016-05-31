package cn.song.test;

import java.util.ArrayList;

public class SimpleTest
{

	public static void main(String[] args)
	{

		StringBuffer str = new StringBuffer("123\n4567\n89");
		System.out.println(str);
		
		ArrayList<String> ls = new ArrayList<>();
		for (int i = 0; i<5; i++)
		{
			ls.add("fjfewfe"+i+"\n");
		}
		System.out.println(ls.get(0));
	}

}

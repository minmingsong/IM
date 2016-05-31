package cn.song.dataclient;

import java.io.Serializable;

public class Client_Info implements Serializable
{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8096982911405569977L;
	private int id;
	private String ip;
	private String name;
	private String password;
	public Client_Info(int id, String password)
	{
		this.id = id;
		this.password = password;
		this.name = "NO.1";
	}
	
	
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}




	public int getId()
	{
		return id;
	}




	public String getIp()
	{
		return ip;
	}




	public void setIp(String ip)
	{
		this.ip = ip;
	}




	
	
	
}

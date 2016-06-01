package cn.song.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.song.Util.MUtil;
import cn.song.Util.MUtil.Message;
import cn.song.dataclient.Client_Info;

public class Client extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8697923325770163718L;

	JButton login;

	Socket socket;

	Client_Info me;

	JTextArea msg;
	JTextField sendmsg;
	JButton send;
	JPanel panel;

	Message Msg = new Message("", "", "");
	List<String> msglist = null;
	StringBuffer sbuffer = null;

	
	ObjectOutputStream oos = null;
	public Client()
	{
		super("客户端");
		this.setLocationByPlatform(true);
		this.setSize(600, 400);
		
		sbuffer = new StringBuffer();
		
		msglist = new ArrayList<>();

		panel = new JPanel(null);
		panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		panel.setBackground(Color.LIGHT_GRAY);
		msg = new JTextArea();
		msg.setBounds(0, 0, panel.getWidth(), panel.getHeight() - 100);
		msg.setBackground(Color.GREEN);
		panel.add(msg);
		sendmsg = new JTextField();
		sendmsg.setBounds(0, msg.getHeight() + 10, panel.getWidth() - 100, 50);
		panel.add(sendmsg);
		send = new JButton("发送");
		send.setBounds(panel.getWidth() - 95, msg.getHeight() + 10, panel.getWidth() - (sendmsg.getWidth() + 10), 50);
		send.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendMSG();
				sendmsg.requestFocus();
			}
		});
		sendmsg.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				// super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					sendMSG();
				}
			}

		});

		panel.add(send);

		login = new JButton("登陆");
		login.setBounds(getWidth() / 3, getHeight() / 3, 100, 50);
		login.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					socket = new Socket("10.216.78.178", 6699);

					me = new Client_Info(11110000, "password");
					me.setName(ManagementFactory.getRuntimeMXBean().getName());
					Msg.setSourceName(me.getName());
					System.out.println("连接服务器成功");

					login.setVisible(false);
					Client.this.add(panel);
					panel.setVisible(true);
					Client.this.repaint();

					sendmsg.requestFocus();
					new Thread(new Runnable()
					{

						@Override
						public void run()
						{

							InputStream is = null;
							OutputStream os = null;
							
							ObjectInputStream ois = null;
							

							try
							{
								is = socket.getInputStream();
								os = socket.getOutputStream();
								
								oos = new ObjectOutputStream(os);
								oos.writeObject(me);
								ois = new ObjectInputStream(is);
								System.out.println("发送本客户的信息成功");
								oos.flush();
								Thread.sleep(1000);
							} catch (SocketException e)
							{
//								e.printStackTrace();
								System.out.println("SocketException");
								System.exit(0);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							} catch (IOException e)
							{
								e.printStackTrace();
							}

							String str = me.getName() + "在_线";
							while (!socket.isClosed())
							{

								try
								{
									Msg.setText(str);
									oos.writeObject(Msg);
									Thread.sleep(3000);
									Msg = (Message)ois.readObject();
									if (Msg.getText().equals(str))
									{
										continue;
									}
									
									msglist.add(Msg.getSourceName());
									msglist.add("     "+Msg.getText());
									DispalyHis();
									
								}catch (SocketException e)
								{
									return;
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								} catch (ClassNotFoundException e)
								{
//									e.printStackTrace();
									System.out.println("ClassNotFoundException");
								} catch (IOException e)
								{
									e.printStackTrace();
									System.out.println("IO");
								}
							}
							if (oos != null)
							{
								
								try
								{
									oos.close();
								} catch (IOException e1)
								{
									e1.printStackTrace();
								}
							}
							if (ois != null)
							{
								
								try
								{
									ois.close();
								} catch (IOException e1)
								{
									e1.printStackTrace();
								}
							}
						}
					}).start();
				}catch (ConnectException e1)
				{
					System.out.println("ConnectException");
					return;
				}
				catch (UnknownHostException e1)
				{
					e1.printStackTrace();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		this.setLayout(null);
		this.add(login);
		this.setVisible(true);
		this.addWindowListener(new WindowListener()
		{

			@Override
			public void windowOpened(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				
				if (socket != null)
				{

					if (!socket.isClosed())
					{
						try
						{
							socket.close();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO 自动生成的方法存根

			}
		});

	}

	public static void main(String[] args)
	{
		new Client();
	}

	/**
	 * 用于更新历史消息记录
	 */
	private void sendMSG()
	{
		if (sendmsg.getText().trim().equals(""))
		{
			return;
		}

		Msg.setText(sendmsg.getText());
//			MUtil.sendMsg(oos, Msg);
		msglist.add(sendmsg.getText());
		
		if (msglist.size() > 10)// 把内容控制在10行内
		{
			msglist = msglist.subList(5,10);
		}
		sendmsg.setText(null);
		try
		{
			MUtil.sendMsg(oos, Msg);
			oos.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("flush失败343");
		}
		System.out.println("消息发送成功");
		DispalyHis();
	}

	
	private void DispalyHis()
	{
		if (msglist.size()>0)
		{
			sbuffer.delete(0, sbuffer.length());
			for (String str:msglist)
			{
				sbuffer.append(str+'\n');
			}
		}
		msg.setText(sbuffer.toString());
	}

}

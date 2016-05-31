package cn.song.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.song.Util.MUtil;
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
	DataOutputStream dos = null;
	DataInputStream dis = null;
	ObjectOutputStream oos = null;
	ArrayList<String> msglist;
	StringBuffer sbuffer = new StringBuffer();

	public Client()
	{
		super("客户端");
		this.setLocationByPlatform(true);
		this.setSize(600, 400);
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
							

							try
							{
								is = socket.getInputStream();
								os = socket.getOutputStream();
								dos = new DataOutputStream(os);
								dis = new DataInputStream(is);
								
								oos = new ObjectOutputStream(os);
								oos.writeObject(me);
								os.write((me.getId() + " " + me.getPassword()).getBytes());
								os.flush();
								Thread.sleep(1000);
//								oos.close();
							} catch (IOException | InterruptedException e1)
							{
								e1.printStackTrace();
								System.exit(0);
							}

							while (!socket.isClosed())
							{

								try
								{
									// os.write("呼叫服务器。。。".getBytes());
									dos.writeUTF("呼叫服务器。。。");
									Thread.sleep(1000);
									
								}catch (SocketException e)
								{
									return;
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								} catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
					}).start();
				} catch (UnknownHostException e1)
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
	 * 
	 */
	private void sendMSG()
	{
		try
		{
			if (sendmsg.getText().trim().equals(""))
			{
				return;
			}

			MUtil.sendMsg(dos, sendmsg.getText());
			msglist.add(sendmsg.getText());

//			sbuffer.delete(0, sbuffer.length());
//			for (int i = 0; i < msglist.size(); i++)
//			{
//				sbuffer.append(msglist.get(i));// 自动转行
//			}
//			msg.setText(sbuffer.toString());
			if (msglist.size() > 10)// 超过把内容控制在10行内
			{
				msglist.subList(5, 10);
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		sendmsg.setText(" ");
		
		try
		{
			receive(dis.readUTF());
		} catch (IOException e)
		{
			return;
		}

	}

	private void receive(String str)
	{
		
		sbuffer.delete(0, sbuffer.length());
		for (int i = 0; i < msglist.size(); i++)
		{
			sbuffer.append(msglist.get(i)+"\n");// 自动转行
		}
		sbuffer.append(str);
		msglist.add(str);
		msg.setText(sbuffer.toString());
	}

}

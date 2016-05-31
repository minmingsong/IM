package cn.song.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.song.Util.MUtil;
import cn.song.dataclient.Client_Info;

public class Server extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -367491045079534108L;
	ServerSocket ssocket;
	Socket socket;
	int port;
	JLabel tip;
	JButton openServer;
	Client_Info clients;

	public Server()
	{

		super("Sever");

		this.setLayout(null);
		tip = new JLabel();
		openServer = new JButton("开启服务器");

		openServer.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						while (true)
						{

							System.out.println("等待连接——");
							try
							{
								socket = ssocket.accept();

							} catch (SocketException e)
							{
								return;
							} catch (IOException e)
							{
								e.printStackTrace();
							}
							System.out.println("客户端连接成功");
							new Thread(new Runing(socket)).start();

						}
					}
				}).start();
				openServer.setVisible(false);
				tip.setSize(100, 100);
				JLabel Tip = new JLabel(tip.getText());
				tip.setText("服务器开启成功");

				Tip.setBounds(Server.this.getWidth() / 3, tip.getY() + 50, 200, 50);
				Server.this.add(Tip);
				Server.this.repaint();
			}
		});

		this.setLocationByPlatform(true);
		this.setSize(600, 400);
		tip.setBounds(this.getWidth() / 3, this.getHeight() / 3, 100, 50);
		openServer.setBounds(this.getWidth() / 3, this.getHeight() / 3 + 50, 100, 50);
		this.add(tip);
		this.add(openServer);

		this.setVisible(true);
		this.addWindowListener(new WindowListener()
		{

			@Override
			public void windowOpened(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub

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
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					}
				}
				if (ssocket != null)
				{
					if (!ssocket.isClosed())
					{
						try
						{
							ssocket.close();
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

			}

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}
		});
		port = 6699;
		try
		{
			ssocket = new ServerSocket(port);
			String str = "服务器地址:" + MUtil.getLocalIPList();
			tip.setText(str);

			tip.setSize(str.length() * 10, 50);

		} catch (BindException e)
		{
			tip.setText("端口号已被占用，程序退出");
			return;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public class Runing implements Runnable
	{
		Socket socket;

		InputStream is = null;
		OutputStream os = null;
		ObjectInputStream ois = null;

		Client_Info client;
		public Runing(Socket socket)
		{
			super();
			this.socket = socket;

			try
			{
				is = socket.getInputStream();
				os = socket.getOutputStream();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}

		@Override
		public void run()
		{
			byte[] b = new byte[512];
			int len = 0;
			
			try
			{
				ois = new ObjectInputStream(is);
				client = (Client_Info)ois.readObject();
				System.out.println("得到的对象："+client.getName());
				Thread.currentThread().setName(client.getName());
				len = is.read(b);
				String str = new String(b, 0, len);
				System.out.println(str);
				String[] strs = str.split(" ");
				b = socket.getInetAddress().getAddress();

				System.out.println(socket.getInetAddress());
				System.out.println(Arrays.toString(b));
				clients = new Client_Info(Integer.valueOf(strs[0]), strs[1]);
				clients.setIp(Arrays.toString(b));
				System.out.println("客户：" + clients.getId() + "登陆成功\n" + "iP:" + clients.getIp());
//				ois.close();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			} catch (ClassNotFoundException e)
			{
//				e.printStackTrace();
				System.out.println("对象未传输");
			}
			b = new byte[512];
			DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			StringBuffer MSG = new StringBuffer();
			while (!socket.isClosed())
			{
				try
				{
					Thread.sleep(500);
					// len = is.read(b, 0, 512);
					// if (len != 0 && len != -1)
					// {
					// System.out.println(new String(b, 0, len));
					// }
					MSG.delete(0, MSG.length());
					MSG.append(dis.readUTF());
					System.out.println(MSG);
					if (!MSG.toString().equals("呼叫服务器。。。"))
					{
						dos.writeUTF("您发送的内容为：" + MSG.toString());
					}
				} catch (EOFException e)
				{
					System.out.println("EOFException");
					return;
				} catch (InterruptedException | IOException e)
				{
					e.printStackTrace();
				}

			}
			if (ois != null)
			{
				try
				{
					ois.close();
				} catch (IOException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args)
	{
		new Server();
	}
}

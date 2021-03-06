import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class MainWindow 
{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 800, 600);
		
		CaesarPanel caesarPanel = new CaesarPanel();
		tabbedPane.add(caesarPanel);
		tabbedPane.setTitleAt(0, "Caesar Cipher");
		
		AffinePanel affinePanel = new AffinePanel();
		tabbedPane.add(affinePanel);
		tabbedPane.setTitleAt(1, "Affine Cipher");
		
		HillPanel hillPanel = new HillPanel();
		tabbedPane.add(hillPanel);
		tabbedPane.setTitleAt(2, "Hill Cipher");
		
		VigenerePanel vigenerePanel = new VigenerePanel();
		tabbedPane.add(vigenerePanel);
		tabbedPane.setTitleAt(3, "Vigenere Cipher");
		
		Base64Panel base64Panel = new Base64Panel();
		tabbedPane.add(base64Panel);
		tabbedPane.setTitleAt(4, "Base64");
		
		PortaPanel portaPanel = new PortaPanel();
		tabbedPane.add(portaPanel);
		tabbedPane.setTitleAt(5, "Porta Cipher");
		
		frame.getContentPane().add(tabbedPane);
	}

}

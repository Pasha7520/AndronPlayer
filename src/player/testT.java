package player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class testT{
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
	static boolean bb;
	static JButton b1;
	static JButton b2;
	static JButton b3;
	static File f ;
	static PlayerMp3 pl;
	static boolean imp;
	static boolean play;
	static boolean stop;
	static JList southList;
	static boolean onlyTrack;
	static DBService service ;
	static int select =0;
		public static void createGUI() {
			  bb = true;
			  b1 = new JButton("Play");
			b2 = new JButton("Pause");
			 b3 = new JButton("Stop");
			imp = true;
			play = true;
			stop = false;
			 while(bb){
		
			bb=false;
		JFrame fren = new JFrame("1Mp3 Player!!");

		fren.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalGlue());
		final JLabel label = new JLabel("Вибраний файл");
		label.setAlignmentX(label.CENTER_ALIGNMENT);
		panel.add(label);	
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		JButton button = new JButton("Вибрати файл");
		button.setAlignmentX(button.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JFrame fre = new JFrame("тратата1");
            	
            	fre.setDefaultCloseOperation(fre.DISPOSE_ON_CLOSE);
                fre.addWindowListener(new WindowListener() {
         
                    public void windowActivated(WindowEvent event) {
         
                    }
         
                    public void windowClosed(WindowEvent event) {
                    	testT.createGUI();
                    		
                    }
         
                    public void windowClosing(WindowEvent event) {
                        Object[] options = { "Да", "Нет!" };
                        int n = JOptionPane
                                .showOptionDialog(event.getWindow(), "Закрыть окно?",
                                        "Подтверждение", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null, options,
                                        options[0]);
                        if (n == 0) {
                            event.getWindow().setVisible(false);
                            fren.setVisible(false);
                          
                            try {
            					imp = true;
            					play = true;
            					stop = false;
            					if(!(pl==null)){
            					pl.mp3Stop();
            					}
            				} catch (Exception e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}

                            //System.exit(0);
                            
                            bb = true;
                        }
                    }
         
                    public void windowDeactivated(WindowEvent event) {
         
                    }
         
                    public void windowDeiconified(WindowEvent event) {
         
                    }
         
                    public void windowIconified(WindowEvent event) {
         
                    }
         
                    public void windowOpened(WindowEvent event) {
         
                    }

         
                });
            	fre.setBounds(0, 0, 500, 450);
            	fren.setContentPane(new BgPanel());
            	Container p1 = fren.getContentPane();
            	p1.add(b1);
            	p1.add(b2);
            	p1.add(b3);
            	fre.getContentPane().add(p1);
            	fre.setSize(1366, 768);
            	
                JFileChooser fileopen = new JFileChooser();   
                
                int ret = fileopen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                   f = fileopen.getSelectedFile();
                   DBService service = new DBService();
                   service.checkOrWritting(f);
                    fre.setVisible(true);
                    onlyTrack = true;

                }
            }
       });
		
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			if(onlyTrack){
					try {
						
						if(play){
							if(imp){
							pl = new PlayerMp3(f.getAbsolutePath());
							pl.player();
							stop = true;
							play = false;
							}
							else{
								pl.resume();
								play = false;
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					if(southList.isSelectionEmpty()){
						southList.setSelectedIndex(0);
					}
				
				
				try {
					if(!(pl==null)){
						if(!(select == southList.getSelectedIndex())){
							pl.mp3Stop();
							play=true;
							stop=true;
							select = southList.getSelectedIndex();
							imp = true;
						}
					}
					if(play){
						if(imp){
						pl = new PlayerMp3(service.getAllTracks(),southList.getSelectedIndex(),southList);
						pl.player();
						stop = true;
						play = false;
						select = southList.getSelectedIndex();
						}
						else{
							pl.resume();
							play = false;
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					imp = true;
					play = true;
					stop = false;
					pl.mp3Stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try {
					if(stop){
					imp = false;
					play = true;
					pl.pause();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel.add(button);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		final JLabel label1= new JLabel("Вибрати з списку");
		label1.setAlignmentX(label1.CENTER_ALIGNMENT);
		panel.add(label1);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JButton button1 = new JButton("Список");
		button1.setAlignmentX(button1.CENTER_ALIGNMENT);
		button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	onlyTrack = false;
            	JFrame fre = new JFrame("тратата2");
            	
            	fre.setDefaultCloseOperation(fre.DISPOSE_ON_CLOSE);
            	//fre.setDefaultCloseOperation(fre.EXIT_ON_CLOSE);
                fre.addWindowListener(new WindowListener() {
         
                    public void windowActivated(WindowEvent event) {
         
                    }
         
                    public void windowClosed(WindowEvent event) {
                    	testT.createGUI();
                    		
                    }
         
                    public void windowClosing(WindowEvent event) {
                        Object[] options = { "Да", "Нет!" };
                        int n = JOptionPane
                                .showOptionDialog(event.getWindow(), "Закрыть окно?",
                                        "Подтверждение", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null, options,
                                        options[0]);
                        if (n == 0) {
                            event.getWindow().setVisible(false);
                            fren.setVisible(false);
                          
                            try {
            					imp = true;
            					play = true;
            					stop = false;
            					if(!(pl==null)){
            					pl.mp3Stop();
            					}
            				} catch (Exception e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}

                            //System.exit(0);
                            
                            bb = true;
                        }
                    }
         
                    public void windowDeactivated(WindowEvent event) {
         
                    }
         
                    public void windowDeiconified(WindowEvent event) {
         
                    }
         
                    public void windowIconified(WindowEvent event) {
         
                    }
         
                    public void windowOpened(WindowEvent event) {
         
                    }

         
                });
                 service = new DBService();
                
                southList = new JList(service.getAllTracks());
                southList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                southList.setVisibleRowCount(0);

                JScrollPane southScroll = new JScrollPane(southList);
                southScroll.setPreferredSize(new Dimension(380, 698));
                
                fre.setLayout(new BorderLayout());

                fre.getContentPane().add(new BgPanel());
                JPanel p = new JPanel();
                p.setBackground(Color.green);
                p.add(southScroll);
                fre.getContentPane().add(p,BorderLayout.EAST);
                
                JPanel p1 = new JPanel();
                p1.setBackground(Color.green);
                p1.add(b1);
                p1.add(b2);
                p1.add(b3);
                fre.getContentPane().add(p1,BorderLayout.NORTH);
               
                fre.setSize(1366, 768);
            	fre.setVisible(true);
                
            }
       });
		panel.add(button1);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		panel.add(Box.createVerticalGlue());
		
		
		fren.getContentPane().add(panel);
		fren.setPreferredSize(new Dimension(260, 220));
		fren.pack();
		fren.setLocationRelativeTo(null);
		fren.setVisible(true);	
	 }
		
	}
		
		
}
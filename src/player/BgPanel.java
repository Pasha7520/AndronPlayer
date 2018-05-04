package player;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class BgPanel extends JPanel  
{
	public void paintComponent(Graphics g){
       Image im = null;
       try {
           im = ImageIO.read(new File("D:\\HTML task\\Bootstrap MY\\img\\33.JPG"));
       } catch (IOException e) {}
       g.drawImage(im, 0, 0, null); 
	}
}


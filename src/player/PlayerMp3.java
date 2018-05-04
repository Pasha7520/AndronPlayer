package player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class PlayerMp3 implements Runnable {
	String [] strings;
	int index=0;
	private String name ;
	boolean one = true;
	private Thread thread;
	FileInputStream fis;
	AdvancedPlayer mp3;
	String path = "D:\\Music\\else\\";
	private int selected = 0;
	private JList jlist;
	
	PlayerMp3 (String name) throws Exception{
		this.name = name;
		thread = new Thread(this);
	}
	PlayerMp3 (String [] strings ,int index,JList l) throws Exception{
		this.jlist = l;
		this.strings = strings;
		this.index=index;
		thread = new Thread(this);
	}
	public void mp3Stop() throws IOException{
		thread.stop();
		
	}
	public void pause() throws IOException{
	
		thread.suspend();
		
	}
	
	public void resume(){
		thread.resume();
	}
	
	public void player() throws Exception{
		if(!(thread.isAlive())){
			thread.start();

		}
	}
	@Override
	public void run() {
		
		while(one){
			if(strings != null){
				if(index>(strings.length-1)){
					index = 0;
				}
			}
		
		try {
			if(name!=null){
			 fis = new FileInputStream(name);
			 mp3 = new AdvancedPlayer(fis);
			 mp3.play();
			}
			else if(strings!=null){
				
				fis = new FileInputStream(path+strings[index]);
				jlist.setSelectedIndex(index);
				mp3 = new AdvancedPlayer(fis);
				mp3.play();
				index++;
				
			}
	} catch (Exception e) {
		e.printStackTrace();
			}
		}
	}
	
	public String getName(){
		return name;
	}
	}

	



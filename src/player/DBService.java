package player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Track;
import util.HibernateUtil;

public class DBService {
	public void fillTracksNames(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Track> list= new ArrayList<Track>();
		
		File tracksDirectory = new File("D:\\Music\\else");
		if(tracksDirectory.exists()){
			File [] f = tracksDirectory.listFiles();
			for(File ff:f){
				System.out.println(ff.getName());
				Track newTrack = new Track(ff.getName());
				newTrack.setId(1);
				list.add(newTrack);
			}
		}
		
		try{
			session.beginTransaction();
			for(Track a:list){
				session.save(a);
			}
			
			
			session.getTransaction().commit();
		}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
		}
		finally{
				session.close();
		}
	}
	public boolean checkExistTrack(File track){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Track> list=null;
		boolean b = false;
		try{	
			session.beginTransaction();
			Query query = session.createQuery("FROM Track");
			list = query.list();
			for(Track t:list){
				if(t.getName().equals(track.getName())){
					b= true;
				}
			}
			session.getTransaction().commit();
		}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
		}
		finally{
				session.close();
		}
		return b;
	}
	
	public static void copyFile(File f , File s) throws IOException{
		Files.copy(f.toPath(),s.toPath());
		System.out.println(f.toPath());
		System.out.println(s.toPath());
		
	}
	
	public void checkOrWritting(File track){
		Session session = HibernateUtil.getSessionFactory().openSession();
		if(!checkExistTrack(track)){
			
			try{
				Track newTrack = new Track(track.getName());
				
				session.beginTransaction();
				
				
					session.save(newTrack);
					
				copyFileToPlayList(track);
				
				
				session.getTransaction().commit();
			}catch(Exception e){
					session.getTransaction().rollback();
					e.printStackTrace();
			}
			finally{
					session.close();
			}
		}
	}
	
	public boolean copyFileToPlayList(File f){
		boolean b = false;
	
		File copy = new File("D:\\Music\\else",f.getName());
		if(f.exists()){
			try {
				copyFile(f,copy);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	
	public String[] getAllTracks(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Track> list=null;
		String [] files = null ;
		try{
			
			session.beginTransaction();
			Query query = session.createQuery("FROM Track");
			list = query.list();
			files = new String[list.size()];
			int i=0;
			for(Track t:list){
				
				files[i++] = t.getName();
				
			}
			session.getTransaction().commit();
		}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
		}
		finally{
				session.close();
		}

		return files;
		
	}
}

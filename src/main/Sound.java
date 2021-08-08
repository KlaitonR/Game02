package main;

//import java.applet.Applet;
//import java.applet.AudioClip;

import java.io.*;
import javax.sound.sampled.*;

//@SuppressWarnings("deprecation")
public class Sound {
	
	public static class Clips {
		public Clip[] clips;
		private int p;
		private int count;
		private int size;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
				size = clips.length;
			}
		}
		
		public void play() {
			if(clips == null)return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p >= count)p = 0;
		}
		
		public void stop() {
			if(clips == null) 
				return;
			
			clips[p].stop();
			p = 0;
		}
		
		public void loop() {
			if(clips == null) return;
			clips[p].loop(300);
		}
		
		public int getP() {
			return p;
		}
		
		public int getSize() {
			return size;
		}
		
		public static Clips music = load("/music/music.wav", 1);
		public static Clips hurt = load("/music/hurt.wav", 1);
		public static Clips passos = load("/music/passosGrama.wav", 1);
		public static Clips shoot = load("/music/gunShot.wav", 1);
		public static Clips reload = load("/music/reloadRifle.wav", 1);
		public static Clips missAmo = load("/music/missAmo.wav", 1);
		public static Clips dropAndGetItem = load("/music/dropItem.wav", 1);
		public static Clips lighter = load("/music/lighter.wav", 1);
		public static Clips text = load("/music/text.wav", 1);
		public static Clips selected = load("/music/selected.wav", 1);
		public static Clips cuttingTree = load("/music/cuttingTree.wav", 1);
		public static Clips fallingTree = load("/music/fallingTree.wav", 1);
		public static Clips digging = load("/music/digging.wav", 1);
		public static Clips fishing = load("/music/fishing.wav", 1);
		public static Clips pigDeath = load("/music/pigDeath.wav", 1);
		public static Clips pigGrunts = load("/music/pigGrunts.wav", 1);
		public static Clips waterDrop = load("/music/waterDrop.wav", 1);
		public static Clips waterRunning = load("/music/waterRunning.wav", 1);
		public static Clips selectedInventory = load("/music/selectedInventory.wav", 1);
		public static Clips medievalMusic = load("/music/medievalMusic.wav", 1);
		public static Clips openBag = load("/music/openBag.wav", 1);
		public static Clips creation = load("/music/creation.wav", 1);
		public static Clips paper = load("/music/folha.wav", 1);
		public static Clips chuva = load("/music/chuva.wav", 1);
		public static Clips trovao = load("/music/trovao.wav", 1);
		public static Clips pickaxeDirt = load("/music/pickaxeDirt.wav", 1);
		public static Clips pickaxe = load("/music/pickaxe.wav", 1);
		
		private static Clips load(String name, int count) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataInputStream dis  =new DataInputStream(Sound.class.getResourceAsStream(name));
				
				byte[] buffer = new byte[1024];
				int read = 0;
				
				while((read = dis.read(buffer)) >= 0) {
					baos.write(buffer,0,read);
				}
				dis.close();
				byte[] data = baos.toByteArray();
				return new Clips(data, count);
				
			}catch (Exception e) {
				try {
					return new Clips(null,0);
				}catch (Exception ee) {
					return null;
				}
			}
		}
	}
}

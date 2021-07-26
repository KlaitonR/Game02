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
		
		public static Clips music = load("/music.wav", 1);
		public static Clips hurt = load("/hurt.wav", 1);
		public static Clips passos = load("/passosGrama.wav", 1);
		public static Clips shoot = load("/gunShot.wav", 1);
		public static Clips reload = load("/reloadRifle.wav", 1);
		public static Clips missAmo = load("/missAmo.wav", 1);
		public static Clips dropAndGetItem = load("/dropItem.wav", 1);
		public static Clips lighter = load("/lighter.wav", 1);
		public static Clips text = load("/text.wav", 1);
		public static Clips selected = load("/selected.wav", 1);
		public static Clips cuttingTree = load("/cuttingTree.wav", 1);
		public static Clips fallingTree = load("/fallingTree.wav", 1);
		public static Clips digging = load("/digging.wav", 1);
		public static Clips fishing = load("/fishing.wav", 1);
		public static Clips pigDeath = load("/pigDeath.wav", 1);
		public static Clips pigGrunts = load("/pigGrunts.wav", 1);
		public static Clips waterDrop = load("/waterDrop.wav", 1);
		public static Clips waterRunning = load("/waterRunning.wav", 1);
		public static Clips selectedInventory = load("/selectedInventory.wav", 1);
		public static Clips medievalMusic = load("/medievalMusic.wav", 1);
		public static Clips openBag = load("/openBag.wav", 1);
		public static Clips creation = load("/creation.wav", 1);
		public static Clips paper = load("/folha.wav", 1);
		
		
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

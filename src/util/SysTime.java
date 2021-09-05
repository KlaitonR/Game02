package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Game;
import main.Sound;

public class SysTime {

	public int hour = 8, minute = 0, second = 0, darken;
	public boolean dusk, dawn, night, day;
	public int duskHour = 18, duskFinalHour = 19, dawnHour = 6, dawnFinalHour = 7;
	public int controlDarken, opacityNight = 235;

	private int frames, maxFrames = 5, index, maxIndex = 3;
	private int framesTrovao, maxFramesTrovao = 10, indexTrovao, maxIndexTrovao = 8;
	private boolean trovao;
	private long init;
	private int espera, tempMin;
	
	public long initChuva;
	public int timeChuva;
	public int timeProxChuva;
	public boolean chovendo;
	public BufferedImage chuva[];
	public ArrayList<Mapa> mapa;
	public ArrayList<Regiao> regiao;
	
	public SysTime() {
		
		mapa = new ArrayList<>();
		regiao = new ArrayList<>();
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
		//TimeProxChuva precisa se maior que timeChuva
		timeProxChuva = Game.rand.nextInt(120000);
		timeChuva = 50000;
		//trovao
		initChuva = init = System.currentTimeMillis();
		espera = Game.rand.nextInt(30000);
		tempMin = 5000;
		
		chuva = new BufferedImage[9];
		try {
			chuva[0] = ImageIO.read(getClass().getResource("/images/chuva01.png"));
			chuva[1] = ImageIO.read(getClass().getResource("/images/chuva02.png"));
			chuva[2] = ImageIO.read(getClass().getResource("/images/chuva03.png"));
			chuva[3] = ImageIO.read(getClass().getResource("/images/chuva04.png"));
			chuva[4] = ImageIO.read(getClass().getResource("/images/chuva_trovao01.png"));
			chuva[5] = ImageIO.read(getClass().getResource("/images/chuva_trovao02.png"));
			chuva[6] = ImageIO.read(getClass().getResource("/images/chuva_trovao03.png"));
			chuva[7] = ImageIO.read(getClass().getResource("/images/chuva_trovao02.png"));
			chuva[8] = ImageIO.read(getClass().getResource("/images/chuva_trovao01.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage imageChuva() {
		if(trovao)
			return chuva[indexTrovao];
		else
			return chuva[index];
	}
	
	public void tick(double timer) {
		
		coutTime(timer);
		checkDuskOrDawn();
		controllChuva(timer);
		
	}
	
	public void coutTime(double timer) {
//		TimeSystem
		if(Game.gameState.equals("NORMAL")) {

			if(timer >= 1)
				second++;
				
			if(second == 60) {
				minute++;
				second = 0;
			}
				
			if(minute == 59) {
				minute = 0;
				hour++;
			}
				
			if(hour == 24)
				hour = 0;
		}
	}
	
	public void checkDuskOrDawn() {
		
		if(hour >= dawnHour && hour < dawnFinalHour) {
			controlDarken ++;
			if(controlDarken == 15) {
				darken++;
				controlDarken = 0;
			}
			dusk = false;
			dawn = true;
			day = false;
			night = false;
				
		}else if(hour >= duskHour && hour < duskFinalHour){
			controlDarken ++;
			if(controlDarken == 15) {
				darken ++;
				controlDarken = 0;
			}
			dawn = false;
			dusk = true;
			day = false;
			night = false;
			
		}else {
			dawn = false;
			dusk = false;
			if(hour>=dawnFinalHour && hour<duskHour) {
				day = true;
				night = false;
			}else {
				day = false;
				night = true;
			}
		}
	}
	
	public void render(Graphics g) {
		
		if(Game.gameState.equals("NORMAL")) {
			
			Graphics2D g2 = (Graphics2D) g;
			
			if(!trovao) {
				renderDuskOrDawn(g2);
				
			}else {
				if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)) {
					if(!day) {
						g2.setColor(new Color(0,0,80,100));
						g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
					}
				}else {
					renderDuskOrDawn(g2);
				}
			}
		}
	}
	
	public void renderDuskOrDawn(Graphics2D g2) {
		
		if(dusk && !Game.player.useLighter) { // se estiver anoitecendo 
			if (darken < 235) {
				g2.setColor(new Color(0,0,0, darken));
			}else {
				g2.setColor(new Color(0,0,0, opacityNight));
			}
			g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		}
		
		if(dawn && !Game.player.useLighter) { // se estiver amanhecendo 
			if(235 - darken >= 0) {
				g2.setColor(new Color(0,0,0, opacityNight - darken));
			}else {
				g2.setColor(new Color(0,0,0,0));
			}
	
			g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	
		}
		
		if(night && !Game.player.useLighter) {
			g2.setColor(new Color(0,0,0, opacityNight));
			g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
		
		if(day) {
			g2.setColor(new Color(0,0,0,0));
			g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
		
	}
	
	public void controllChuva(double timer) {
		
		if(timer > initChuva + timeProxChuva + timeChuva) {
			initChuva = (long)timer;
			chovendo = true;
			timeProxChuva = Game.rand.nextInt(120000) + timeChuva;
			Sound.Clips.chuva.loop();
		}else {
			if(timer > initChuva + timeChuva) {
				chovendo = false;
				Sound.Clips.chuva.stop();
			}
		}
		
		if(chovendo) {
			if(timer > espera + init + tempMin) {
				trovao = true;
				espera = Game.rand.nextInt(10000);
				init = (long)timer;
			}
			
			if(trovao) {
				framesTrovao++;
				if(framesTrovao == maxFramesTrovao) {
					framesTrovao = 4;
					indexTrovao++;
					if(indexTrovao > maxIndexTrovao) {
						indexTrovao = 4;
						trovao = false;
						Sound.Clips.trovao.play();
					}
				}
			}else {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index > maxIndex)
						index = 0;
				}
			}
		}
		
	}
	
}



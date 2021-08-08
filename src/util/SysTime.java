package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Game;
import main.Sound;

public class SysTime {

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

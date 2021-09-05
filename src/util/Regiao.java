package util;

import java.util.ArrayList;

public enum Regiao {
	
	REGIAO_FLORESTA,
	REGIAO_MANGUE,
	REGIAO_CALABOU�O,
	REGIAO_ROOM_HOUSE_01;
	
	public static ArrayList<Regiao> allRegioes = new ArrayList<>();

	public static ArrayList<Regiao> addAll() {
		allRegioes.add(REGIAO_FLORESTA);
		allRegioes.add(REGIAO_CALABOU�O);
		allRegioes.add(REGIAO_MANGUE);
		allRegioes.add(REGIAO_ROOM_HOUSE_01);
		
		return allRegioes;
	}
	
}

package util;

import java.util.ArrayList;

public enum Mapa {
	
	MAPA_FLORESTA,
	MAPA_CALABOU�O;
	
	public static ArrayList<Mapa> allMapas = new ArrayList<>();

	public static ArrayList<Mapa> addAll() {
		allMapas.add(MAPA_FLORESTA);
		allMapas.add(MAPA_CALABOU�O);
		
		return allMapas;
	}
}

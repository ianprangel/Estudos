package application;

import java.util.Locale;
import java.util.Scanner;

import xadrez.PartidaXadrez;


public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		UI.imprimirTabuleiro(partidaXadrez.pegaPecas());
		
		sc.close();

	}

}

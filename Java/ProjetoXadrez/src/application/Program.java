package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;


public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.imprimirPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem:");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.clearScreen();
				UI.imprimirTabuleiro(partidaXadrez.pegaPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.println("Destino:");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.fazendoMovimentoXadrez(origem, destino);
				
				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
			}
			catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}

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
		
		while (!partidaXadrez.getXequemate()) {
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
				
				if(partidaXadrez.getPromocao() != null) {
					System.out.println("Entre com a peça para a promoção (Q-B-T-C):");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.println("Valor Inválido! Entre com a peça para a promoção (Q-B-T-C):");
						type = sc.nextLine().toUpperCase();
					}
					partidaXadrez.trocaPecaPromovida(type);
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
		UI.clearScreen();
		UI.imprimirPartida(partidaXadrez, capturada);
	}

}

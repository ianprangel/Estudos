package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		if (getCor() == Cor.BRANCA) {
			p.setValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setValores(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() +2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}
	

}

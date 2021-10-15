package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		// verifica��o pra cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());;
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// verifica��o pra esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		// verifica��o pra direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// verifica��o pra baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());;
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		// verifica��o noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);;
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		// verifica��o nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
						
		// verifica��o sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		// verifica��o sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);;
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}		
		
		return mat;
	}
}


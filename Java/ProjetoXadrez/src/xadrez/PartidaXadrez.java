package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		configInicial();
	}
	
	public PecaXadrez[][] pegaPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez fazendoMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		return pecaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Não tem uma peça na posição");
		}
		if (!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não existe movimentos possíveis para a peça escolhida");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode ir pra posição de destino.");
		}
	}
	
	private void colocaPecaNova(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	private void configInicial() {
		colocaPecaNova('c', 1, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('c', 2, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('d', 2, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('e', 1, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('e', 2, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('d', 1, new Rei(tabuleiro, Cor.BRANCA));
		
		colocaPecaNova('c', 7, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('c', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('d', 7, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('e', 7, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('e', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('d', 8, new Rei(tabuleiro, Cor.PRETA));
	}
	

}

package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Tabuleiro tabuleiro;
	private Cor jogadorAtual;
	private boolean check;
	private boolean xequemate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		configInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getxeque() {
		return check;
	}
	
	public boolean getXequemate() {
		return xequemate;
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
		
		if (testexeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Você não pode se colocar em cheque!");
		}
		
		check = (testexeque(oponente(jogadorAtual))) ? true : false;
		
		if (testeXequemate(oponente(jogadorAtual))) {
			xequemate = true;
		}
		else {
			proximoTurno();
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.aumentaContadorMovimento();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.diminuiContadorMovimento();
		tabuleiro.colocaPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Não tem uma peça na posição");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua");
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
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private void colocaPecaNova(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe este Rei no tabuleiro");
	}
	
	private boolean testexeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequemate(Cor cor) {
		if(!testexeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i,j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeXeque = testexeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if(!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void configInicial() {
		colocaPecaNova('a', 1, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('c', 1, new Bispo(tabuleiro,Cor.BRANCA));
		colocaPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCA));
		colocaPecaNova('f', 1, new Bispo(tabuleiro,Cor.BRANCA));
		colocaPecaNova('h', 1, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('a', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('b', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('c', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('d', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('e', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('f', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('g', 2, new Peao(tabuleiro,Cor.BRANCA));
		colocaPecaNova('h', 2, new Peao(tabuleiro,Cor.BRANCA));
		
		colocaPecaNova('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('c', 8, new Bispo(tabuleiro,Cor.PRETA));
		colocaPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETA));
		colocaPecaNova('f', 8, new Bispo(tabuleiro,Cor.PRETA));
		colocaPecaNova('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('a', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('b', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('c', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('d', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('e', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('f', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('g', 7, new Peao(tabuleiro, Cor.PRETA));
		colocaPecaNova('h', 7, new Peao(tabuleiro, Cor.PRETA));	
     }
}

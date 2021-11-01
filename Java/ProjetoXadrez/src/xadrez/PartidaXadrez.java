package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavaleiro;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Tabuleiro tabuleiro;
	private Cor jogadorAtual;
	private boolean check;
	private boolean xequemate;
	private PecaXadrez vulnerabilidadeEnPassant;
	private PecaXadrez promocao;
	
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
	
	public PecaXadrez getVulnerabilidadeEnPassant() {
		return vulnerabilidadeEnPassant;
	}
	
	public PecaXadrez getPromocao() {
		return promocao;
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
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//Movimento Especial Promoção
		promocao = null;
		if (pecaMovida instanceof Peao) {
			if(pecaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = trocaPecaPromovida("Q");
			}
		}
		
		check = (testexeque(oponente(jogadorAtual))) ? true : false;
		
		if (testeXequemate(oponente(jogadorAtual))) {
			xequemate = true;
		}
		else {
			proximoTurno();
		}
		
		// Movimento Especial En Passant
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			vulnerabilidadeEnPassant = pecaMovida;
		}
		else {
			vulnerabilidadeEnPassant = null;
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez trocaPecaPromovida(String type) {
		if(promocao == null) {
			throw new IllegalStateException("Não tem peça para a Promoção");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promocao;
		}
		
		Posicao pos = promocao.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.colocaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	private PecaXadrez novaPeca(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(tabuleiro,cor);
		if (type.equals("C")) return new Cavaleiro(tabuleiro,cor);
		if (type.equals("Q")) return new Rainha(tabuleiro,cor);
		return new Torre(tabuleiro,cor);
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.aumentaContadorMovimento();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
		// Movimento Especial Roque
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);
			torre.aumentaContadorMovimento();
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);
			torre.aumentaContadorMovimento();
		}
		
		//Movimento Especial En Passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCA) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
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
		
		// Movimento Especial Roque
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.diminuiContadorMovimento();
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.diminuiContadorMovimento();
		}
		
		//Movimento Especial En Passant
				if (p instanceof Peao) {
					if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulnerabilidadeEnPassant) {
						PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
						Posicao peaoPosicao;
						if (p.getCor() == Cor.BRANCA) {
							peaoPosicao = new Posicao(3, destino.getColuna());
						}
						else {
							peaoPosicao = new Posicao(4, destino.getColuna());
						}
						tabuleiro.colocaPeca(peao, peaoPosicao);
					}
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
		colocaPecaNova('b', 1, new Cavaleiro(tabuleiro,Cor.BRANCA));
		colocaPecaNova('c', 1, new Bispo(tabuleiro,Cor.BRANCA));
		colocaPecaNova('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		colocaPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
		colocaPecaNova('f', 1, new Bispo(tabuleiro,Cor.BRANCA));
		colocaPecaNova('g', 1, new Cavaleiro(tabuleiro,Cor.BRANCA));
		colocaPecaNova('h', 1, new Torre(tabuleiro,Cor.BRANCA));
		colocaPecaNova('a', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('b', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('c', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('d', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('e', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('f', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('g', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		colocaPecaNova('h', 2, new Peao(tabuleiro,Cor.BRANCA, this));
		
		colocaPecaNova('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('b', 8, new Cavaleiro(tabuleiro,Cor.PRETA));
		colocaPecaNova('c', 8, new Bispo(tabuleiro,Cor.PRETA));
		colocaPecaNova('d', 8, new Rainha(tabuleiro,Cor.PRETA));
		colocaPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('f', 8, new Bispo(tabuleiro,Cor.PRETA));
		colocaPecaNova('g', 8, new Cavaleiro(tabuleiro,Cor.PRETA));
		colocaPecaNova('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaPecaNova('h', 7, new Peao(tabuleiro, Cor.PRETA, this));	
     }
}

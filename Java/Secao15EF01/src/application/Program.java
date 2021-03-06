package application;

import java.util.Locale;
import java.util.Scanner;

import entities.Account;
import entities.BusinessException;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Informe os dados da Conta");
		System.out.print("Numero: ");
		int num = sc.nextInt();
		System.out.print("Nome: ");
		sc.nextLine();
		String nome = sc.nextLine();
		System.out.print("Saldo Inicial: ");
		double saldoInicial = sc.nextDouble();
		System.out.print("Limite de Saque: ");
		double limiteSaque = sc.nextDouble();
		
		Account acc = new Account(num, nome, saldoInicial, limiteSaque);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print("Informe a quantidade do saque:");
		double quantia = sc.nextDouble();
		
		
		try {
			acc.withdraw(quantia);
			System.out.println("Novo saldo: " + String.format("%.2f", acc.getBalance()));
		}
		catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
		
		sc.close();

	}

}

package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Company;
import entities.Individual;
import entities.TaxPayer;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<TaxPayer> list = new ArrayList<TaxPayer>();
		
		System.out.print("Entre com o n?mero de contribuintes:");
		int N = sc.nextInt();
		
		for(int i =0; i < N; i++) {
			System.out.println("Dados do contribuinte N?:" + i+1);
			System.out.print("F?sico ou pessoa Jur?dica? (f/j");
			char ch = sc.next().charAt(0);
			System.out.print("Nome:");
			String name = sc.next();
			System.out.print("Renda Anual:");
			Double anualIncome = sc.nextDouble();
			if (ch == 'f') {
				System.out.print("Gasto com sa?de:");
				Double healthExpenditures = sc.nextDouble();
				Individual x = new Individual(name, anualIncome, healthExpenditures);
				list.add(x);
			}
			else {
				System.out.print("N?mero de Funcion?rios:");
				Integer numberOfEmployees = sc.nextInt();
				Company y = new Company(name, anualIncome, numberOfEmployees);
				list.add(y);
			}
		}
		
		System.out.println();
		System.out.println();
		System.out.println("TAXAS PAGAS:");
		for (TaxPayer tp : list) {
			System.out.println(tp.getName() + ": $" + String.format("%.2f",tp.tax())); 
		}
		System.out.println();
		
		double sum = 0.0;
		for (TaxPayer tp : list) {
			sum += tp.tax(); 
		}
		
		System.out.println("TOTAL DE TAXAS: $" + String.format("%.2f",sum));
		
		sc.close();

	}

}

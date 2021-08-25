package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Employee;
import entities.OutsorcedEmployee;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Employee> list = new ArrayList<>();
		
		System.out.println("Entre com o número de empregados: ");
		int n = sc.nextInt();
		
		for (int i=1; i<=n; i++) {
			
			System.out.println("Empregado Número:" + i + " dado:");
			System.out.print("Terceirizado: (y/n):");
			char ch = sc.next().charAt(0);
			System.out.print("Nome: ");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.print("Hours: ");
			int hours = sc.nextInt();
			System.out.println("Valor por hora: ");
			double valuePerHour = sc.nextDouble();
			if (ch == 'y') {
				System.out.print("Despesa Adicional: ");
				double additionalCharge = sc.nextDouble();
				list.add(new OutsorcedEmployee(name, hours, valuePerHour, additionalCharge));
			}
			else {
				list.add(new Employee(name, hours, valuePerHour));
			}
			
			
			
			System.out.println();
			System.out.println();
			System.out.println("PAGAMENTOS:");
			for (Employee emp : list) {
				System.out.println(emp.getName() + " - $ " + String.format("%.2f", emp.payment()));
			}
			
			
		}
		
		
		
		
		
		
		
		sc.close();

	}

}

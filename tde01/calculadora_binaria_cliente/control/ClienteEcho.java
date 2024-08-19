package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteEcho {

	public static void main(String[] args) {

		Socket socket;
		InputStream canalEntrada;
		OutputStream canalSaida;
		BufferedReader entrada;
		PrintWriter saida;

		try {
			socket = new Socket("127.0.0.1", 4000);

			canalEntrada = socket.getInputStream();
			canalSaida = socket.getOutputStream();

			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			Scanner leitor = new Scanner(System.in);

			System.out.println("Envie o primeiro valor:");
			String valor1 = leitor.nextLine();

			System.out.println("Envie o segundo valor:");
			String valor2 = leitor.nextLine();

			System.out.println("Escolha a operação ('+' para soma ou '-' para subtração):");
			String operacao = leitor.nextLine();

			String mensagem = valor1 + " " + valor2 + " " + operacao;
			saida.println(mensagem);

			String resultado = entrada.readLine();
			System.out.println(resultado);

			socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
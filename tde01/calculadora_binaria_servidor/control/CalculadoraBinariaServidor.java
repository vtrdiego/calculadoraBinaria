package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraBinariaServidor {

	private ServerSocket sckServidor;

	public CalculadoraBinariaServidor() throws IOException {
		this.sckServidor = new ServerSocket(4000);

		for (;;) {
			Socket sckEcho;
			InputStream canalEntrada;
			OutputStream canalSaida;
			BufferedReader entrada;
			PrintWriter saida;

			sckEcho = this.sckServidor.accept();
			canalEntrada = sckEcho.getInputStream();
			canalSaida = sckEcho.getOutputStream();
			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			while (true) {
				String linhaPedido = entrada.readLine();

				if (linhaPedido == null || linhaPedido.length() == 0)
					break;

				String[] partes = linhaPedido.split(" ");

				if (partes.length == 3) {
					try {
						String bin1 = partes[0];
						String bin2 = partes[1];
						String operacao = partes[2];

						String resultado;

						if (operacao.equals("+")) {
							resultado = somarBinarios(bin1, bin2);
						} else if (operacao.equals("-")) {
							resultado = subtrairBinarios(bin1, bin2);
						} else {
							saida.println("Erro: Operação incorreta.");
							continue;
						}

						saida.println("Resultado: " + resultado);

					} catch (NumberFormatException e) {
						saida.println("Erro: Valores inválidos.");
					}
				} else {
					saida.println("Erro: Esperado valores binários e uma operação.");
				}
			}
			sckEcho.close();
		}
	}

	public static int binarioParaDecimal(String binario) {
		int bitCount = binario.length();
		int valor = Integer.parseInt(binario, 2);
		if (binario.charAt(0) == '1') {
			valor -= (1 << bitCount);
		}
		return valor;
	}

	public static String decimalParaBinario(int decimal) {
		int bitCount = 8;
		if (decimal >= 0) {
			return String.format("%" + bitCount + "s", Integer.toBinaryString(decimal)).replace(' ', '0');
		} else {
			int mask = (1 << bitCount) - 1;
			int valor = (decimal & mask) + (1 << bitCount);
			return String.format("%" + bitCount + "s", Integer.toBinaryString(valor)).replace(' ', '0');
		}
	}

	public static String somarBinarios(String bin1, String bin2) {
		int num1 = binarioParaDecimal(bin1);
		int num2 = binarioParaDecimal(bin2);
		int soma = num1 + num2;
		return Integer.toString(soma);
	}

	public static String subtrairBinarios(String bin1, String bin2) {
		int num1 = binarioParaDecimal(bin1);
		int num2 = binarioParaDecimal(bin2);
		int diferenca = num1 - num2;
		return Integer.toString(diferenca);
	}

	public static void main(String[] args) {
		try {
			new CalculadoraBinariaServidor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



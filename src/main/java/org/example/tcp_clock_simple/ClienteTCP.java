package org.example.tcp_clock_simple;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe ClienteTCP.
 *
 * <p>cliente TCP que solicita ao servidor
 *  * o horário de uma região geográfica digitada pelo usuário.</p>
 *
 * @author José Victor
 * @version 1.0
 * @since 2026-03-17
 */

public class ClienteTCP {
    public static void main(String[] args) {
        Socket socket = null;

        try {
            int portaServidor = 12345;
            String servidor = "localhost";

            socket = new Socket(servidor,portaServidor);

            //Lendo a Região digitada
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite a região geografica: ");
            String mensagem = scanner.nextLine();
;
            //Criação fluxo entrada e saida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //Envia mensagem para servidor
            saida.write(mensagem);
            saida.newLine();
            saida.flush();

            //Recebe a resposta do servidor
            String resposta = entrada.readLine();
            System.out.println("Recebido: "+resposta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

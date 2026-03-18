package org.example.tcp_clock_multithread;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe ServidorTCP Concorrente.
 *
 * <p>Servidor que cria uma nova Thread para cada cliente
 * conectado, permitindo múltiplos atendimentos simultâneos.</p>
 *
 * @author José Victor
 * @version 2.0
 * @since 2026-03-17
 */
public class ServidorTCP {

    public static void main(String[] args) {

        int porta = 12345;

        try (ServerSocket servidorSocket = new ServerSocket(porta)) {

            System.out.println("Servidor concorrente esperando conexão na porta " + porta);

            while (true) {

                Socket clienteSocket = servidorSocket.accept();

                Thread thread = new Thread(new AtendeCliente(clienteSocket));
                thread.start();

            }

        } catch (IOException e) {
            System.out.println("IO " + e.getMessage());
        }
    }
}
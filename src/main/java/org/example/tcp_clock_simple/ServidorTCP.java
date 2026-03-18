package org.example.tcp_clock_simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

/**
 * Classe ServidorTCP.
 *
 * <p>Servidor que recebe uma região geográfica via socket TCP
 * e retorna o horário atual da região informada.</p>
 *
 * @author José Victor
 * @version 1.0
 * @since 2026-03-17
 */
public class ServidorTCP {
    public static void main(String[] args) {
        int porta = 12345;
        ServerSocket servidorSocket = null;
        try {
            servidorSocket = new ServerSocket(porta);
            System.out.println("Servidor esperando conexão na porta " + porta);

            while (true) {
                try (Socket clienteSocket = servidorSocket.accept()) {

                    //Criação fluxo entrada e saida
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                    BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));

                    System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());
                    //Recebe mensagem do cliente
                    String regiao = entrada.readLine();
                    String hora = null;
                    if (regiao != null) {
                        //Busaca e trata hora da região informada
                        try {
                            ZonedDateTime horaRegiao = ZonedDateTime.now(ZoneId.of(regiao));
                            System.out.println(horaRegiao.getHour() + ":" + horaRegiao.getMinute());
                            hora = "Na região de " + horaRegiao.getZone() + " são " + horaRegiao.getHour() + ":" + horaRegiao.getMinute();
                        } catch (ZoneRulesException e) {
                            hora = "Região invalida";
                        }
                        //Envia resposta
                        saida.write(hora);
                        saida.newLine();
                        saida.flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IO" + e.getMessage());
        } finally {
            if (servidorSocket != null) {
                try {
                    servidorSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

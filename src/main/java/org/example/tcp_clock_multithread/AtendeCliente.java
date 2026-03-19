package org.example.tcp_clock_multithread;

import java.io.*;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

/**
 * Classe AtendeCliente.
 *
 * <p>Responsável por atender um cliente em uma thread separada
 * em um servidor TCP multithread. Recebe uma região geográfica
 * enviada pelo cliente e retorna o horário correspondente.</p>
 *
 * @author José Victor
 * @version 1.0
 * @since 2026-03-17
 */

public class AtendeCliente implements Runnable {
    private Socket clienteSocket;

    public AtendeCliente(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }


    @Override
    public void run() {

        String nomeThread = Thread.currentThread().getName();

        System.out.println(
                "Thread " + nomeThread +
                        " atendendo cliente " +
                        clienteSocket.getInetAddress() +
                        ":" + clienteSocket.getPort()
        );

        //Criação fluxo entrada e saida
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
        ) {

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
                Thread.sleep(5000);
                //Envia resposta
                saida.write(hora);
                saida.newLine();
                saida.flush();

            }
        }catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (clienteSocket != null) {
                try {
                    clienteSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

package org.example.udp_clock;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Classe ClienteUDP.
 *
 * <p>Cliente UDP que envia ao servidor uma região geográfica
 * digitada pelo usuário e recebe como resposta o horário
 * atual daquela região.</p>
 *
 * @author José Victor
 * @version 1.0
 * @since 2026-03-17
 */
public class ClienteUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);

            Scanner entrada = new Scanner(System.in);

            //Pedindo para que o usuario insira a região
            System.out.println("Digite a região geografica: ");
            byte[] regiao = entrada.nextLine().getBytes();

            //ip e porta do servidor
            InetAddress servidor = InetAddress.getByName("localhost");
            int portaServidor = 6789;

            //enviando requisição (não bloqueante)
            DatagramPacket requisicao = new DatagramPacket(regiao, regiao.length, servidor, portaServidor);
            socket.send(requisicao);

            //recebendo resposta (bloqueante)
            byte[] bufferResposta = new byte[1024];
            DatagramPacket resposta = new DatagramPacket(bufferResposta, bufferResposta.length);
            socket.receive(resposta);

            //Construindo resposta e exibindo
            String respostaTexto = new String(resposta.getData(),0,resposta.getLength());
            System.out.println("Resposta: " + new String(respostaTexto));

        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: " + e.getMessage());
        } catch (SocketTimeoutException e){
            System.out.println("Servidor ocupado ou offline");
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

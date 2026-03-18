package org.example.udp_clock;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

/**
 * Classe ServidorUDP.
 *
 * <p>Servidor UDP que recebe de um cliente uma região geográfica
 * e retorna o horário atual correspondente utilizando
 * ZonedDateTime.</p>
 *
 * @author José Victor
 * @version 1.0
 * @since 2026-03-17
 */
public class ServidorUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(6789);
            byte[] buffer = new byte[1024];
            while (true) {

                //recebendo requisição (bloqueante)
                DatagramPacket requisicao = new DatagramPacket(buffer, buffer.length);
                socket.receive(requisicao);


                //Processando requisição para enviar a hora pela região
                String regiao = new String(requisicao.getData(), 0,requisicao.getLength());
                String hora =null;
                //Tratando quando a região não é valida
                try {
                    ZonedDateTime horaRegiao = ZonedDateTime.now(ZoneId.of(regiao));
                    System.out.println(horaRegiao.getHour()+":"+horaRegiao.getMinute());
                    hora = "Na região de "+horaRegiao.getZone() +" são " +horaRegiao.getHour()+":"+horaRegiao.getMinute();
                }catch (ZoneRulesException e){
                    hora = "Região invalida";
                }
                //Pàssando a hora para a requisição da resposta
                requisicao.setData(hora.getBytes());
                requisicao.setLength(requisicao.getLength());

                //Thread.sleep(2000);

                //enviando resposta (não bloqueante)
                DatagramPacket resposta = new DatagramPacket(requisicao.getData(),requisicao.getLength(),requisicao.getAddress(),requisicao.getPort());
                socket.send(resposta);

            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }// catch (InterruptedException e) {
         //   System.out.println("Interrupted: " + e.getMessage());
        //}
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

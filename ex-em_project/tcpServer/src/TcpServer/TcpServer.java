package TcpServer;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.net.ServerSocket;

import java.net.Socket;

import MsgPacker.PacketUnpacker;
import MsgPacker.PacketType;

public class TcpServer {

    public static void main(String[] args) {

       ServerSocket serverSocket = null; //외부 선언하여 try catch에 활용

        try {

            serverSocket = new ServerSocket(); // 서버용 소켓

            serverSocket.bind(new InetSocketAddress("localhost", 5001));
            //TCP 소켓 연결 대기

            while(true) {

                System.out.println( "[TCP 클라이언트 요청 기다림]");

                Socket socket = serverSocket.accept(); //연결

                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress(); //반환타입 : IP주소 + 포트번호

                System.out.println("[TCP 연결 수락] " + isa.getHostName());

                byte[] bytes = null;



                InputStream is = socket.getInputStream();

                bytes = new byte[1024];

                int readByteCount = is.read(bytes); //bytes에 읽어온 값 입력, int형인 이유는 -1 반환을 위함
                //성공 시 읽은 바이트 수 반환, 오류시 -1 반환
                
                //String message = null;
                //message = new String(bytes, 0, readByteCount, "UTF-8");

                PacketUnpacker msg = new PacketUnpacker(bytes); //할당 후 데이터 값 입력
            
                System.out.println("[읽어온 바이트 수: ]"+readByteCount);

                byte protocol = msg.getPacketType();

                //System.out.println(protocol);

                switch(protocol){
                    case PacketType.INT2LONG4:{
                        System.out.println("PACKET TYPE : INT2LONG4");
                        msg.printINT2LONG4();
                        break;

                    }
                    case PacketType.LONG1STRING:{
                        System.out.println("PACKET TYPE : LONG1STRING");
                        msg.printLONG1STRING();
                        break;
                    }
                    case PacketType.NOTHING:{
                        System.out.println("프로토콜에 존재하지 않는 값입니다");
                        break;
                    }


                }

                is.close();

                socket.close();

            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        if(!serverSocket.isClosed()) {

            try {

                serverSocket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }



}


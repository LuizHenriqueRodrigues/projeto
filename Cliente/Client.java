package Cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Util.MsgResponse;
import Util.MsgResquest;
import Util.Status;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        final String HOST ="localhost";
        final int PORT = 12345;
        double value1,value2;
         char oper;
        
         Scanner entrada = new Scanner(System.in);
        
        try {
            socket = new Socket(HOST,PORT);
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            System.out.println("digite uma operação (+,-,*,/): ");
            oper = entrada.nextLine().charAt(0); // pega  apenas o primeiro caracter
            System.out.println("digite o primeiro numero");
            value1 = Double.parseDouble(entrada.nextLine());
            System.out.println("digite o segundo numero");
            value2  = Double.parseDouble(entrada.nextLine());

            MsgResquest request = new MsgResquest(value1, value2, oper);

            out.writeObject(request);

            MsgResponse response = (MsgResponse) (in.readObject());

            if (response.getStatus() == Status.SUCESSO) {
                System.out.println("Resposta: "+ response.getValue());
            } else {
                if (response.getStatus() == Status.DIVISAO_ZERO) {
                    System.out.println("erro. Divisao por zero");
                } else {
                    System.out.println("operador invalido");
                }
            }
            entrada.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}

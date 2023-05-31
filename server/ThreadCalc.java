package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Util.MsgResponse;
import Util.MsgResquest;
import Util.Status;

public class ThreadCalc extends Thread {
        
    private Socket client;

    public ThreadCalc(Socket client){
        this.client = client;
    }

@Override
public void run() {
    try {
        System.out.println("conectado ao client: " + client.getInetAddress().getHostAddress());
        
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

        MsgResquest request = (MsgResquest) in.readObject();

        char oper = request.getOperation();
        double valeu1 = request.getValue1();
        double value2 = request.getValue2();
        double resp;
        MsgResponse response;

        switch (oper){
            case '+':
                resp = valeu1 + value2;
                response = new MsgResponse(Status.SUCESSO, resp);
            break;
        case '-':
                resp = valeu1 - value2;
                response = new MsgResponse(Status.SUCESSO, resp);      
            break;
            case'*':
                resp = valeu1 * value2;
                response = new MsgResponse(Status.SUCESSO, resp);      
            break;
            case '/':
                if (request.getValue2()== 0) {
                    response = new MsgResponse(Status.SUCESSO, 0);
                } else {
                    resp = valeu1 / value2;
                    response = new MsgResponse(Status.SUCESSO, resp);
                }
                break;

            default:
                    response = new MsgResponse(Status.OPERADOR_INVALIDO, 0);
                break;   
        }

        out.writeObject(response);
        


        in.close();
        out.close();
        client.close();
    } catch (Exception e) {
        System.out.println("Error na thread"+ e.getMessage());
    }
}

}

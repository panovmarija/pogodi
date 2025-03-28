/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class PokreniServer extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket ss=new ServerSocket(9000);
            System.out.println("pokrenut server");
            while(true)
            {
                System.out.println("cekam klijenta");
                Socket s=ss.accept();
                System.out.println("dosao klijent");
                ObradaZahteva o=new ObradaZahteva(s);
                o.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

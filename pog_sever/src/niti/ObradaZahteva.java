/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Broj;
import transfer.KlijentZahtev;
import transfer.ServerOdg;

/**
 *
 * @author USER
 */
public class ObradaZahteva extends Thread {
 private Socket s;

    public ObradaZahteva(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while(true)
        {
            KlijentZahtev kz=procitajZahtev();
            if(kz==null)
            {   
//                System.out.println(s==null);
//                System.out.println(s.isClosed());
                break;
            }
            ServerOdg so=new ServerOdg();
            switch (kz.getOperacija()) {
                case operacije.Operacije.klijent_pogadja:
                    Broj b=(Broj) kz.getParam();
                    for(Broj br:kontroler.Kontroler.getInstance().getPozicije())
                    {
                        if(br.getK()==b.getK() && br.getR()==b.getR())
                        {
                             b=br;
                            System.out.println(b.getBroj());
                            break;
                        }
                    }
                    so.setOdg(b);
                    break;
                case operacije.Operacije.vrati_sifru_klijentu:
                    int i=kontroler.Kontroler.getInstance().getPozicije().get(0).getBroj();
                    int ii=kontroler.Kontroler.getInstance().getPozicije().get(1).getBroj();
                    int iii=kontroler.Kontroler.getInstance().getPozicije().get(2).getBroj();
                    int x=Integer.min(i, Integer.min(ii, iii));
                    int y=Integer.max(i, Integer.max(ii, iii));
                    int z=i+ii+iii-x-y;
                    so.setOdg(z*100+y*10+x);
                    break;
                case operacije.Operacije.prikazi_pozicije:
                    so.setOdg(kontroler.Kontroler.getInstance().getPozicije());
                    break;
                case operacije.Operacije.nova_igra:
                    kontroler.Kontroler.getInstance().getGf().novaIgra();
                    break;
                default:
                    throw new AssertionError();
            }
            posaljiOdgovor(so);
            System.out.println("poslat odg klijentu");
        }
        
        System.out.println("gotov klijent");
     try {
//         System.out.println(s.isClosed());
         if(s!=null && !s.isClosed())
         s.close();
         
     } catch (IOException ex) {
         Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
     }
        System.out.println(s.isClosed());
        
    }

    private KlijentZahtev procitajZahtev() {
     try {
         ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
         return (KlijentZahtev) ois.readObject();
     } catch (IOException ex) {
            System.out.println("klijent odvezan/nema zahteva");
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
     }
     return null;
     }

    private void posaljiOdgovor(ServerOdg so) {
     try {
         ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
         oos.writeObject(so);
         oos.flush();
     } catch (IOException ex) {
         Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
     }
    }




 
}

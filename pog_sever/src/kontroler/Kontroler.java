/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import java.util.LinkedList;
import java.util.List;
import model.Admin;
import model.Broj;

/**
 *
 * @author USER
 */
public class Kontroler {
    private List<Admin> administratori;
    private Admin ulogovan;
    private List<Broj> pozicije;
    private static Kontroler instance; 
    public static Kontroler getInstance() {
        if(instance==null)
            instance=new Kontroler();
        return instance;
    }

    private Kontroler() {
    administratori=new LinkedList<>();
    Admin a=new Admin("pera@gmail.com", "123456", "pera", "peric");
    Admin b=new Admin("mara@gmail.com", "654321", "mara", "maric");
    administratori.add(a);
    administratori.add(b);
     }

    public List<Admin> getAdministratori() {
        return administratori;
    }

    public Admin getUlogovan() {
        return ulogovan;
    }

    public void setUlogovan(Admin ulogovan) {
        this.ulogovan = ulogovan;
    }

    public List<Broj> getPozicije() {
        return pozicije;
    }

    public void setPozicije(List<Broj> pozicije) {
        this.pozicije = pozicije;
    }
     
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Broj;
import transfer.KlijentZahtev;
import transfer.ServerOdg;

/**
 *
 * @author USER
 */
public class Pogodi extends javax.swing.JFrame {

    /**
     * Creates new form Pogodi
     */
    private int brpog=0;
   private int brpok=0;
    public Pogodi() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowHeight(60);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
//       umesto da onemogucim celu tabelu, svaki preko pokusaj ce vratiti 
        if(brpok==5)return ;
        int r=jTable1.getSelectedRow();
        int k=jTable1.getSelectedColumn();
//      nece uvecati brpok, ako je pozicija vec proverena
        if(jTable1.getValueAt(r, k)!=null)return ;    
        
        brpok++;
        komunikacija.Komunikacija.getInstance().posaljiZahtev(new KlijentZahtev(operacije.Operacije.klijent_pogadja, new Broj(-1, r,k)));
        ServerOdg so=komunikacija.Komunikacija.getInstance().vratiOdg();
        Broj b=(Broj) so.getOdg();
        jTable1.setValueAt(b.getBroj(), r,k );
//        ako je tacno pogodjeno
        if(b.getBroj()>0) 
            brpog++;
        
        if(brpog==3)
        {   KlijentZahtev kz=new KlijentZahtev(operacije.Operacije.vrati_sifru_klijentu, "");
            komunikacija.Komunikacija.getInstance().posaljiZahtev(kz);
            ServerOdg so1=komunikacija.Komunikacija.getInstance().vratiOdg();
            int sifra=(int) so1.getOdg();
            JOptionPane.showMessageDialog(this, "sifra je "+sifra);
            komunikacija.Komunikacija.getInstance().posaljiZahtev(new KlijentZahtev(operacije.Operacije.nova_igra, null));
//          normalno zatvaranje veze
            try {
                komunikacija.Komunikacija.getInstance().getS().close();
            } catch (IOException ex) {
                Logger.getLogger(Pogodi.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
            return;
        }
//        ako nije tacno pogodjeno i poslednji je pokusaj
            if(brpok==5)
            {
            KlijentZahtev kz=new KlijentZahtev(operacije.Operacije.prikazi_pozicije, "");
            komunikacija.Komunikacija.getInstance().posaljiZahtev(kz);
            ServerOdg so2=komunikacija.Komunikacija.getInstance().vratiOdg();
            prikazipozicije(so2.getOdg());
            komunikacija.Komunikacija.getInstance().posaljiZahtev(new KlijentZahtev(operacije.Operacije.nova_igra, null));
            Timer t=new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "izgubio si");
                    dispose();
                }
            });
            t.setRepeats(false);
            t.start();

//          zatvaranje veze
            try {
                    komunikacija.Komunikacija.getInstance().getS().close();
                } catch (IOException ex) {
                    Logger.getLogger(Pogodi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

    }//GEN-LAST:event_jTable1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pogodi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pogodi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pogodi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pogodi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pogodi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void prikazipozicije(Object odg) {
        for(int i=0;i<3;i++)
        {
        for(int j=0;j<3;j++)
        {
             jTable1.setValueAt(null, i, j);
        }
        }
        List<Broj>l=(List<Broj>) odg;
        for(Broj b:l)
        {
            jTable1.setValueAt(b.getBroj(), b.getR(), b.getK());
            System.out.println(b.getBroj());
        }
    }
        
}

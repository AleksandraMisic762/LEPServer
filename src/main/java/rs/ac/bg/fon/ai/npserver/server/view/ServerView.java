package rs.ac.bg.fon.ai.npserver.server.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;


public class ServerView extends javax.swing.JFrame {

    public ServerView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServerPokrenut = new javax.swing.JLabel();
        lblVremePokretanja = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKorisnici = new javax.swing.JTable();
        lblVremeAzuriranja = new javax.swing.JLabel();
        lblPrikazAzuriranPoslednjiPut = new javax.swing.JLabel();
        lblStanjeServera = new javax.swing.JLabel();
        lblTrenutnoStanje = new javax.swing.JLabel();
        btnPokreniS = new javax.swing.JButton();
        btnZaustavi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server program");

        lblServerPokrenut.setText("Server pokrenut:");

        lblVremePokretanja.setText("jLabel2");

        tblKorisnici.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblKorisnici);

        lblVremeAzuriranja.setText("jLabel1");

        lblPrikazAzuriranPoslednjiPut.setText("Prikaz ažuriran poslednji put:");

        lblStanjeServera.setText("Stanje servera:");

        lblTrenutnoStanje.setText("jLabel1");

        btnPokreniS.setText("Pokreni server");

        btnZaustavi.setText("Zaustavi server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPrikazAzuriranPoslednjiPut)
                                .addGap(18, 18, 18)
                                .addComponent(lblVremeAzuriranja))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblServerPokrenut)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblVremePokretanja))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStanjeServera)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTrenutnoStanje)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPokreniS, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnZaustavi, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStanjeServera)
                    .addComponent(lblTrenutnoStanje))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPokreniS)
                    .addComponent(btnZaustavi))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServerPokrenut)
                    .addComponent(lblVremePokretanja))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVremeAzuriranja)
                    .addComponent(lblPrikazAzuriranPoslednjiPut))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public JLabel getLblVremeAzuriranja() {
        return lblVremeAzuriranja;
    }

    public JLabel getLblVremePokretanja() {
        return lblVremePokretanja;
    }

    public JTable getTblKorisnici() {
        return tblKorisnici;
    }

    public JButton getBtnPokreniS() {
        return btnPokreniS;
    }

    public JButton getBtnZaustavi() {
        return btnZaustavi;
    }

    public JLabel getLblTrenutnoStanje() {
        return lblTrenutnoStanje;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPokreniS;
    private javax.swing.JButton btnZaustavi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPrikazAzuriranPoslednjiPut;
    private javax.swing.JLabel lblServerPokrenut;
    private javax.swing.JLabel lblStanjeServera;
    private javax.swing.JLabel lblTrenutnoStanje;
    private javax.swing.JLabel lblVremeAzuriranja;
    private javax.swing.JLabel lblVremePokretanja;
    private javax.swing.JTable tblKorisnici;
    // End of variables declaration//GEN-END:variables
}

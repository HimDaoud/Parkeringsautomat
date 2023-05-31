package com.example.parkering;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Bil{
    public String bilNummer;
    public Date startTid;
    public boolean kortTid;

    public Bil(String bilNummer,Date startTid,boolean kortTid){
        this.bilNummer=bilNummer; this.startTid=startTid;this.kortTid=kortTid;
    }
    public String formaterKvittering(){
        Date nytid =new Date();
        String nå=new SimpleDateFormat("dd.mm.yyyy hh:mm").format(nytid);
        String startTidOgDato = new SimpleDateFormat("dd.mm.yyyy hh:mm").format(startTid);
        String ut= "Kvittering for bilen: "+bilNummer+"\n";
        ut+="Start tid "+startTidOgDato+" til "+nå+"\n";
        ut+="Betalt: "+avgift();
        return ut;
    }
    public double getPris(){
        if (this.kortTid){
            return 10.00;
        } else {
            return 20.00;
        }
    }
    public double avgift(){
        Date nå = new Date();
        long varighet = nå.getTime() - startTid.getTime();
        int time=(int) varighet/10_000;
        return time * getPris();
    }
}

class Parkeringshus{
    public ArrayList <Bil> parkering = new ArrayList<>();

    public void reserverPlass(Bil enBil){
        parkering.add(enBil);
    }
    public String frigjørPlass(String bilNummeret){
        for (Bil enBil:parkering){
            if (enBil.bilNummer.equals(bilNummeret)){
                parkering.remove(enBil);
                return enBil.formaterKvittering();
            }
        }
        return "Fant ikke bilen";
    }
}

public class HelloController {
    Parkeringshus parkering =new Parkeringshus();
    @FXML
    private Label lblAvgift;

    @FXML
    private TextField txtBilnummer;

    @FXML
    void kjørUt(ActionEvent event) {
        String melding=parkering.frigjørPlass(txtBilnummer.getText());
        lblAvgift.setText(melding);

    }

    @FXML
    void regKorttid(ActionEvent event) {
        Date nyTid=new Date();
        Bil enBil=new Bil(txtBilnummer.getText(),nyTid,true);
        parkering.reserverPlass(enBil);
    }

    @FXML
    void regLangtid(ActionEvent event) {
        Date nyTid=new Date();
        Bil enBil = new Bil(txtBilnummer.getText(),nyTid,false);
        parkering.reserverPlass(enBil);
    }

}

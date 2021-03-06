/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jamk.Elokuvarekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Asmo
 */
class Elokuvalista implements Serializable {
    private ArrayList<Elokuva> elokuvat;
    private final String elokuvanTiedot = "elokuvalista.data";
    
    //konstruktori
    public Elokuvalista() {
        this.elokuvat = new ArrayList<>();
        alustaLista();
    }
    
    // luetaan tiedostosta tai luodaan oletus elokuvat
    private void alustaLista() {
    File el = new File(elokuvanTiedot);
    
    if (!el.exists()) {
        elokuvat.add(new Elokuva("Starwars", "Stewen", 1999, 130));
        elokuvat.add(new Elokuva("Starwars", "Stewen", 1999, 130));
        elokuvat.add(new Elokuva("Starwars", "Stewen", 1999, 130));
        elokuvat.add(new Elokuva("Starwars", "Stewen", 1999, 130));
        tallenna();
    }
    else {
        lue();
    }
        tulosta();
    }
    
    // tulostetaan elokuvat output-ikkunaan
    private void tulosta() {
        for (Elokuva k : elokuvat) {
        System.out.println(k);
        
        }
    }
    
    // Elokuvien lukeminen tiedostosta
    public void lue() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(new File(elokuvanTiedot)));
            elokuvat = (ArrayList<Elokuva>) input.readObject();
            
        } 
        catch (IOException ex)
        {
                System.out.println("Virhe tiedostosta luettaessa" + ex);
                
        }
        catch (ClassNotFoundException e) 
        {
                
        }
        finally 
        {
                try
                { 
                    if (input != null) input.close();
                }
                catch (IOException ex)
                {
                System.out.println("Virhe tiedoston sulkemisessa : " + ex);
                }
        }
        // debuggausta konsolille
        System.out.println("Elokuvalista luettu uudelleen tiedostosta");
        tulosta();
    
    }
    // elokuvien tallennus tiedostoon.
    public void tallenna() {
    
        ObjectOutputStream output = null;
        try
        {
            output = new ObjectOutputStream(new FileOutputStream(new File(elokuvanTiedot)));
            output.writeObject(elokuvat);
            
        }
        catch (IOException ex)
        {
            System.out.println("Virhe tiedostoon kirjoittamisessa" +ex);
        }
        finally 
        {
            try
            {
                if (output != null) output.close();
            }
            catch (IOException ex)
            {
                System.out.println("Virhe tiedostoa sulkiessa" +ex);
            }
        }
                
            
    }
    // palauttaa elokuvalistan
    public ArrayList<Elokuva> palauta() {
        return elokuvat;
    }
    // lisää uuden elokuvan
    public void lisaaUusi() {
        elokuvat.add(new Elokuva("Elokuvannimi", "Ohjaaja", 0,0));
        tallenna();
    
    }
    // poistaa elokuvan rivilt'
    public void poista (int rivi) {
        elokuvat.remove(rivi);
        tallenna();
    
    }
    // elokuvien jarjestaminen
    public void jarjesta() {
        Collections.sort(elokuvat, new JarjestaElokuvat());
        System.out.println("Elokuvalista on jarjestetty painon mukaan");
        tallenna();
    }
    
 
    
    public void paivita(Object uusi, int rivi, int sarake) {
        Elokuva elokuva = elokuvat.get(rivi);
        if (sarake == 0) elokuva.setNimi((String) uusi);
        else if (sarake == 1) elokuva.setOhjaaja((String) uusi);
        else if (sarake == 2) elokuva.setValmistusvuosi(((Integer) uusi).intValue());
        else if (sarake == 3) elokuva.setPituus(((Integer) uusi).intValue());
        
        elokuvat.remove(rivi);
        elokuvat.add(rivi, elokuva);
        tallenna();
    }
    
     class JarjestaElokuvat implements Comparator<Elokuva>
    {
         @Override
         public int compare (Elokuva e1, Elokuva e2)
         {
             if (e1.getValmistusvuosi() > e2.getValmistusvuosi()) return -1;
             if (e2.getValmistusvuosi() < e1.getValmistusvuosi()) return 1;
             return 0;
         }
    }
}

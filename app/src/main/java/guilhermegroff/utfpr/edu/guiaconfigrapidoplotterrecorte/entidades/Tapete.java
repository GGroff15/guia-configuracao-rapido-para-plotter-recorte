package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tapete {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cor;
    private String forcaAderencia;

    public Tapete(String cor, String forcaAderencia) {
        this.cor = cor;
        this.forcaAderencia = forcaAderencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getForcaAderencia() {
        return forcaAderencia;
    }

    public void setForcaAderencia(String forcaAderencia) {
        this.forcaAderencia = forcaAderencia;
    }

    @Override
    public String toString() {
        return cor + " - " + forcaAderencia;
    }
}

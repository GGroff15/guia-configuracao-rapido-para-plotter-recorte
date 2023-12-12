package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lamina {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cor;
    private String tipoMaterial;

    public Lamina(String cor, String tipoMaterial) {
        this.cor = cor;
        this.tipoMaterial = tipoMaterial;
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

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    @NonNull
    @Override
    public String toString() {
        return cor + " - " + tipoMaterial;
    }
}

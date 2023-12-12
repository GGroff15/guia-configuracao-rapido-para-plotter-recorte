package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Caneta {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String espessura;

    public Caneta(String espessura) {
        this.espessura = espessura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspessura() {
        return espessura;
    }

    public void setEspessura(String espessura) {
        this.espessura = espessura;
    }
}

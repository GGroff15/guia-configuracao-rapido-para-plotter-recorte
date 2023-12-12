package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "processo",
    foreignKeys = {
            @ForeignKey(entity = Material.class, parentColumns = "id", childColumns = "material"),
            @ForeignKey(entity = Tapete.class, parentColumns = "id", childColumns = "tapete"),
            @ForeignKey(entity = Caneta.class, parentColumns = "id", childColumns = "caneta"),
            @ForeignKey(entity = Lamina.class, parentColumns = "id", childColumns = "lamina")
    })
public class Processo {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(index = true)
    private Integer material;
    @ColumnInfo(index = true)
    private Integer tapete;
    @ColumnInfo(index = true)
    private Integer caneta;
    private Integer pressao;
    private String tipo;
    @ColumnInfo(index = true)
    private Integer lamina;
    private Integer profundidadeLamina;
    private String tecido;

    public Processo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public Integer getTapete() {
        return tapete;
    }

    public void setTapete(Integer tapete) {
        this.tapete = tapete;
    }

    public Integer getCaneta() {
        return caneta;
    }

    public void setCaneta(Integer caneta) {
        this.caneta = caneta;
    }

    public Integer getPressao() {
        return pressao;
    }

    public void setPressao(Integer pressao) {
        this.pressao = pressao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getLamina() {
        return lamina;
    }

    public void setLamina(Integer lamina) {
        this.lamina = lamina;
    }

    public Integer getProfundidadeLamina() {
        return profundidadeLamina;
    }

    public void setProfundidadeLamina(Integer profundidadeLamina) {
        this.profundidadeLamina = profundidadeLamina;
    }

    public String getTecido() {
        return tecido;
    }

    public void setTecido(String tecido) {
        this.tecido = tecido;
    }
}

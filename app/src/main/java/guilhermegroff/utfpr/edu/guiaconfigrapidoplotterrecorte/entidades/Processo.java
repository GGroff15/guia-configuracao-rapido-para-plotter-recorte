package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "processo",
    foreignKeys = {
            @ForeignKey(entity = Mat.class, parentColumns = "id", childColumns = "tapete"),
            @ForeignKey(entity = Blade.class, parentColumns = "id", childColumns = "lamina")
    })
public class Processo {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String material;
    private int gramatura;
    @ColumnInfo(index = true)
    private Integer tapete;
    private Integer pressao;
    private String tipo;
    @ColumnInfo(index = true)
    private Integer lamina;
    private Integer profundidadeLamina;
    private String tecido;
    private String pen;

    public Processo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTapete() {
        return tapete;
    }

    public void setTapete(Integer tapete) {
        this.tapete = tapete;
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

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setGramatura(int gramatura) {
        this.gramatura = gramatura;
    }

    public int getGramatura() {
        return gramatura;
    }

    public String getPen() {
        return pen;
    }

    public void setPen(String pen) {
        this.pen = pen;
    }
}

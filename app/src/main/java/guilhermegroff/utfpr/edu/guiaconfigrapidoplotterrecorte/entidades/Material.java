package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Material {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private int gramatura;
    private String marca;

    public Material(String nome, int gramatura, String marca) {
        this.nome = nome;
        this.gramatura = gramatura;
        this.marca = marca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGramatura() {
        return gramatura;
    }

    public void setGramatura(int gramatura) {
        this.gramatura = gramatura;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;

@Dao
public interface ProcessoDao {

    @Insert
    long insert(Processo processo);

    @Delete
    void delete(Processo processo);

    @Update
    void update(Processo processo);

    @Query("SELECT * FROM processo WHERE id = :id")
    Processo findById(int id);

    @Query("SELECT * FROM processo ORDER BY tipo ASC")
    List<Processo> findAll();

}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;

@Dao
public interface CanetaDao {

    @Insert
    long insert(Caneta caneta);

    @Delete
    void delete(Caneta caneta);

    @Update
    void update(Caneta caneta);

    @Query("SELECT * FROM caneta WHERE id = :id")
    Caneta findById(int id);

    @Query("SELECT * FROM caneta ORDER BY espessura ASC")
    List<Caneta> findAll();

}

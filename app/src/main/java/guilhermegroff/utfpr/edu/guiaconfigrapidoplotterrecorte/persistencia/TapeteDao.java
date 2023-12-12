package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;

@Dao
public interface TapeteDao {

    @Insert
    long insert(Tapete tapete);

    @Delete
    void delete(Tapete tapete);

    @Update
    void update(Tapete tapete);

    @Query("SELECT * FROM tapete WHERE id = :id")
    Tapete findById(int id);

    @Query("SELECT * FROM tapete ORDER BY cor ASC")
    List<Tapete> findAll();

}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;

@Dao
public interface LaminaDao {

    @Insert
    long insert(Lamina lamina);

    @Delete
    void delete(Lamina lamina);

    @Update
    void update(Lamina lamina);

    @Query("SELECT * FROM lamina WHERE id = :id")
    Lamina findById(int id);

    @Query("SELECT * FROM lamina ORDER BY cor ASC")
    List<Lamina> findAll();

}

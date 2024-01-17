package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;

@Dao
public interface LaminaDao {

    @Insert
    long insert(Blade lamina);

    @Delete
    void delete(Blade lamina);

    @Update
    void update(Blade lamina);

    @Query("SELECT * FROM Blade WHERE id = :id")
    Blade findById(int id);

    @Query("SELECT * FROM Blade ORDER BY cor ASC")
    List<Blade> findAll();

    @Query("SELECT * FROM Blade ORDER BY cor ASC")
    LiveData<List<Blade>> getItens();
}

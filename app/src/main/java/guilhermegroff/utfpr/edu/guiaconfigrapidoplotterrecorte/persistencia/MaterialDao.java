package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;

@Dao
public interface MaterialDao {

    @Insert
    long insert(Material material);

    @Delete
    void delete(Material material);

    @Update
    void update(Material material);

    @Query("SELECT * FROM material WHERE id = :id")
    Material findById(int id);

    @Query("SELECT * FROM material ORDER BY nome ASC")
    List<Material> findAll();

}

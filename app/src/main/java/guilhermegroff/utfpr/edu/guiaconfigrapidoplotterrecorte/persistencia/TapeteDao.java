package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;

@Dao
public interface TapeteDao {

    @Insert
    long insert(Mat tapete);

    @Delete
    void delete(Mat tapete);

    @Update
    void update(Mat tapete);

    @Query("SELECT * FROM Mat WHERE id = :id")
    Mat findById(int id);

    @Query("SELECT * FROM Mat ORDER BY cor ASC")
    List<Mat> findAll();

    @Query("SELECT * FROM Mat ORDER BY cor ASC")
    LiveData<List<Mat>> getItens();
}

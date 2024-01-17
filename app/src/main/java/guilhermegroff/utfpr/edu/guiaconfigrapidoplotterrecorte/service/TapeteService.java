package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.TapeteDao;

public class TapeteService {

    private Context context;
    private TapeteDao tapeteDao;

    public TapeteService(Context context) {
        this.tapeteDao = PlotterDatabase.getDatabase(context).tapeteDao();
    }

    public List<Mat> listar() {
        return tapeteDao.findAll();
    }

    public LiveData<List<Mat>> getItens() {
        return tapeteDao.getItens();
    }

    public Mat search(int id) {
        return tapeteDao.findById(id);
    }

    public void save(Mat mat) {
        tapeteDao.insert(mat);
    }

    public void update(Mat mat) {
        tapeteDao.update(mat);
    }
}

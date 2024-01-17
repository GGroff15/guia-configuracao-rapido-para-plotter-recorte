package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.LaminaDao;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class LaminaService {

    private final LaminaDao laminaDao;

    public LaminaService(Context context) {
        this.laminaDao = PlotterDatabase.getDatabase(context).laminaDao();
    }

    public List<Blade> listar() {
        return laminaDao.findAll();
    }

    public void save(Blade lamina) {
        laminaDao.insert(lamina);
    }

    public void update(Blade lamina) {
        laminaDao.update(lamina);
    }

    public Blade buscar(int id) {
        return laminaDao.findById(id);
    }

    public LiveData<List<Blade>> getItens() {
        return laminaDao.getItens();
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.LaminaDao;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class LaminaService {

    private final LaminaDao laminaDao;

    public LaminaService(Context context) {
        this.laminaDao = PlotterDatabase.getDatabase(context).laminaDao();
    }

    public List<Lamina> listar() {
        return laminaDao.findAll();
    }

    public void save(Lamina lamina) {
        laminaDao.insert(lamina);
    }

    public void update(Lamina lamina) {
        laminaDao.update(lamina);
    }

    public Lamina buscar(int id) {
        return laminaDao.findById(id);
    }
}

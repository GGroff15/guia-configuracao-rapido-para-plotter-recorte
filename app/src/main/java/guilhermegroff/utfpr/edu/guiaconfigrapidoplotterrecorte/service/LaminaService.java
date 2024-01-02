package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class LaminaService {

    private Context context;

    public LaminaService(Context context) {
        this.context = context;
    }

    public List<Lamina> listar() {
        PlotterDatabase plotterDatabase = PlotterDatabase.getDatabase(context);
        return plotterDatabase.laminaDao().findAll();
    }

}

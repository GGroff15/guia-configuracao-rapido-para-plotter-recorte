package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class TapeteService {

    private Context context;

    public TapeteService(Context context) {
       this.context = context;
    }

    public List<Tapete> listar() {
        PlotterDatabase plotterDatabase = PlotterDatabase.getDatabase(context);
        return plotterDatabase.tapeteDao().findAll();
    }

}

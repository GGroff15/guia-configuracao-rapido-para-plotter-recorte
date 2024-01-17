package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.ProcessDao;

public class ProcessService {

    private final ProcessDao processoDao;

    public ProcessService(Context context) {
        this.processoDao = PlotterDatabase.getDatabase(context).processoDao();
    }

    public void save(Processo processo) {
        processoDao.insert(processo);
    }

    public void update(Processo processo) {
        processoDao.update(processo);
    }

    public LiveData<List<Processo>> getItens(String tipo) {
        return processoDao.getItens(tipo);
    }

    public LiveData<List<Processo>> getItens(String tipo, String brand) {
        return processoDao.getItens(tipo, "%" + brand + "%");
    }
}

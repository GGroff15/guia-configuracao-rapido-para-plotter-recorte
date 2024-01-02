package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.ProcessoDao;

public class ProcessoService {

    private final ProcessoDao processoDao;

    public ProcessoService(Context context) {
        this.processoDao = PlotterDatabase.getDatabase(context).processoDao();
    }

    public List<Processo> listar() {
        return processoDao.findAll();
    }

    public List<Processo> listarPorTipo(String tipo) {
        return processoDao.findAllByTipo(tipo);
    }

    public void salvar(Processo processo) {
        processoDao.insert(processo);
    }

    public void atualizar(Processo processo) {
        processoDao.update(processo);
    }
    
}

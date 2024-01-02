package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.MaterialDao;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.ProcessoDao;

public class ProcessoService {

    private final ProcessoDao processoDao;
    private final MaterialDao materialDao;

    public ProcessoService(Context context) {
        this.processoDao = PlotterDatabase.getDatabase(context).processoDao();
        this.materialDao = PlotterDatabase.getDatabase(context).materialDao();
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

    public List<Processo> listarPorTipoNome(String tipo, String nome) {
        List<Material> listaMateriais = materialDao.findByNome("%" + nome + "%");
        List<Integer> listaIdsMaterial = listaMateriais.stream().map(Material::getId).collect(Collectors.toList());
        return processoDao.findAllByTipoAndMaterial(tipo, listaIdsMaterial);
    }

    public List<Processo> listarPorTipoGramatura(String tipo, String gramatura) {
        List<Material> listaMateriais = materialDao.findByGramatura(Integer.valueOf(gramatura));
        List<Integer> listaIdsMaterial = listaMateriais.stream().map(Material::getId).collect(Collectors.toList());
        return processoDao.findAllByTipoAndMaterial(tipo, listaIdsMaterial);
    }

    public List<Processo> listarPorTipoNomeGramatura(String tipo, String nome, String gramatura) {
        List<Material> listaMateriais = materialDao.findByNomeGramatura(nome, Integer.valueOf(gramatura));
        List<Integer> listaIdsMaterial = listaMateriais.stream().map(Material::getId).collect(Collectors.toList());
        return processoDao.findAllByTipoAndMaterial(tipo, listaIdsMaterial);
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.adapter.ProcessoCorteAdapter;

public class ProcessoCorteFragment extends ProcessoFragment {

    public ProcessoCorteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.operacao = ProcessoEnum.CORTE;
        this.service = new ProcessoService(this.getContext());
        return inflater.inflate(R.layout.fragment_processo_corte_list, container, false);
    }

    @Override
    public void carregarProcessos(String nome, String gramatura) {
        AsyncTask.execute(() -> {
            List<Processo> processos = getProcessos(nome, gramatura);
            getActivity().runOnUiThread(() -> {
                adapter = new ProcessoCorteAdapter(getContext(), processos, getActivity());
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void filtrarElementos(String nome, String gramatura) {
        carregarProcessos(nome, gramatura);
    }

}
package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.adapter.ProcessoDesenhoAdapter;

public class ProcessoDesenhoFragment extends ProcessoFragment {

    public ProcessoDesenhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.operacao = ProcessoEnum.DESENHO;
        this.service = new ProcessoService(this.getContext());
        return inflater.inflate(R.layout.fragment_processo_desenho_list, container, false);
    }

    @Override
    protected void carregarProcessos(String nome, String gramatura) {
        AsyncTask.execute(() -> {
            List<Processo> processos = getProcessos(nome, gramatura);
            getActivity().runOnUiThread(() -> {
                adapter = new ProcessoDesenhoAdapter(getContext(), processos, getActivity());
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void filtrarElementos(String nome, String gramatura) {
        carregarProcessos(nome, gramatura);
    }
}
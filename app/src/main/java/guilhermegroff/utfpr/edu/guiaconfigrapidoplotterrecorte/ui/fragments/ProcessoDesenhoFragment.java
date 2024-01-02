package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;

/**
 * A fragment representing a list of Items.
 */
public class ProcessoDesenhoFragment extends ProcessoFragment {

    private ProcessoDesenhoAdapter adapter;

    public ProcessoDesenhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.service = new ProcessoService(this.getContext());
        return inflater.inflate(R.layout.fragment_processo_desenho_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        this.recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(getMaterialDividerItemDecoration(layoutManager));
        carregarProcessos();
        registerForContextMenu(recyclerView);
    }

    protected void carregarProcessos() {
        AsyncTask.execute(() -> {
            List<Processo> processos = this.service.listarPorTipo("Desenho");
            getActivity().runOnUiThread(() -> {
                adapter = new ProcessoDesenhoAdapter(getContext(), processos);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}
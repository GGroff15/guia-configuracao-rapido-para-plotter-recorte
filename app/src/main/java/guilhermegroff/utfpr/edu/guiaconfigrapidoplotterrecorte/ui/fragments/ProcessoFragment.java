package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;

public abstract class ProcessoFragment extends Fragment {

    protected ProcessoService service;
    protected RecyclerView recyclerView;
    protected ProcessoEnum operacao;

    protected ModalBottomSheet modalBottomSheet;

    @NonNull
    MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

    protected List<Processo> getProcessos(String nome, String gramatura) {
        if ((nome != null && !nome.isEmpty()) && (gramatura != null && !gramatura.isEmpty())) {
            return this.service.listarPorTipoNomeGramatura(operacao.getTipo(), nome, gramatura);
        }

        if ((nome != null && !nome.isEmpty()) && (gramatura == null || gramatura.isEmpty())) {
            return this.service.listarPorTipoNome(operacao.getTipo(), nome);
        }

        if ((nome == null || nome.isEmpty()) && (gramatura != null && !gramatura.isEmpty())) {
            return this.service.listarPorTipoGramatura(operacao.getTipo(), gramatura);
        }

        if ((nome == null || nome.isEmpty()) && (gramatura == null || gramatura.isEmpty())) {
            return this.service.listarPorTipo(operacao.getTipo());
        }

        return new ArrayList<>();
    }

    protected void carregarProcessos() {
        carregarProcessos("", "");
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

    protected abstract void carregarProcessos(String nome, String gramatura);

    public abstract void filtrarElementos(String gramatura, String nome);
}

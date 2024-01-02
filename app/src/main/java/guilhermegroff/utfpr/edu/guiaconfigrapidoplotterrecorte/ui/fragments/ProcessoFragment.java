package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.security.AccessController;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;

public abstract class ProcessoFragment extends Fragment {

    protected ProcessoService service;
    protected RecyclerView recyclerView;

    @NonNull
    MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.ProcessAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet.ModalBottomSheetProcessFilter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public abstract class ProcessoFragment extends Fragment {

    protected ProcessListViewModel viewModel;

    protected ProcessService service;
    protected RecyclerView recyclerView;
    protected ProcessoEnum operacao;
    protected ViewModelFactory viewModelFactory;

    protected ModalBottomSheetProcessFilter modalBottomSheetProcessFilter;

    protected ProcessAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        this.recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(getMaterialDividerItemDecoration(layoutManager));
    }

    @NonNull
    MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

    public ProcessListViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(ProcessListViewModel viewModel) {
        this.viewModel = viewModel;
    }
}

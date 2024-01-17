package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.cut;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.cut.ProcessCutAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.ProcessoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.list.ProcessListFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessCutListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ProcessCutFragment extends ProcessoFragment {

    private static ProcessCutFragment instance;

    public static ProcessCutFragment getInstance() {
        if (instance == null) {
            instance = new ProcessCutFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.operacao = ProcessoEnum.CORTE;
        this.service = new ProcessService(this.getContext());
        this.viewModelFactory = new ViewModelFactory(getActivity());

        viewModel = viewModelFactory.createProcessCutListViewModel(service);
        viewModel.getProcessListLiveData().observe(getViewLifecycleOwner(), processList ->
                recyclerView.setAdapter(new ProcessCutAdapter(getContext(), processList, getActivity()))
        );

        return inflater.inflate(R.layout.fragment_processo_corte_list, container, false);
    }

}
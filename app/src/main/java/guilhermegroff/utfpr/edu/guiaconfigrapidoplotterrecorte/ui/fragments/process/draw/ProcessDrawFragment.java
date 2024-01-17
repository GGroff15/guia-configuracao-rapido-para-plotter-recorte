package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.draw;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.draw.ProcessDrawAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.ProcessoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessDrawListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ProcessDrawFragment extends ProcessoFragment {

    private static ProcessDrawFragment instance;

    public static ProcessDrawFragment getInstance() {
        if (instance == null) {
            instance = new ProcessDrawFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.operacao = ProcessoEnum.DESENHO;
        this.service = new ProcessService(this.getContext());
        this.viewModelFactory = new ViewModelFactory(getActivity());

        ProcessDrawListViewModel viewModel = viewModelFactory.createProcessDrawListViewModel(service);
        viewModel.getProcessListLiveData().observe(getViewLifecycleOwner(), processList -> recyclerView.setAdapter(new ProcessDrawAdapter(getContext(), processList, getActivity())));

        return inflater.inflate(R.layout.fragment_processo_desenho_list, container, false);
    }
}
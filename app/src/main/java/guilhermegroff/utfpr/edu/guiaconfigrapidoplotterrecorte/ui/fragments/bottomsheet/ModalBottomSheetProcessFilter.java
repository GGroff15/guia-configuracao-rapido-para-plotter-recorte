package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.ProcessoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.cut.ProcessCutFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.draw.ProcessDrawFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessCutListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessDrawListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ModalBottomSheetProcessFilter extends BottomSheetDialogFragment {

    public static final String PROCESS_TYPE = "ProcessType";
    private ProcessService service;
    private ViewModelFactory viewModelFactory;
    private Intent intent;
    private TextInputEditText inputEditTextNome;
    private Button buttonSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.service = new ProcessService(getContext());
        this.viewModelFactory = new ViewModelFactory(this);
        return inflater.inflate(R.layout.modal_bottom_sheet_process_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.inputEditTextNome = view.findViewById(R.id.input_filtro_nome);
        this.buttonSearch = view.findViewById(R.id.filtro_search);
        this.buttonSearch.setOnClickListener(v -> {
            getProcessList().getViewModel().update(inputEditTextNome.getText().toString());
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private ProcessoFragment getProcessList() {
        String processType = intent.getExtras().getString("ProcessType", "");

        if (processType.equals(ProcessoEnum.CORTE.getTipo())) {
            return ProcessCutFragment.getInstance();
        }

        if (processType.equals(ProcessoEnum.DESENHO.getTipo())) {
            return ProcessDrawFragment.getInstance();
        }

        return ProcessCutFragment.getInstance();
    }

    public void show(String processType, FragmentManager fragmentManager, String tag) {
        this.intent = new Intent();
        intent.putExtra(PROCESS_TYPE, processType);
        show(fragmentManager, tag);
    }


}

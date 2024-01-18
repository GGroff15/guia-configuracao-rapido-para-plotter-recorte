package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.BladeListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ModalBottomSheetAddBlade extends ModalBottomSheetAdd {

    private LaminaService service;
    private Blade lamina;
    private BladeListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.service = new LaminaService(this.getContext());
        viewModel = new ViewModelFactory(requireActivity()).createBladeListViewModel(service);
        return inflater.inflate(R.layout.modal_bottom_sheet_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.intent.getExtras();
        this.mode = bundle.getInt(MODE, NEW);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.lamina = new Blade("", "");
            }

            if (mode == EDIT) {
                int id = bundle.getInt(ID);
                this.lamina = service.buscar(id);
            }

            getActivity().runOnUiThread(() -> {
                this.inputEditTextColor.setText(lamina.getCor());
                this.inputEditTextStrenght.setText(lamina.getTipoMaterial());
            });
        });
    }

    protected void save(View v) {
        String color = inputEditTextColor.getText().toString();
        String cutType = inputEditTextStrenght.getText().toString();

        this.lamina.setCor(color);
        this.lamina.setTipoMaterial(cutType);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.service.save(lamina);
            }

            if (mode == EDIT) {
                this.service.update(lamina);
            }

            viewModel.update();
            dismiss();
        });
    }

    public void edit(FragmentManager manager, String tag, Blade lamina) {
        this.intent = new Intent();
        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID, lamina.getId());
        show(manager, tag);
    }
}

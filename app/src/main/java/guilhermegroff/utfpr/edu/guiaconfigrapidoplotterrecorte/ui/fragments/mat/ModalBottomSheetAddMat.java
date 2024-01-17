package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.mat;

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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.TapeteService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.MatListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ModalBottomSheetAddMat extends ModalBottomSheetAdd {

    private TapeteService service;
    private Mat mat;
    private MatListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.service = new TapeteService(getContext());
        viewModel = new ViewModelFactory(requireActivity()).createMatListViewModel(service);
        return inflater.inflate(R.layout.modal_bottom_sheet_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.intent.getExtras();
        this.mode = bundle.getInt(MODE, NEW);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.mat = new Mat("", "");
            }

            if (mode == EDIT) {
                int id = bundle.getInt(ID);
                this.mat = service.search(id);
            }

            getActivity().runOnUiThread(() -> {
                this.inputEditTextColor.setText(mat.getCor());
                this.inputEditTextStrenght.setText(mat.getForcaAderencia());
            });
        });
    }

    @Override
    protected void save(View view) {
        String color = inputEditTextColor.getText().toString();
        String strength = inputEditTextStrenght.getText().toString();

        this.mat.setCor(color);
        this.mat.setForcaAderencia(strength);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.service.save(mat);
            }

            if (mode == EDIT) {
                this.service.update(mat);
            }

            viewModel.update();
            dismiss();
        });
    }

    public void edit(FragmentManager manager, String tag, Mat mat) {
        this.intent = new Intent();
        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID, mat.getId());
        show(manager, tag);
    }

}

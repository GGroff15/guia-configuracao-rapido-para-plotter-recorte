package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.ListaLaminaActivity;

public class ModalBottomSheetAddBlade extends BottomSheetDialogFragment {

    private static final int EDIT = 1;
    private static final int NEW = 0;
    private static final String MODE = "Modo";
    public static final String ID = "ID";
    private Intent intent;
    private LaminaService service;
    private TextInputEditText inputEditTextColor;
    private TextInputEditText inputEditTextCutType;
    private Button buttonSave;
    private Button buttonClear;
    private Lamina lamina;
    private int mode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.service = new LaminaService(this.getContext());
        return inflater.inflate(R.layout.modal_bottom_sheet_add_blade, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.inputEditTextColor = view.findViewById(R.id.input_blade_color);
        this.inputEditTextCutType = view.findViewById(R.id.input_blade_cut_strength);

        this.buttonSave = view.findViewById(R.id.add);
        this.buttonSave.setOnClickListener(this::save);

        this.buttonClear = view.findViewById(R.id.clear);
        this.buttonClear.setOnClickListener(this::clear);

        Bundle bundle = this.intent.getExtras();
        this.mode = bundle.getInt(MODE, NEW);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.lamina = new Lamina("", "");
            }

            if (mode == EDIT) {
                int id = bundle.getInt(ID);
                this.lamina = service.buscar(id);
            }

            getActivity().runOnUiThread(() -> {
                this.inputEditTextColor.setText(lamina.getCor());
                this.inputEditTextCutType.setText(lamina.getTipoMaterial());
            });
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void save(View v) {
        String color = inputEditTextColor.getText().toString();
        String cutType = inputEditTextCutType.getText().toString();

        this.lamina.setCor(color);
        this.lamina.setTipoMaterial(cutType);

        AsyncTask.execute(() -> {
            if (mode == NEW) {
                this.service.save(lamina);
            }

            if (mode == EDIT) {
                this.service.update(lamina);
            }

            ListaLaminaActivity activity = (ListaLaminaActivity) getActivity();
            activity.carregarLaminas();
            dismiss();
        });
    }

    private void clear(View view) {
        inputEditTextColor.setText("");
        inputEditTextCutType.setText("");
    }

    public void newBlade(FragmentManager manager, String tag) {
        this.intent = new Intent();
        intent.putExtra(MODE, NEW);
        show(manager, tag);
    }

    public void editBlade(FragmentManager manager, String tag, Lamina lamina) {
        this.intent = new Intent();
        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID, lamina.getId());
        show(manager, tag);
    }
}

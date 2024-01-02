package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.ListaProcessoActivity;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    private TextInputEditText inputEditTextNome;
    private TextInputEditText inputEditTextGramatura;
    private Button buttonSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modal_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.inputEditTextNome = view.findViewById(R.id.input_filtro_nome);
        this.inputEditTextGramatura = view.findViewById(R.id.input_filtro_gramatura);
        this.buttonSearch = view.findViewById(R.id.filtro_search);
        this.buttonSearch.setOnClickListener(v -> {
            filtrar();
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void filtrar() {
        String gramatura = inputEditTextNome.getText().toString();
        String nome = inputEditTextGramatura.getText().toString();

        ListaProcessoActivity activity = (ListaProcessoActivity) getActivity();
        activity.filtrarElementos(nome, gramatura);
    }

}

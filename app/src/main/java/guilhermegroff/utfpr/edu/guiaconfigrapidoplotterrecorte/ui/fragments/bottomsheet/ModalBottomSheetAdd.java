package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;

public abstract class ModalBottomSheetAdd extends BottomSheetDialogFragment {

    public static final String ID = "ID";
    protected static final int EDIT = 1;
    protected static final int NEW = 0;
    protected static final String MODE = "Modo";
    protected Intent intent;
    protected Button buttonSave;
    protected Button buttonClear;
    protected TextInputEditText inputEditTextColor;
    protected TextInputEditText inputEditTextStrenght;
    protected TextView title;
    protected int mode;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.title = view.findViewById(R.id.bottom_sheet_add_title);

        this.inputEditTextColor = view.findViewById(R.id.input_color);
        this.inputEditTextStrenght = view.findViewById(R.id.input_strength);

        this.buttonSave = view.findViewById(R.id.add);
        this.buttonSave.setOnClickListener(this::save);

        this.buttonClear = view.findViewById(R.id.clear);
        this.buttonClear.setOnClickListener(this::clear);
    }

    protected abstract void save(View view);

    protected void clear(View view) {
        inputEditTextColor.setText("");
        inputEditTextStrenght.setText("");
    }

    public void add(FragmentManager manager, String tag) {
        this.intent = new Intent();
        intent.putExtra(MODE, NEW);
        show(manager, tag);
    }

}

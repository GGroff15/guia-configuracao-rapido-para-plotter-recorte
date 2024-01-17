package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.appbar.MaterialToolbar;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;

public class ProcessCreationFragmentHelper {

    private final ProcessCreationFragment fragment;

    public ProcessCreationFragmentHelper(ProcessCreationFragment fragment) {
        this.fragment = fragment;
    }

    public void setupActionBar() {
        FragmentActivity fragmentActivity = fragment.requireActivity();
        MaterialToolbar toolbar = fragmentActivity.findViewById(R.id.topAppBar);

        AppCompatActivity activity = (AppCompatActivity) fragmentActivity;
        activity.setSupportActionBar(toolbar);

        if (activity.getSupportActionBar() != null) {
            ActionBar supportActionBar = activity.getSupportActionBar();
            supportActionBar.setDisplayHomeAsUpEnabled(true);

            if (fragment.getModo() == ProcessCreationFragment.ModeEnum.NEW.getCode()) {
                supportActionBar.setTitle(fragment.getString(R.string.novo_processo));
            } else {
                supportActionBar.setTitle(fragment.getString(R.string.alterar_processo));
            }
        }
    }
}

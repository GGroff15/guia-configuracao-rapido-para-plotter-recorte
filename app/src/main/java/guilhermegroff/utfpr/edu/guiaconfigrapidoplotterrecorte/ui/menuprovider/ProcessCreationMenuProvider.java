package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.menuprovider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation.ProcessCreationFragment;

public class ProcessCreationMenuProvider implements MenuProvider {

    private ProcessCreationFragment fragment;

    public ProcessCreationMenuProvider(ProcessCreationFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.process_creation_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItemSalvar) {
            fragment.save(item);
            return true;
        }

        if (item.getItemId() == R.id.menuItemLimpar) {
            fragment.clear(item);
            return true;
        }
        return false;
    }

}

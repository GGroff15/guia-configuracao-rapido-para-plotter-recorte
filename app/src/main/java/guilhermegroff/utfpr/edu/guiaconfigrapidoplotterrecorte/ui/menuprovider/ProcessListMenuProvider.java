package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.menuprovider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ThemeService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet.ModalBottomSheetProcessFilter;

public class ProcessListMenuProvider implements MenuProvider {

    private final ThemeService temaUtil;
    private final TabLayout tabLayout;
    private final ModalBottomSheetProcessFilter modalBottomSheetProcessFilter;
    private final FragmentManager fragmentManager;

    public ProcessListMenuProvider(final ThemeService temaUtil, TabLayout tabLayout, ModalBottomSheetProcessFilter modalBottomSheetProcessFilter, FragmentManager fragmentManager) {
        this.temaUtil = temaUtil;
        this.tabLayout = tabLayout;
        this.modalBottomSheetProcessFilter = modalBottomSheetProcessFilter;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.app_options_menu, menu);
        menuInflater.inflate(R.menu.process_list_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.tema_escuro) {
            menuItem.setChecked(true);
            temaUtil.salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        }
        if (menuItem.getItemId() == R.id.tema_claro) {
            menuItem.setChecked(true);
            temaUtil.salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        }
        if (menuItem.getItemId() == R.id.menuItemFiltrar) {
            String tabName = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
            modalBottomSheetProcessFilter.show(tabName, fragmentManager, "BottomProcessFilter");
        }
        return false;
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {
        MenuItem item;
        if (temaUtil.getOpcaoTema() == AppCompatDelegate.MODE_NIGHT_YES) {
            item = menu.findItem(R.id.tema_escuro);
            item.setChecked(true);
        }
        if (temaUtil.getOpcaoTema() == AppCompatDelegate.MODE_NIGHT_NO) {
            item = menu.findItem(R.id.tema_claro);
            item.setChecked(true);
        }
    }
}
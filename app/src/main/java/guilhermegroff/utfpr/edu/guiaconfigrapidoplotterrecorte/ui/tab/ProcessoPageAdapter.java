package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ProcessoCorteFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ProcessoDesenhoFragment;

public class ProcessoPageAdapter extends FragmentStatePagerAdapter {

    private int numeroTabs;

    public ProcessoPageAdapter(@NonNull FragmentManager fm, int numeroTabs) {
        super(fm);
        this.numeroTabs = numeroTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProcessoCorteFragment();
            case 1:
                return new ProcessoDesenhoFragment();
            default:
                return new ProcessoCorteFragment();
        }
    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}

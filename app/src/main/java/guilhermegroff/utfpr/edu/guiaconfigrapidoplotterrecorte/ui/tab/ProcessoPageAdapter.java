package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.ProcessoCorteFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.ProcessoDesenhoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ProcessoFragment;

public class ProcessoPageAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
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

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public ProcessoFragment getRegisteredFragments(int position) {
        return (ProcessoFragment) registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return numeroTabs;
    }

}

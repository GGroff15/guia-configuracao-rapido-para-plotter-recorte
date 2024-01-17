package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.cut.ProcessCutFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.draw.ProcessDrawFragment;

public class ProcessSlidePagerAdapter extends FragmentStateAdapter {

    public ProcessSlidePagerAdapter(Fragment processListFragmentActivity) {
        super(processListFragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ProcessCutFragment.getInstance();
            case 1:
                return ProcessDrawFragment.getInstance();
            default:
                return ProcessCutFragment.getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ThemeService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ModalBottomSheetProcessFilter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation.ProcessCreationFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.menuprovider.ProcessListMenuProvider;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.ProcessSlidePagerAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessListViewModel;

public class ProcessListFragment extends Fragment {

    private ThemeService temaUtil;
    private ModalBottomSheetProcessFilter modalBottomSheetProcessFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        temaUtil = new ThemeService(getActivity());
        temaUtil.lerPreferenciaTema();

        modalBottomSheetProcessFilter = new ModalBottomSheetProcessFilter();

        requireActivity().invalidateOptionsMenu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_process_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ProcessSlidePagerAdapter pagerAdapter = new ProcessSlidePagerAdapter(this);

        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new ProcessTabLayoutMediatorImpl());
        tabLayoutMediator.attach();

        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(this::navigateToCreationFragment);

        requireActivity().addMenuProvider(new ProcessListMenuProvider(temaUtil, tabLayout, modalBottomSheetProcessFilter, getParentFragmentManager()), getViewLifecycleOwner());
    }

    private void navigateToCreationFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(ProcessCreationFragment.MODE, ProcessCreationFragment.ModeEnum.NEW.getCode());
        NavController navController1 = Navigation.findNavController(view);
        navController1.navigate(R.id.action_processListFragment_to_processCreationFragment, bundle);
    }

}

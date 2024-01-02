package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab;

import android.os.AsyncTask;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;

public class TabSelectedListenerImpl implements TabLayout.OnTabSelectedListener {


    private final ViewPager viewPager;

    public TabSelectedListenerImpl(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.list;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;

public class ProcessTabLayoutMediatorImpl implements TabLayoutMediator.TabConfigurationStrategy {

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        if (position == 0) {
            tab.setText(ProcessoEnum.CORTE.getTipo());
        }

        if (position == 1) {
            tab.setText(ProcessoEnum.DESENHO.getTipo());
        }
    }

}

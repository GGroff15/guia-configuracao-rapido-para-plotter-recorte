package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;

public class InterfaceComponentsHelper {

    @NonNull
    public MaterialDividerItemDecoration getMaterialDividerItemDecoration(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

}

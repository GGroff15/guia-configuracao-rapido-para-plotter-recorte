package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.blades;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.blade.BladeAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.bottomsheet.ModalBottomSheetAddBlade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.BladeListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class BladeListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ModalBottomSheetAddBlade modalBottomSheetAddBlade;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blade_list, container, false);

        LaminaService service = new LaminaService(getContext());

        recyclerView = view.findViewById(R.id.list_blades);

        BladeListViewModel mViewModel = new ViewModelFactory(getActivity()).createBladeListViewModel(service);
        mViewModel.getBladeList().observe(getViewLifecycleOwner(), blades -> {
            recyclerView.setAdapter(new BladeAdapter(blades, getActivity(), getContext(), modalBottomSheetAddBlade));
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        recyclerView.addItemDecoration(getMaterialDividerItemDecoration(layout));

        getModalBottomSheet();

        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(v -> adicionarLamina());
    }

    private MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

    private void getModalBottomSheet() {
        if (modalBottomSheetAddBlade == null) {
            modalBottomSheetAddBlade = new ModalBottomSheetAddBlade();
        }
    }

    private void adicionarLamina() {
        modalBottomSheetAddBlade.add(requireActivity().getSupportFragmentManager(), "BottomSheetAddBlade");
    }
}
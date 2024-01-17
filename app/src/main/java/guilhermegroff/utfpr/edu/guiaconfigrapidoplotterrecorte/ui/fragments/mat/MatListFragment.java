package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.mat;

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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.TapeteService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.mat.MatAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.MatListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class MatListFragment extends Fragment {

    private MatListViewModel mViewModel;
    private TapeteService service;
    private RecyclerView recyclerView;
    private ModalBottomSheetAddMat modalBottomSheetAddMat;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mat_list, container, false);
        this.service = new TapeteService(getContext());
        this.recyclerView = view.findViewById(R.id.list_mats);
        mViewModel = new ViewModelFactory(requireActivity()).createMatListViewModel(service);
        mViewModel.getListMat().observe(getViewLifecycleOwner(), matList -> {
            recyclerView.setAdapter(new MatAdapter(matList, requireActivity(), getContext(), modalBottomSheetAddMat));
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
        fab.setOnClickListener(v -> addMat());
    }

    private MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

    private void getModalBottomSheet() {
        if (modalBottomSheetAddMat == null) {
            modalBottomSheetAddMat = new ModalBottomSheetAddMat();
        }
    }

    private void addMat() {
        modalBottomSheetAddMat.add(requireActivity().getSupportFragmentManager(), "BottomSheetAddBlade");
    }
}
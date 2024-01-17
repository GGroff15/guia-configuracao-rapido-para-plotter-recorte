package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.blade;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.dialog.DeleteConfirmationDialog;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.blades.ModalBottomSheetAddBlade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class BladeAdapter extends RecyclerView.Adapter<BladeAdapter.LaminaHolder> {

    private final List<Blade> bladeList;
    private final FragmentActivity activity;
    private final Context context;
    private int selectedPosition;
    private final ModalBottomSheetAddBlade modalBottomSheetAdd;

    public static class LaminaHolder extends RecyclerView.ViewHolder {

        public TextView cor;
        public TextView cutStrength;

        public LaminaHolder(@NonNull View itemView) {
            super(itemView);
            this.cor = itemView.findViewById(R.id.cor);
            this.cutStrength = itemView.findViewById(R.id.cut_strength);
        }
    }

    public BladeAdapter(List<Blade> bladeList, FragmentActivity activity, Context context, ModalBottomSheetAddBlade modalBottomSheetAddBlade) {
        this.bladeList = bladeList;
        this.activity = activity;
        this.context = context;
        this.modalBottomSheetAdd = modalBottomSheetAddBlade;
    }

    @NonNull
    @Override
    public LaminaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamina, parent, false);
        return new LaminaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaminaHolder holder, int position) {
        Blade blade = bladeList.get(position);

        activity.runOnUiThread(() -> {
            holder.cor.setText(blade.getCor());
            holder.cutStrength.setText(blade.getTipoMaterial());
        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getBindingAdapterPosition();
            return false;
        });

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, v.getId(), 0, context.getString(R.string.edit)).setOnMenuItemClickListener(menuItem -> {
                edit(selectedPosition);
                return true;
            });
            menu.add(0, v.getId(), 0, context.getString(R.string.delete)).setOnMenuItemClickListener(menuItem -> {
                delete(selectedPosition);
                //new ViewModelFactory(activity).createProcessCutListViewModel(new ProcessService(context)).update();
                return true;
            });
        });

    }

    @Override
    public long getItemId(int position) {
        return bladeList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return bladeList.size();
    }

    protected void edit(int position) {
        modalBottomSheetAdd.edit(activity.getSupportFragmentManager(), "BottomSheetAddBlade", bladeList.get(position));
    }

    protected void delete(int position) {
        Blade lamina = bladeList.get(position);

        Runnable runnable = () -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(context);
            database.laminaDao().delete(lamina);
        };

        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(context, runnable);
        dialog.show();
    }
}

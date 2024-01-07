package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ModalBottomSheetAddBlade;

public class LaminaAdapter extends RecyclerView.Adapter<LaminaAdapter.LaminaHolder> {

    private final List<Lamina> laminas;
    private final Activity activity;
    private final Context context;
    private int selectedPosition;
    private Lamina selectedLamina;

    public static class LaminaHolder extends RecyclerView.ViewHolder {

        public TextView cor;
        public TextView tipoCorte;

        public LaminaHolder(@NonNull View itemView) {
            super(itemView);
            this.cor = itemView.findViewById(R.id.cor);
            this.tipoCorte = itemView.findViewById(R.id.cut_strength);
        }
    }

    public LaminaAdapter(List<Lamina> laminas, Activity activity, Context context) {
        this.laminas = laminas;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public LaminaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamina, parent, false);
        return new LaminaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaminaHolder holder, int position) {
        Lamina lamina = laminas.get(position);

        AsyncTask.execute(() -> {
            activity.runOnUiThread(() -> {
                holder.cor.setText(lamina.getCor());
                holder.tipoCorte.setText(lamina.getTipoMaterial());
            });
        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getBindingAdapterPosition();
            selectedLamina = laminas.get(selectedPosition);
            return false;
        });

        /*
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_editar)).setOnMenuItemClickListener(menuItem -> {
                editar(selectedPosition);
                return true;
            });
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_excluir)).setOnMenuItemClickListener(menuItem -> {
                excluir(selectedPosition);
                return true;
            });
        });

         */
    }



    @Override
    public long getItemId(int position) {
        return laminas.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return laminas.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public Lamina getSelectedLamina() {
        return selectedLamina;
    }
}

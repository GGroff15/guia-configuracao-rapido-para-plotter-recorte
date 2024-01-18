package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.draw;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process.ProcessAdapter;

public class ProcessDrawAdapter extends ProcessAdapter<ProcessDrawAdapter.ProcessHolder> {

   public static class ProcessHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialName;
        public TextView textViewMaterialGramatura;
        public TextView textViewMatColor;
        public TextView textViewMatStrength;
        public TextView textViewPressure;
        public TextView textViewProcessType;
        public TextView textViewPenType;
        public TextView textViewIsTecido;

        public ProcessHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewMaterialName = itemView.findViewById(R.id.text_view_material_name);
            this.textViewMaterialGramatura = itemView.findViewById(R.id.text_view_gramatura);
            this.textViewMatColor = itemView.findViewById(R.id.text_view_mat_color);
            this.textViewMatStrength = itemView.findViewById(R.id.text_view_mat_strength);
            this.textViewPressure = itemView.findViewById(R.id.text_view_pressure);
            this.textViewProcessType = itemView.findViewById(R.id.text_view_is_corte);
            this.textViewIsTecido = itemView.findViewById(R.id.is_tecido);
            this.textViewPenType = itemView.findViewById(R.id.pen_type);
        }
    }
    public ProcessDrawAdapter(Context context, List<Processo> processList, FragmentActivity activity) {
        super(context, activity, processList);
    }

    @NonNull
    @Override
    public ProcessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_process_draw, parent, false);
        return new ProcessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessHolder holder, int position) {
        Processo process = processList.get(position);

        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(this.context);
            Mat mat = database.tapeteDao().findById(process.getTapete());

            activity.runOnUiThread(() -> {
                holder.textViewMaterialName.setText(process.getMaterial());
                holder.textViewMaterialGramatura.setText(String.valueOf(process.getGramatura()));
                holder.textViewMatColor.setText(mat.getCor());
                holder.textViewMatStrength.setText(mat.getForcaAderencia());
                holder.textViewPressure.setText(String.valueOf(process.getPressao()));
                holder.textViewProcessType.setText(process.getTipo());
                holder.textViewIsTecido.setText(process.getTecido());
                holder.textViewPenType.setText(process.getPen());
            });
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
                return true;
            });
        });
    }

    @Override
    public long getItemId(int i) {
        return processList.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return processList.size();
    }

}

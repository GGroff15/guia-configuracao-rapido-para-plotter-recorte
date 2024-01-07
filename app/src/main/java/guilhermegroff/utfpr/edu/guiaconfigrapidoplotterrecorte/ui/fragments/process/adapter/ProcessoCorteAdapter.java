package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.adapter;

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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class ProcessoCorteAdapter extends ProcessoAdapter<ProcessoCorteAdapter.ProcessoHolder> {

    public static class ProcessoHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialNome;
        public TextView textViewMaterialGramatura;
        public TextView textViewTapeteCor;
        public TextView textViewTapeteForca;
        public TextView textViewPressao;
        public TextView textViewIsCorte;
        public TextView textViewLaminaCor;
        public TextView textViewLaminaMaterial;
        public TextView textViewProfundidadeLamina;
        public TextView textViewIsTecido;

        public ProcessoHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewMaterialNome = itemView.findViewById(R.id.textViewMaterialNome);
            this.textViewMaterialGramatura = itemView.findViewById(R.id.textViewGramatura);
            this.textViewTapeteCor = itemView.findViewById(R.id.textViewTapeteCor);
            this.textViewTapeteForca = itemView.findViewById(R.id.textViewForcaTapete);
            this.textViewPressao = itemView.findViewById(R.id.textViewPressao);
            this.textViewIsCorte = itemView.findViewById(R.id.textViewIsCorte);
            this.textViewLaminaCor = itemView.findViewById(R.id.textViewLaminaCor);
            this.textViewLaminaMaterial = itemView.findViewById(R.id.textViewLaminaMaterial);
            this.textViewProfundidadeLamina= itemView.findViewById(R.id.textViewProfundidadeLamina);
            this.textViewIsTecido = itemView.findViewById(R.id.isTecido);
        }

    }
    public ProcessoCorteAdapter(Context context, List<Processo> processos, FragmentActivity activity) {
        super(context, activity, processos);
    }

    @NonNull
    @Override
    public ProcessoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_processo_corte, parent, false);
        return new ProcessoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessoHolder holder, int position) {
        Processo processo = processos.get(position);

        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(this.context);
            Tapete tapete = database.tapeteDao().findById(processo.getTapete());
            Material material = database.materialDao().findById(processo.getMaterial());
            Lamina lamina = database.laminaDao().findById(processo.getLamina());

            activity.runOnUiThread(() -> {
                holder.textViewMaterialNome.setText(material.getNome());
                holder.textViewMaterialGramatura.setText(String.valueOf(material.getGramatura()));
                holder.textViewTapeteCor.setText(tapete.getCor());
                holder.textViewTapeteForca.setText(tapete.getForcaAderencia());
                holder.textViewPressao.setText(String.valueOf(processo.getPressao()));
                holder.textViewIsCorte.setText(processo.getTipo());
                holder.textViewLaminaCor.setText(lamina.getCor());
                holder.textViewLaminaMaterial.setText(lamina.getTipoMaterial());
                holder.textViewProfundidadeLamina.setText(String.valueOf(processo.getProfundidadeLamina()));
                holder.textViewIsTecido.setText(processo.getTecido());
            });

        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getBindingAdapterPosition();
            return false;
        });

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
    }

    @Override
    public long getItemId(int i) {
        return processos.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return processos.size();
    }

}
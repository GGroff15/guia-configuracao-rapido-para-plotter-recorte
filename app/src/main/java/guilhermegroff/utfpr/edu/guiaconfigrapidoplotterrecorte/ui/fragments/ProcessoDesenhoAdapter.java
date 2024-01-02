package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class ProcessoDesenhoAdapter extends RecyclerView.Adapter<ProcessoDesenhoAdapter.ProcessoHolder> {

    private final Context context;
    private final List<Processo> processos;
    private final FragmentActivity activity;
    private int selectedPosition;

    public static class ProcessoHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialNome;
        public TextView textViewMaterialGramatura;
        public TextView textViewTapeteCor;
        public TextView textViewTapeteForca;
        public TextView textViewPressao;
        public TextView textViewIsCorte;
        public TextView textViewCanetaCor;
        public TextView textViewIsTecido;

        public ProcessoHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewMaterialNome = itemView.findViewById(R.id.textViewMaterialNome);
            this.textViewMaterialGramatura = itemView.findViewById(R.id.textViewGramatura);
            this.textViewTapeteCor = itemView.findViewById(R.id.textViewTapeteCor);
            this.textViewTapeteForca = itemView.findViewById(R.id.textViewForcaTapete);
            this.textViewPressao = itemView.findViewById(R.id.textViewPressao);
            this.textViewIsCorte = itemView.findViewById(R.id.textViewIsCorte);
            this.textViewCanetaCor = itemView.findViewById(R.id.textViewCanetaCor);
            this.textViewIsTecido = itemView.findViewById(R.id.isTecido);
        }
    }
    public ProcessoDesenhoAdapter(Context context, List<Processo> processos, FragmentActivity activity) {
        this.context = context;
        this.processos = processos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProcessoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_processo_desenho, parent, false);
        return new ProcessoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessoHolder holder, int position) {
        Processo processo = processos.get(position);

        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(this.context);

            Material material = database.materialDao().findById(processo.getMaterial());
            holder.textViewMaterialNome.setText(material.getNome());
            holder.textViewMaterialGramatura.setText(String.valueOf(material.getGramatura()));

            Tapete tapete = database.tapeteDao().findById(processo.getTapete());
            holder.textViewTapeteCor.setText(tapete.getCor());
            holder.textViewTapeteForca.setText(tapete.getForcaAderencia());

            holder.textViewPressao.setText(String.valueOf(processo.getPressao()));

            holder.textViewIsCorte.setText(processo.getTipo());

            if (processo.getCaneta() != null && processo.getCaneta() != 0) {
                Caneta caneta = database.canetaDao().findById(processo.getCaneta());
                holder.textViewCanetaCor.setText(caneta.getEspessura());
            } else {
                holder.textViewCanetaCor.setText("");
            }

            holder.textViewIsTecido.setText(processo.getTecido());
        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getBindingAdapterPosition();
            return false;
        });

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_editar));
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_excluir));
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class ProcessoCorteAdapter extends RecyclerView.Adapter<ProcessoCorteAdapter.ProcessoHolder> {

    private final Context context;
    private final List<Processo> processos;
    private FragmentActivity activity;
    private int selectedPosition;

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
        this.context = context;
        this.processos = processos;
        this.activity = activity;
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

            Material material = database.materialDao().findById(processo.getMaterial());
            holder.textViewMaterialNome.setText(material.getNome());
            holder.textViewMaterialGramatura.setText(String.valueOf(material.getGramatura()));

            Tapete tapete = database.tapeteDao().findById(processo.getTapete());
            holder.textViewTapeteCor.setText(tapete.getCor());
            holder.textViewTapeteForca.setText(tapete.getForcaAderencia());

            holder.textViewPressao.setText(String.valueOf(processo.getPressao()));

            holder.textViewIsCorte.setText(processo.getTipo());

            if (processo.getLamina() != null && processo.getLamina() != 0) {
                Lamina lamina = database.laminaDao().findById(processo.getLamina());
                holder.textViewLaminaCor.setText(lamina.getCor());
                holder.textViewLaminaMaterial.setText(lamina.getTipoMaterial());
                holder.textViewProfundidadeLamina.setText(String.valueOf(processo.getProfundidadeLamina()));
            } else {
                holder.textViewLaminaCor.setText("");
                holder.textViewLaminaMaterial.setText("");
                holder.textViewProfundidadeLamina.setText("");
            }

            holder.textViewIsTecido.setText(processo.getTecido());
        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getBindingAdapterPosition();
            return false;
        });

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_editar)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                    return false;
                }
            });
            menu.add(0, v.getId(), 0, context.getString(R.string.context_menu_item_excluir)).setOnMenuItemClickListener(menuItem -> {
                excluir(selectedPosition);
                return true;
            });
        });
    }

    private void excluir(int position) {
        // Processo processo = processos.get(position);
        String mensagem = this.context.getString(R.string.confirmar_excluir_processo);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(() -> {
                            PlotterDatabase database = PlotterDatabase.getDatabase(context);
                            //database.processoDao().delete(processo);

                            activity.runOnUiThread(() -> {
                                //processos.remove(position);
                                //listaAdapter.notifyDataSetChanged();
                            });
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onViewRecycled(@NonNull ProcessoHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
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

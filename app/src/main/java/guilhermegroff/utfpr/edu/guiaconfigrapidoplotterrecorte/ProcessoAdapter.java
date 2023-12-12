package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.LaminaDao;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class ProcessoAdapter extends BaseAdapter {

    private final Context context;
    private final List<Processo> processos;
    private View view;

    private static class ProcessoHolder {
        public TextView textViewMaterialNome;
        public TextView textViewMaterialGramatura;
        public TextView textViewMaterialMarca;
        public TextView textViewTapeteCor;
        public TextView textViewTapeteForca;
        public TextView textViewPressao;
        public TextView textViewIsCorte;
        public TextView textViewCanetaCor;
        public TextView textViewLaminaCor;
        public TextView textViewLaminaMaterial;
        public TextView textViewProfundidadeLamina;
        public TextView textViewIsTecido;
    }
    public ProcessoAdapter(Context context, List<Processo> processos) {
        this.context = context;
        this.processos = processos;
    }

    @Override
    public int getCount() {
        return processos.size();
    }

    @Override
    public Object getItem(int i) {
        return processos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        this.view = view;

        ProcessoHolder holder = getHolder(viewGroup);

        Processo processo = processos.get(i);

        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(this.view.getContext());

            Material material = database.materialDao().findById(processo.getMaterial());
            holder.textViewMaterialNome.setText(material.getNome());
            holder.textViewMaterialGramatura.setText(String.valueOf(material.getGramatura()));
            holder.textViewMaterialMarca.setText(material.getMarca());

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

        return this.view;
    }

    private ProcessoHolder getHolder(ViewGroup viewGroup) {
        if (view == null) {
            return criarNovoProcessoHolder(viewGroup);
        }
        return getHolderExistente();
    }

    private ProcessoHolder getHolderExistente() {
        return (ProcessoHolder) view.getTag();
    }

    @NonNull
    private ProcessoHolder criarNovoProcessoHolder(ViewGroup viewGroup) {
        ProcessoHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.linha_lista_processos, viewGroup, false);

        holder = new ProcessoHolder();

        holder.textViewMaterialNome = view.findViewById(R.id.textViewMaterialNome);
        holder.textViewMaterialGramatura = view.findViewById(R.id.textViewGramatura);
        holder.textViewMaterialMarca = view.findViewById(R.id.textViewMarca);
        holder.textViewTapeteCor = view.findViewById(R.id.textViewTapeteCor);
        holder.textViewTapeteForca = view.findViewById(R.id.textViewForcaTapete);
        holder.textViewPressao = view.findViewById(R.id.textViewPressao);
        holder.textViewIsCorte = view.findViewById(R.id.textViewIsCorte);
        holder.textViewCanetaCor = view.findViewById(R.id.textViewCanetaCor);
        holder.textViewLaminaCor = view.findViewById(R.id.textViewLaminaCor);
        holder.textViewLaminaMaterial = view.findViewById(R.id.textViewLaminaMaterial);
        holder.textViewProfundidadeLamina = view.findViewById(R.id.textViewProfundidadeLamina);
        holder.textViewIsTecido = view.findViewById(R.id.isTecido);

        view.setTag(holder);
        return holder;
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.CadastroActivity;

public abstract class ProcessoAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected final Context context;
    protected final List<Processo> processos;
    protected FragmentActivity activity;
    protected int selectedPosition;

    public ProcessoAdapter(Context context, FragmentActivity activity, List<Processo> processos) {
        this.context = context;
        this.activity = activity;
        this.processos = processos;
    }

    protected void editar(int position) {
        Processo processo = processos.get(position);
        CadastroActivity.editarProcesso((AppCompatActivity) activity, processo);
    }

    protected void excluir(int position) {
        Processo processo = processos.get(position);
        String mensagem = this.context.getString(R.string.confirmar_excluir_processo);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(() -> {
                            PlotterDatabase database = PlotterDatabase.getDatabase(context);
                            database.processoDao().delete(processo);

                            activity.runOnUiThread(() -> {
                                processos.remove(position);
                                notifyDataSetChanged();
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
}

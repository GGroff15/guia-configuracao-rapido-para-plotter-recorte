package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.dialog.DeleteConfirmationDialog;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation.ProcessCreationFragment;

public abstract class ProcessAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected final Context context;
    protected final List<Processo> processos;
    protected FragmentActivity activity;
    protected int selectedPosition;

    public ProcessAdapter(Context context, FragmentActivity activity, List<Processo> processos) {
        this.context = context;
        this.activity = activity;
        this.processos = processos;
    }

    public ProcessAdapter(Context context, FragmentActivity activity, List<Processo> processos, String brand) {
        this.context = context;
        this.activity = activity;
        this.processos = getFilteredProcessList(processos, brand);
    }

    private List<Processo> getFilteredProcessList(List<Processo> processos, String brand) {
       return new ArrayList<>();
    }

    protected void editar(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ProcessCreationFragment.MODE, ProcessCreationFragment.ModeEnum.EDIT.getCode());
        bundle.putInt("id", processos.get(position).getId());
        NavController navController = Navigation.findNavController(activity, R.id.fragment_container_view);
        navController.navigate(R.id.action_processListFragment_to_processCreationFragment, bundle);
    }

    protected void excluir(int position) {
        Processo processo = processos.get(position);

        Runnable runnable = () -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(context);
            database.processoDao().delete(processo);
        };

        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(context, runnable);
        dialog.show();
    }
}

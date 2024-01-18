package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.process;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.dialog.DeleteConfirmationDialog;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation.ProcessCreationFragment;

public abstract class ProcessAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected final Context context;
    protected final List<Processo> processList;
    protected FragmentActivity activity;
    protected int selectedPosition;

    public ProcessAdapter(Context context, FragmentActivity activity, List<Processo> processos) {
        this.context = context;
        this.activity = activity;
        this.processList = processos;
    }

    protected void edit(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ProcessCreationFragment.MODE, ProcessCreationFragment.ModeEnum.EDIT.getCode());
        bundle.putInt("id", processList.get(position).getId());
        NavController navController = Navigation.findNavController(activity, R.id.fragment_container_view);
        navController.navigate(R.id.action_processListFragment_to_processCreationFragment, bundle);
    }

    protected void delete(int position) {
        Processo process = processList.get(position);

        Runnable runnable = () -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(context);
            database.processoDao().delete(process);
        };

        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(context, runnable);
        dialog.show();
    }
}

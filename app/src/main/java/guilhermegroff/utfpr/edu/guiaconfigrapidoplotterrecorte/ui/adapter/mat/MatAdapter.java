package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.adapter.mat;

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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.dialog.DeleteConfirmationDialog;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.mat.ModalBottomSheetAddMat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class MatAdapter extends RecyclerView.Adapter<MatAdapter.MatHolder> {

    private final List<Mat> matList;
    private final FragmentActivity activity;
    private final Context context;
    private int selectedPosition;
    private final ModalBottomSheetAddMat modalBottomSheetAdd;

    public static class MatHolder extends RecyclerView.ViewHolder {

        public TextView color;
        public TextView gripStrength;

        public MatHolder(@NonNull View itemView) {
            super(itemView);
            this.color = itemView.findViewById(R.id.color);
            this.gripStrength = itemView.findViewById(R.id.grip_strength);
        }
    }

    public MatAdapter(List<Mat> matList, FragmentActivity activity, Context context, ModalBottomSheetAddMat modalBottomSheetAddBlade) {
        this.matList = matList;
        this.activity = activity;
        this.context = context;
        this.modalBottomSheetAdd = modalBottomSheetAddBlade;
    }

    @NonNull
    @Override
    public MatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mat, parent, false);
        return new MatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatHolder holder, int position) {
        Mat mat = matList.get(position);

        activity.runOnUiThread(() -> {
            holder.color.setText(mat.getCor());
            holder.gripStrength.setText(mat.getForcaAderencia());
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
                //new ViewModelFactory(activity).createProcessDrawListViewModel(new ProcessService(context)).update();
                return true;
            });
        });
    }

    @Override
    public int getItemCount() {
        return matList.size();
    }

    @Override
    public long getItemId(int position) {
        return matList.get(position).getId();
    }

    protected void edit(int position) {
        modalBottomSheetAdd.edit(activity.getSupportFragmentManager(), "BottomSheetAddMat", matList.get(position));
    }

    protected void delete(int position) {
        Mat mat = matList.get(position);

        Runnable runnable = () -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(context);
            database.tapeteDao().delete(mat);
        };

        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(context, runnable);
        dialog.show();
    }
}

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;

public class DeleteConfirmationDialog {

    private final Context context;
    private final Runnable runnable;

    public DeleteConfirmationDialog(Context context, Runnable runnable) {
        this.context = context;
        this.runnable = runnable;
    }

    public void show() {
        String mensagem = this.context.getString(R.string.confirmar_excluir_processo);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(runnable);
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

package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessoService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.adapter.ProcessoDesenhoAdapter;

public class ProcessoDesenhoFragment extends ProcessoFragment {

    private ProcessoDesenhoAdapter adapter;

    public ProcessoDesenhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.operacao = ProcessoEnum.DESENHO;
        this.service = new ProcessoService(this.getContext());
        return inflater.inflate(R.layout.fragment_processo_desenho_list, container, false);
    }

    @Override
    protected void carregarProcessos(String nome, String gramatura) {
        AsyncTask.execute(() -> {
            List<Processo> processos = getProcessos(nome, gramatura);
            getActivity().runOnUiThread(() -> {
                adapter = new ProcessoDesenhoAdapter(getContext(), processos, getActivity());
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.lista_menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int selectedPosition = adapter.getSelectedPosition();

        if (item.getItemId() == R.id.contextMenuItemEditar) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //editar(info.position);
            //posicaoSelecionada = info.position;
            return true;
        }

        if (item.getItemId() == R.id.contextMenuItemExcluir) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            excluir(info.position);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void excluir(int position) {
        // Processo processo = processos.get(position);
        String mensagem = getString(R.string.confirmar_excluir_processo);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(() -> {
                            PlotterDatabase database = PlotterDatabase.getDatabase(ProcessoDesenhoFragment.this.getContext());
                            //database.processoDao().delete(processo);

                            getActivity().runOnUiThread(() -> {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ProcessoDesenhoFragment.this.getContext());
        builder.setTitle(R.string.confirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void filtrarElementos(String nome, String gramatura) {
        carregarProcessos(nome, gramatura);
    }
}
package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class ListaProcessoActivity extends AppCompatActivity {

    private static final String ARQUIVO = "utfpr.edu.guiaconfiguracaorapidoplotterrecorte.sharedpreferences.PREFERENCIAS_TEMAS";
    private static final String TEMA = "TEMA";
    private ListView listViewProcessos;
    private List<Processo> processos;
    private ProcessoAdapter listaAdapter;
    private int posicaoSelecionada = -1;
    private int opcaoTema = AppCompatDelegate.MODE_NIGHT_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_processo);

        listViewProcessos = findViewById(R.id.listViewProcessos);
        listViewProcessos.setOnItemClickListener((adapterView, view, position, id) -> {
            Processo processo = (Processo)  adapterView.getItemAtPosition(position);

            String text = getText(R.string.procedimento_selecionado) + String.valueOf(processo.getMaterial()) + getText(R.string.com) + String.valueOf(processo.getMaterial()) + getString(R.string.gramas_marca) + String.valueOf(processo.getMaterial()) + getString(R.string.foi_clicado);
            Toast.makeText(getApplicationContext(), text,
                    Toast.LENGTH_SHORT).show();
        });
        registerForContextMenu(listViewProcessos);

        carregarProcessos();
        lerPreferenciaTema();
    }

    private void lerPreferenciaTema() {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        opcaoTema = sharedPreferences.getInt(TEMA, opcaoTema);
        AppCompatDelegate.setDefaultNightMode(opcaoTema);
    }

    private void salvarPreferenciaTema(int novoTema) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TEMA, novoTema);
        editor.commit();
        opcaoTema = novoTema;
        AppCompatDelegate.setDefaultNightMode(novoTema);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        if (opcaoTema == AppCompatDelegate.MODE_NIGHT_YES) {
            item = menu.findItem(R.id.tema_escuro);
            item.setChecked(true);
            return true;
        }
        if (opcaoTema == AppCompatDelegate.MODE_NIGHT_NO) {
            item = menu.findItem(R.id.tema_claro);
            item.setChecked(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_opcoes, menu);
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        if (item.getItemId() == R.id.tema_escuro) {
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        }
        if (item.getItemId() == R.id.tema_claro) {
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.lista_menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.contextMenuItemEditar) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            editar(info.position);
            posicaoSelecionada = info.position;
            return true;
        }

        if (item.getItemId() == R.id.contextMenuItemExcluir) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            excluir(info.position);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void editar(int position) {
        Processo processo = processos.get(position);
        CadastroActivity.editarProcesso(this, processo);
    }

    private void excluir(int position) {
        Processo processo = processos.get(position);
        String mensagem = getString(R.string.confirmar_excluir_processo);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(() -> {
                            PlotterDatabase database = PlotterDatabase.getDatabase(ListaProcessoActivity.this);
                            database.processoDao().delete(processo);

                            ListaProcessoActivity.this.runOnUiThread(() -> {
                                processos.remove(position);
                                listaAdapter.notifyDataSetChanged();
                            });
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaProcessoActivity.this);
        builder.setTitle(R.string.confirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void adicionarProcesso(MenuItem item) {
        CadastroActivity.novoProcesso(this);
    }

    public void abrirSobre(MenuItem item) {
        AutoriaAppActivity.sobre(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == CadastroActivity.NOVO || requestCode == CadastroActivity.ALTERAR)) {
            carregarProcessos();
        }
    }

    private void carregarProcessos() {
        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(ListaProcessoActivity.this);
            processos = database.processoDao().findAll();
            ListaProcessoActivity.this.runOnUiThread(() -> {
                listaAdapter = new ProcessoAdapter(ListaProcessoActivity.this, processos);
                listViewProcessos.setAdapter(listaAdapter);
            });
        });
    }
}
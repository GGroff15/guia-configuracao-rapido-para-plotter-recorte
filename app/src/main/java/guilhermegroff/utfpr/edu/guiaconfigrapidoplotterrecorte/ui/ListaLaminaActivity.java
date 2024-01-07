package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ModalBottomSheetAddBlade;

public class ListaLaminaActivity extends AppCompatActivity {

    private LaminaService service;
    private RecyclerView recyclerView;
    private List<Lamina> laminas;
    private ModalBottomSheetAddBlade modalBottomSheetAddBlade;
    private LaminaAdapter adapter;

    public static void laminas(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity, ListaLaminaActivity.class);
        appCompatActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lamina);
        this.service = new LaminaService(this);
        this.recyclerView = findViewById(R.id.list_blades);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layout);
        recyclerView.addItemDecoration(getMaterialDividerItemDecoration(layout));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        carregarLaminas();

        getModalBottomSheet();

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(v -> {
            adicionarLamina();
        });

        registerForContextMenu(recyclerView);
    }

    private void getModalBottomSheet() {
        if (modalBottomSheetAddBlade == null) {
            modalBottomSheetAddBlade = new ModalBottomSheetAddBlade();
        }
    }

    private void adicionarLamina() {
        modalBottomSheetAddBlade.newBlade(getSupportFragmentManager(), "BottomSheetAddBlade");
    }

    public void carregarLaminas() {
        AsyncTask.execute(() -> {
            laminas = service.listar();
            adapter = new LaminaAdapter(laminas, ListaLaminaActivity.this, this);
            ListaLaminaActivity.this.runOnUiThread(() -> {
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.lista_menu_contexto, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.contextMenuItemEditar) {
            editar();
        }

        if (item.getItemId() == R.id.contextMenuItemExcluir) {
            excluir();
        }
        return super.onContextItemSelected(item);
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        cancel();
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancel();
    }

    private void cancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @NonNull
    MaterialDividerItemDecoration getMaterialDividerItemDecoration(LinearLayoutManager layoutManager) {
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetStart(20);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        return materialDividerItemDecoration;
    }

    public void editar() {
        Lamina lamina = adapter.getSelectedLamina();
        modalBottomSheetAddBlade.editBlade(getSupportFragmentManager(), "BottomSheetEditBlade", lamina);
    }

    private void excluir() {
        Lamina lamina = adapter.getSelectedLamina();
        String mensagem = getString(R.string.confirmar_excluir_lamina);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        AsyncTask.execute(() -> {
                            PlotterDatabase database = PlotterDatabase.getDatabase(ListaLaminaActivity.this);
                            database.laminaDao().delete(lamina);

                            ListaLaminaActivity.this.runOnUiThread(() -> {
                                carregarLaminas();
                            });
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaLaminaActivity.this);
        builder.setTitle(R.string.confirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
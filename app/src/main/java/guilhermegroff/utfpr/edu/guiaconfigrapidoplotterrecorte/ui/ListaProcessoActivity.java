package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ModalBottomSheet;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ProcessoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab.ProcessoPageAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab.TabSelectedListenerImpl;

public class ListaProcessoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModalBottomSheet modalBottomSheet;
    private ProcessoPageAdapter processoPageAdapter;
    private TemaUtil temaUtil;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_processo);

        this.temaUtil = new TemaUtil(this);
        this.temaUtil.lerPreferenciaTema();

        viewPager = findViewById(R.id.pager);

        drawerLayout = findViewById(R.id.layoutLista);

        MaterialToolbar materialToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(materialToolbar);

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawerLayout.close();
            return true;
        });

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Corte"));
        tabLayout.addTab(tabLayout.newTab().setText("Desenho"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabSelectedListenerImpl(this.viewPager));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        processoPageAdapter = new ProcessoPageAdapter(supportFragmentManager, tabLayout.getTabCount());
        viewPager.setAdapter(processoPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        getModalBottomSheet();

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(v -> {
            adicionarProcesso(null);
        });
    }

    private void getModalBottomSheet() {
        if (modalBottomSheet == null) {
            modalBottomSheet = new ModalBottomSheet();
        }
    }

    public void filtrarElementos(String nome, String gramatura) {
        int currentItem = viewPager.getCurrentItem();
        ProcessoFragment fragment = processoPageAdapter.getRegisteredFragments(currentItem);
        if (fragment != null) {
            fragment.filtrarElementos(gramatura, nome);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        getMenuInflater().inflate(R.menu.lista_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        if (temaUtil.getOpcaoTema() == AppCompatDelegate.MODE_NIGHT_YES) {
            item = menu.findItem(R.id.tema_escuro);
            item.setChecked(true);
            return true;
        }
        if (temaUtil.getOpcaoTema() == AppCompatDelegate.MODE_NIGHT_NO) {
            item = menu.findItem(R.id.tema_claro);
            item.setChecked(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        if (item.getItemId() == R.id.tema_escuro) {
            this.temaUtil.salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        }
        if (item.getItemId() == R.id.tema_claro) {
            this.temaUtil.salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        drawerLayout.open();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.contextMenuItemEditar) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            editar(info.position);
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
        //Processo processo = processos.get(position);
        //CadastroActivity.editarProcesso(this, processo);
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
                            PlotterDatabase database = PlotterDatabase.getDatabase(ListaProcessoActivity.this);
                            //database.processoDao().delete(processo);

                            ListaProcessoActivity.this.runOnUiThread(() -> {
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

    public void abrirFiltro(MenuItem item) {
        modalBottomSheet.show(getSupportFragmentManager(), "BottomSheetProcessFilter");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == CadastroActivity.NOVO || requestCode == CadastroActivity.ALTERAR)) {
            viewPager.setCurrentItem(0);
        }
    }

}
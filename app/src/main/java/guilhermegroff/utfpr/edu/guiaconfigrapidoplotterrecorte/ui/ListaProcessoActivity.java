package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ModalBottomSheetProcessFilter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.ProcessoFragment;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab.ProcessoPageAdapter;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.tab.TabSelectedListenerImpl;

public class ListaProcessoActivity extends AppCompatActivity {

    private TemaUtil temaUtil;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_processo);

        this.temaUtil = new TemaUtil(this);
        this.temaUtil.lerPreferenciaTema();

        drawerLayout = findViewById(R.id.layoutLista);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawerLayout.close();
            return true;
        });

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

    public void abrirSobre(MenuItem item) {
        AutoriaAppActivity.sobre(this);
    }

    public void abrirListaLaminas(MenuItem item) {
        ListaLaminaActivity.laminas(this);
    }

}
package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;

public class CadastroActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    private static final String DESENHO = "Desenho";
    private static final String CORTE = "Corte";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    public static final String SIM = "Sim";
    public static final String NAO = "Não";

    private int modo;
    private EditText inputNomeMaterial;
    private EditText inputGramaturaMaterial;
    private EditText inputMarcaMaterial;
    private EditText inputPressao;
    private Spinner tipoTapete;
    private RadioGroup radioGroupOperacao;
    private EditText inputEspessuraCaneta;
    private EditText inputProfundidadeLamina;
    private Spinner corLamina;
    private CheckBox checkBoxIsTecido;
    private List<Tapete> listaTapetes;
    private List<Lamina> listaLaminas;
    private Processo processo;

    public static void novoProcesso(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity, CadastroActivity.class);
        intent.putExtra(MODO, NOVO);
        appCompatActivity.startActivityForResult(intent, NOVO);
    }

    public static void editarProcesso(AppCompatActivity appCompatActivity, Processo processo) {
        Intent intent = new Intent(appCompatActivity, CadastroActivity.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra("ID", processo.getId());
        appCompatActivity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        inputNomeMaterial = findViewById(R.id.inputNomeMaterial);
        inputGramaturaMaterial = findViewById(R.id.inputGramatura);
        inputMarcaMaterial = findViewById(R.id.inputMarca);

        radioGroupOperacao = findViewById(R.id.radioGroup);
        radioGroupOperacao.setOnCheckedChangeListener(getOnCheckedChangeListener());

        carregarTipoTapetes();

        inputPressao = findViewById(R.id.inputPressao);

        inputEspessuraCaneta = findViewById(R.id.inputEspessuraCaneta);

        inputProfundidadeLamina = findViewById(R.id.inputProfundidadeLamina);

        carregarLaminas();

        checkBoxIsTecido = findViewById(R.id.checkBoxMaterialBase);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.novo_processo));
                processo = new Processo();
            } else {
                setTitle(getString(R.string.alterar_processo));

                AsyncTask.execute(() -> {
                    int id = bundle.getInt("ID");

                    PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);

                    processo = database.processoDao().findById(id);
                    Material material = database.materialDao().findById(processo.getMaterial());

                    Lamina lamina = null;
                    if (processo.getLamina() != null) {
                        lamina = database.laminaDao().findById(processo.getLamina());
                    }

                    Caneta caneta = null;
                    if (processo.getCaneta() != null) {
                        caneta = database.canetaDao().findById(processo.getCaneta());
                    }

                    popularView(processo, material, lamina, caneta);
                });
            }
        }

        MaterialToolbar materialToolbar = findViewById(R.id.topAppBar);
        materialToolbar.setNavigationOnClickListener(view -> cancelar());
    }

    private void popularView(Processo processo, Material material, final Lamina lamina, final Caneta caneta) {
        CadastroActivity.this.runOnUiThread(() -> {
            String nomeMaterial = material.getNome();
            this.inputNomeMaterial.setText(nomeMaterial);

            int gramaturaMaterial = material.getGramatura();
            this.inputGramaturaMaterial.setText(String.valueOf(gramaturaMaterial));

            String marcaMaterial = material.getMarca();
            this.inputMarcaMaterial.setText(marcaMaterial);

            int pressaoFerramenta = processo.getPressao();
            this.inputPressao.setText(String.valueOf(pressaoFerramenta));

            String tipoProcesso = processo.getTipo();
            int tipoTapete = processo.getTapete();

            String espessuraCaneta = "";
            if (processo.getCaneta() != null) {
                espessuraCaneta = caneta.getEspessura();
            }
            this.inputEspessuraCaneta.setText(espessuraCaneta);

            int profundidadeLamina = processo.getProfundidadeLamina();

            for (int i = 0; i < listaTapetes.size(); i++) {
                if (listaTapetes.get(i).getId() == (tipoTapete)) {
                    this.tipoTapete.setSelection(i);
                    break;
                }
            }

            if(tipoProcesso.equals(CORTE)) {
                this.radioGroupOperacao.check(R.id.radioButtonCorte);
            } else {
                this.radioGroupOperacao.check(R.id.radioButtonDesenho);
            }

            this.inputProfundidadeLamina.setText(String.valueOf(profundidadeLamina));

            for (int i = 0; i < listaLaminas.size(); i++) {
                if (listaLaminas.get(i).getId() == lamina.getId()) {
                    this.corLamina.setSelection(i);
                    break;
                }
            }
            boolean isTecido = processo.getTecido().equals(SIM);
            this.checkBoxIsTecido.setChecked(isTecido);
        });
    }

    private void carregarLaminas() {
        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);
            listaLaminas = database.laminaDao().findAll();
            CadastroActivity.this.runOnUiThread(() -> {
                ArrayAdapter<Lamina> spinnerAdapter = new ArrayAdapter<>(CadastroActivity.this, android.R.layout.simple_spinner_item, listaLaminas);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                corLamina = findViewById(R.id.inputCorLamina);
                corLamina.setAdapter(spinnerAdapter);
            });
        });
    }

    private void carregarTipoTapetes() {
        AsyncTask.execute(() -> {
            PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);
            listaTapetes = database.tapeteDao().findAll();
            CadastroActivity.this.runOnUiThread(() -> {
                ArrayAdapter<Tapete> spinnerAdapter = new ArrayAdapter<>(CadastroActivity.this, android.R.layout.simple_spinner_item, listaTapetes);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tipoTapete = findViewById(R.id.tipoTapete);
                tipoTapete.setAdapter(spinnerAdapter);
            });
        });
    }

    private void limpar() {
        tipoTapete.setSelection(0);
        inputNomeMaterial.setText("");
        inputPressao.setText("");
        radioGroupOperacao.clearCheck();

        limparCamposDesenho();

        limparCamposCorte();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancelar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvar(MenuItem item) {
        salvar();
    }

    public void limpar(MenuItem item) {
        limpar();
        String text = "Campos Limpos";
        Toast.makeText(getApplicationContext(), text,
              Toast.LENGTH_SHORT).show();
    }

    private void salvar() {
        boolean isCamposValidos = validar();
        if(isCamposValidos) {

            String pressao = inputPressao.getText().toString();
            processo.setPressao(Integer.valueOf(pressao));

            Tapete tapete = (Tapete) tipoTapete.getSelectedItem();
            processo.setTapete(tapete.getId());

            int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();

            if (checkedRadioButtonId == R.id.radioButtonCorte) {
                processo.setTipo(CORTE);

                String profundidadeLamina = inputProfundidadeLamina.getText().toString();
                processo.setProfundidadeLamina(Integer.valueOf(profundidadeLamina));

                Lamina lamina = (Lamina) corLamina.getSelectedItem();
                processo.setLamina(lamina.getId());

                if (checkBoxIsTecido.isChecked()) {
                    processo.setTecido(SIM);
                } else {
                    processo.setTecido(NAO);
                }
            }

            AsyncTask.execute(() -> {
                int idMaterial = salvarMaterial();
                processo.setMaterial(idMaterial);

                if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                    processo.setTipo(DESENHO);

                    int idCaneta = salvarCaneta();
                    processo.setCaneta(idCaneta);
                }

                PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);
                if (modo == NOVO) {
                    database.processoDao().insert(processo);
                } else {
                    database.processoDao().update(processo);
                }

            });

            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @NonNull
    private int salvarCaneta( ) {
        String espessuraCaneta = inputEspessuraCaneta.getText().toString();
        Caneta caneta = new Caneta(espessuraCaneta);

        PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);
        int idCaneta = (int) database.canetaDao().insert(caneta);
        caneta.setId(idCaneta);

        return caneta.getId();
    }

    private int salvarMaterial() {
        String nomeMaterial = inputNomeMaterial.getText().toString();

        String gramaturaMaterial = inputGramaturaMaterial.getText().toString();

        String marcaMaterial = inputMarcaMaterial.getText().toString();
        Material material = new Material(nomeMaterial, Integer.valueOf(gramaturaMaterial), marcaMaterial);

        PlotterDatabase database = PlotterDatabase.getDatabase(CadastroActivity.this);
        return (int) database.materialDao().insert(material);
    }

    private boolean validar() {
        List<Integer> listaIdCampoErro = new ArrayList<>();

        if (inputNomeMaterial.getText().toString().isEmpty()) {
            listaIdCampoErro.add(inputNomeMaterial.getId());
        }

        /*
        if (tipoTapete.getSelectedItemId() == 0) {
            listaIdCampoErro.add(tipoTapete.getId());
        }
         */

        if (inputPressao.getText().toString().isEmpty()) {
            listaIdCampoErro.add(inputPressao.getId());
        }

        if (radioGroupOperacao.getCheckedRadioButtonId() == 0) {
            listaIdCampoErro.add(radioGroupOperacao.getId());
        }

        int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.radioButtonCorte) {
            if (inputProfundidadeLamina.getText().toString().isEmpty()) {
                listaIdCampoErro.add(inputProfundidadeLamina.getId());
            }

            /*
            if (corLamina.getSelectedItemId() == 0) {
                listaIdCampoErro.add(corLamina.getId());
            }

             */
        }

        if (checkedRadioButtonId == R.id.radioButtonDesenho) {
            if (inputEspessuraCaneta.getText().toString().isEmpty()) {
                listaIdCampoErro.add(inputEspessuraCaneta.getId());
            }
        }

        if (listaIdCampoErro.size() > 0) {

            String mensagemErro = "Por favor preencha todos os campos obrigatórios";

            Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT).show();

            if (listaIdCampoErro.size() == 1) {
                View campoErro = findViewById(listaIdCampoErro.get(0));
                campoErro.requestFocus();
            }
            return false;
        }
        return true;
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (radioGroup, i) -> {
            findViewById(R.id.layoutDesenho).setVisibility(View.INVISIBLE);
            findViewById(R.id.layoutCorte).setVisibility(View.INVISIBLE);

            int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.radioButtonCorte) {
                findViewById(R.id.layoutCorte).setVisibility(View.VISIBLE);
                limparCamposDesenho();
            }

            if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                findViewById(R.id.layoutDesenho).setVisibility(View.VISIBLE);
                limparCamposCorte();
            }
        };
    }

    private void limparCamposDesenho() {
        inputEspessuraCaneta.setText("");
    }

    private void limparCamposCorte() {
        inputProfundidadeLamina.setText("");
        corLamina.setSelection(0);
        checkBoxIsTecido.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelar();
    }

    private void cancelar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
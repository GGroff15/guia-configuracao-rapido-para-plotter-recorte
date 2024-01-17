package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia.PlotterDatabase;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.TapeteService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.menuprovider.ProcessCreationMenuProvider;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessCutListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ProcessDrawListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.ViewModelFactory;

public class ProcessCreationFragment extends Fragment {

    public enum ModeEnum {
        NEW(1), EDIT(2);

        private final int code;

        ModeEnum(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public static final String MODE = "MODO";
    public static final String SIM = "Sim";
    public static final String NAO = "Não";

    private int id;
    private int modo;
    private EditText inputNomeMaterial;
    private EditText inputGramaturaMaterial;
    private EditText inputPressao;
    private Spinner tipoTapete;
    private RadioGroup radioGroupOperacao;
    private EditText inputEspessuraCaneta;
    private EditText inputProfundidadeLamina;
    private Spinner corLamina;
    private CheckBox checkBoxIsTecido;
    private List<Mat> listaTapetes;
    private List<Blade> listaLaminas;
    private Processo processo;
    private LaminaService laminaService;
    private TapeteService tapeteService;
    private ProcessService processoService;
    private ProcessCreationFragmentHelper helper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        laminaService = new LaminaService(getContext());
        tapeteService = new TapeteService(getContext());
        processoService = new ProcessService(getContext());

        helper = new ProcessCreationFragmentHelper(this);

        modo = getArguments().getInt(MODE, ModeEnum.NEW.getCode());
        id = getArguments().getInt("id", 0);

        requireActivity().invalidateOptionsMenu();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_process_creation, container, false);
        helper.setupActionBar();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new ProcessCreationMenuProvider(this), getViewLifecycleOwner());

        inputNomeMaterial = view.findViewById(R.id.inputNomeMaterial);
        inputGramaturaMaterial = view.findViewById(R.id.inputGramatura);
        radioGroupOperacao = view.findViewById(R.id.radioGroup);
        radioGroupOperacao.setOnCheckedChangeListener(getOnCheckedChangeListener());
        inputPressao = view.findViewById(R.id.inputPressao);
        inputEspessuraCaneta = view.findViewById(R.id.inputEspessuraCaneta);
        inputProfundidadeLamina = view.findViewById(R.id.inputProfundidadeLamina);
        checkBoxIsTecido = view.findViewById(R.id.checkBoxMaterialBase);

        carregarTapetes();
        carregarLaminas();

        if (modo == ModeEnum.NEW.getCode()) {
            processo = new Processo();
        } else {
            AsyncTask.execute(() -> {

                PlotterDatabase database = PlotterDatabase.getDatabase(getContext());

                processo = database.processoDao().findById(id);

                Blade lamina = null;
                if (processo.getLamina() != null) {
                    lamina = database.laminaDao().findById(processo.getLamina());
                }

                popularView(processo, lamina);
            });
        }
    }

    private void popularView(Processo processo, final Blade lamina) {
        getActivity().runOnUiThread(() -> {
            String nomeMaterial = processo.getMaterial();
            this.inputNomeMaterial.setText(nomeMaterial);

            int gramaturaMaterial = processo.getGramatura();
            this.inputGramaturaMaterial.setText(String.valueOf(gramaturaMaterial));

            int pressaoFerramenta = processo.getPressao();
            this.inputPressao.setText(String.valueOf(pressaoFerramenta));

            String tipoProcesso = processo.getTipo();
            int tipoTapete = processo.getTapete();

            String espessuraCaneta = "";
            this.inputEspessuraCaneta.setText(espessuraCaneta);

            int profundidadeLamina = processo.getProfundidadeLamina();

            for (int i = 0; i < listaTapetes.size(); i++) {
                if (listaTapetes.get(i).getId() == (tipoTapete)) {
                    this.tipoTapete.setSelection(i);
                    break;
                }
            }

            if(tipoProcesso.equals(ProcessoEnum.CORTE.getTipo())) {
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
            listaLaminas = this.laminaService.listar();
            getActivity().runOnUiThread(() -> {
                ArrayAdapter<Blade> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaLaminas);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                corLamina = getView().findViewById(R.id.inputCorLamina);
                corLamina.setAdapter(spinnerAdapter);
            });
        });
    }

    private void carregarTapetes() {
        AsyncTask.execute(() -> {
            listaTapetes = tapeteService.listar();
            getActivity().runOnUiThread(() -> {
                ArrayAdapter<Mat> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaTapetes);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tipoTapete = getView().findViewById(R.id.tipoTapete);
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

    public void salvar(MenuItem item) {
        salvar();
    }

    public void limpar(MenuItem item) {
        limpar();
        String text = "Campos Limpos";
        Toast.makeText(getContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    private void salvar() {
        boolean isCamposValidos = validar();
        if(isCamposValidos) {

            String material = inputNomeMaterial.getText().toString();
            processo.setMaterial(material);

            Integer weight = Integer.valueOf(inputGramaturaMaterial.getText().toString());
            processo.setGramatura(weight);

            String pressao = inputPressao.getText().toString();
            processo.setPressao(Integer.valueOf(pressao));

            Mat tapete = (Mat) tipoTapete.getSelectedItem();
            processo.setTapete(tapete.getId());

            int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();

            if (checkedRadioButtonId == R.id.radioButtonCorte) {
                processo.setTipo(ProcessoEnum.CORTE.getTipo());

                String profundidadeLamina = inputProfundidadeLamina.getText().toString();
                processo.setProfundidadeLamina(Integer.valueOf(profundidadeLamina));

                Blade lamina = (Blade) corLamina.getSelectedItem();
                processo.setLamina(lamina.getId());

                if (checkBoxIsTecido.isChecked()) {
                    processo.setTecido(SIM);
                } else {
                    processo.setTecido(NAO);
                }
            }

            AsyncTask.execute(() -> {
                if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                    processo.setTipo(ProcessoEnum.DESENHO.getTipo());
                }

                if (modo == ModeEnum.NEW.getCode()) {
                    processoService.save(processo);
                } else {
                    processoService.update(processo);
                }

            });

            Navigation.findNavController(getView()).navigateUp();
        }
    }

    private boolean validar() {
        List<Integer> listaIdCampoErro = new ArrayList<>();

        if (inputNomeMaterial.getText().toString().isEmpty()) {
            listaIdCampoErro.add(inputNomeMaterial.getId());
        }

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
        }

        if (checkedRadioButtonId == R.id.radioButtonDesenho) {
            if (inputEspessuraCaneta.getText().toString().isEmpty()) {
                listaIdCampoErro.add(inputEspessuraCaneta.getId());
            }
        }

        if (listaIdCampoErro.size() > 0) {

            String mensagemErro = "Por favor preencha todos os campos obrigatórios";

            Toast.makeText(getContext(), mensagemErro, Toast.LENGTH_SHORT).show();

            if (listaIdCampoErro.size() == 1) {
                View campoErro = getView().findViewById(listaIdCampoErro.get(0));
                campoErro.requestFocus();
            }
            return false;
        }
        return true;
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (radioGroup, i) -> {
            getView().findViewById(R.id.layoutDesenho).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.layoutCorte).setVisibility(View.INVISIBLE);

            int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.radioButtonCorte) {
                getView().findViewById(R.id.layoutCorte).setVisibility(View.VISIBLE);
                limparCamposDesenho();
            }

            if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                getView().findViewById(R.id.layoutDesenho).setVisibility(View.VISIBLE);
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

    public int getModo() {
        return modo;
    }
}
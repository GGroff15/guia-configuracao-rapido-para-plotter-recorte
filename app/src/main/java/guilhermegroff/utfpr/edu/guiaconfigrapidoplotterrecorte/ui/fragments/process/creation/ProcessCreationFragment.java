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
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.BladeListViewModel;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel.MatListViewModel;
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
    private Spinner matSpinner;
    private RadioGroup radioGroupOperacao;
    private RadioGroup radioPenType;
    private EditText inputProfundidadeLamina;
    private Spinner bladeSpinner;
    private CheckBox checkBoxIsTecido;
    private List<Mat> listaTapetes;
    private List<Blade> listaLaminas;
    private Processo processo;
    private LaminaService laminaService;
    private TapeteService tapeteService;
    private ProcessService processoService;
    private ProcessCreationFragmentHelper helper;

    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        laminaService = new LaminaService(getContext());
        tapeteService = new TapeteService(getContext());
        processoService = new ProcessService(getContext());
        viewModelFactory = new ViewModelFactory(this);

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
        inputProfundidadeLamina = view.findViewById(R.id.inputProfundidadeLamina);
        checkBoxIsTecido = view.findViewById(R.id.checkBoxMaterialBase);
        radioPenType = view.findViewById(R.id.pen_type);

        loadMats();
        loadBlades();

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

            int profundidadeLamina = processo.getProfundidadeLamina();

            String pen = processo.getPen();

            for (int i = 0; i < listaTapetes.size(); i++) {
                if (listaTapetes.get(i).getId() == (tipoTapete)) {
                    this.matSpinner.setSelection(i);
                    break;
                }
            }

            if(tipoProcesso.equals(ProcessoEnum.CORTE.getTipo())) {
                this.radioGroupOperacao.check(R.id.radioButtonCorte);
            } else {
                this.radioGroupOperacao.check(R.id.radioButtonDesenho);
            }

            if (pen.equals(PenEnum.SCAN_N_CUT.getType())) {
                this.radioPenType.check(R.id.radioButtonScanNCutPen);
            } else {
                this.radioPenType.check(R.id.radioButtonUniversalAdapter);
            }

            this.inputProfundidadeLamina.setText(String.valueOf(profundidadeLamina));

            for (int i = 0; i < listaLaminas.size(); i++) {
                if (listaLaminas.get(i).getId() == lamina.getId()) {
                    this.bladeSpinner.setSelection(i);
                    break;
                }
            }
            boolean isTecido = processo.getTecido().equals(SIM);
            this.checkBoxIsTecido.setChecked(isTecido);
        });
    }

    private void loadBlades() {
        BladeListViewModel viewModel = viewModelFactory.createBladeListViewModel(laminaService);
        viewModel.getBladeList().observe(getViewLifecycleOwner(), blades -> {
            listaLaminas = blades;
            ArrayAdapter<Blade> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaLaminas);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bladeSpinner = getView().findViewById(R.id.spinnerBlade);
            bladeSpinner.setAdapter(spinnerAdapter);
        });
    }

    private void loadMats() {
        MatListViewModel viewModel = viewModelFactory.createMatListViewModel(tapeteService);
        viewModel.getListMat().observe(getViewLifecycleOwner(), mats -> {
            listaTapetes = mats;
            ArrayAdapter<Mat> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaTapetes);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            matSpinner = getView().findViewById(R.id.spinnerMat);
            matSpinner.setAdapter(spinnerAdapter);
        });
    }

    private void clear() {
        matSpinner.setSelection(0);
        inputNomeMaterial.setText("");
        inputPressao.setText("");
        radioGroupOperacao.clearCheck();
        limparCamposCorte();
    }

    public void save(MenuItem item) {
        boolean isCamposValidos = validar();
        if(isCamposValidos) {

            String material = inputNomeMaterial.getText().toString();
            processo.setMaterial(material);

            Integer weight = Integer.valueOf(inputGramaturaMaterial.getText().toString());
            processo.setGramatura(weight);

            String pressure = inputPressao.getText().toString();
            processo.setPressao(Integer.valueOf(pressure));

            Mat mat = (Mat) matSpinner.getSelectedItem();
            processo.setTapete(mat.getId());

            int checkedRadioButtonId = radioGroupOperacao.getCheckedRadioButtonId();

            if (checkedRadioButtonId == R.id.radioButtonCorte) {
                processo.setTipo(ProcessoEnum.CORTE.getTipo());

                String profundidadeLamina = inputProfundidadeLamina.getText().toString();
                processo.setProfundidadeLamina(Integer.valueOf(profundidadeLamina));

                Blade lamina = (Blade) bladeSpinner.getSelectedItem();
                processo.setLamina(lamina.getId());

                if (checkBoxIsTecido.isChecked()) {
                    processo.setTecido(SIM);
                } else {
                    processo.setTecido(NAO);
                }
            }

            if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                processo.setTipo(ProcessoEnum.DESENHO.getTipo());
                int checkedRadioButtonPen = radioPenType.getCheckedRadioButtonId();
                if (checkedRadioButtonPen == R.id.radioButtonScanNCutPen) {
                    processo.setPen(PenEnum.SCAN_N_CUT.getType());
                } else {
                    processo.setPen(PenEnum.UNIVERSAL_ADAPTER.getType());
                }
            }

            AsyncTask.execute(() -> {
                if (modo == ModeEnum.NEW.getCode()) {
                    processoService.save(processo);
                } else {
                    processoService.update(processo);
                }
            });

            Navigation.findNavController(getView()).navigateUp();
        }
    }

    public void clear(MenuItem item) {
        clear();
        String text = getString(R.string.cleared_field);
        Toast.makeText(getContext(), text,
                Toast.LENGTH_SHORT).show();
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

        if (listaIdCampoErro.size() > 0) {

            String mensagemErro = getString(R.string.required_field_error);

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
            }

            if (checkedRadioButtonId == R.id.radioButtonDesenho) {
                getView().findViewById(R.id.layoutDesenho).setVisibility(View.VISIBLE);
                limparCamposCorte();
            }
        };
    }

    private void limparCamposCorte() {
        inputProfundidadeLamina.setText("");
        bladeSpinner.setSelection(0);
        checkBoxIsTecido.setChecked(false);
    }

    public int getModo() {
        return modo;
    }
}
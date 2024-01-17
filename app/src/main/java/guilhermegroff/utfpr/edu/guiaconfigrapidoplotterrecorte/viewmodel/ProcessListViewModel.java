package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;

public abstract class ProcessListViewModel extends ViewModel {

    ProcessoEnum processoEnum;
    final ProcessService service;
    private final MutableLiveData<String> filtroLiveData = new MutableLiveData<>("");

    public ProcessListViewModel(ProcessService service, ProcessoEnum processoEnum) {
        this.processoEnum = processoEnum;
        this.service = service;
    }

    public LiveData<List<Processo>> getProcessListLiveData() {
        return Transformations.switchMap(filtroLiveData, filtro -> {
            if (TextUtils.isEmpty(filtro)) {
                return service.getItens(processoEnum.getTipo());
            } else {
                return service.getItens(processoEnum.getTipo(), filtro);
            }
        });
    }

    public void update(String brand) {
        filtroLiveData.setValue(brand);
    }

}

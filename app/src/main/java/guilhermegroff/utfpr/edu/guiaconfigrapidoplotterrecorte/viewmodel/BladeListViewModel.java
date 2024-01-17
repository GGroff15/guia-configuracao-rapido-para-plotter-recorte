package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;

public class BladeListViewModel extends ViewModel {

    private LaminaService service;
    private LiveData<List<Blade>> bladeList;

    public BladeListViewModel(LaminaService service) {
        this.service = service;
        bladeList = service.getItens();
    }

    public LiveData<List<Blade>> getBladeList() {
        return bladeList;
    }

    public void update() {
        bladeList = service.getItens();
    }
}
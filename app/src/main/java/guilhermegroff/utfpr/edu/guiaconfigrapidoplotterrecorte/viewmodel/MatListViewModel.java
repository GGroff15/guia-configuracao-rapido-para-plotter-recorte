package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.TapeteService;

public class MatListViewModel extends ViewModel {

    private TapeteService service;
    private LiveData<List<Mat>> listMat;

    public MatListViewModel(TapeteService service) {
        this.service = service;
        this.listMat = service.getItens();
    }

    public LiveData<List<Mat>> getListMat() {
        return listMat;
    }

    public void update() {
        this.listMat = service.getItens();
    }
}
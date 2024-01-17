package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;

public class ProcessCutListViewModel extends ProcessListViewModel {

    private static ProcessCutListViewModel instance;

    public ProcessCutListViewModel(ProcessService service) {
        super(service, ProcessoEnum.CORTE);
    }

    public static ProcessCutListViewModel getInstance(ProcessService service) {
        if (instance == null) {
            instance = new ProcessCutListViewModel(service);
        }
        return instance;
    }
}

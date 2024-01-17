package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.ProcessoEnum;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;

public class ProcessDrawListViewModel extends ProcessListViewModel {
    public ProcessDrawListViewModel(ProcessService service) {
        super(service, ProcessoEnum.DESENHO);
    }

}

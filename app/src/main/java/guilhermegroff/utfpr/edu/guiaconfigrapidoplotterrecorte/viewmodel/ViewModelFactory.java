package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.LaminaService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.ProcessService;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service.TapeteService;

public class ViewModelFactory {
    private ViewModelStoreOwner owner;

    public ViewModelFactory(ViewModelStoreOwner owner) {
        this.owner = owner;
    }

    public ProcessCutListViewModel createProcessCutListViewModel(ProcessService service) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) ProcessCutListViewModel.getInstance(service);
            }
        }).get(ProcessCutListViewModel.class);
    }

    public ProcessDrawListViewModel createProcessDrawListViewModel(ProcessService service) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProcessDrawListViewModel(service);
            }
        }).get(ProcessDrawListViewModel.class);
    }

    public BladeListViewModel createBladeListViewModel(LaminaService service) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new BladeListViewModel(service);
            }
        }).get(BladeListViewModel.class);
    }

    public MatListViewModel createMatListViewModel(TapeteService service) {
        return new ViewModelProvider(owner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MatListViewModel(service);
            }
        }).get(MatListViewModel.class);
    }

}

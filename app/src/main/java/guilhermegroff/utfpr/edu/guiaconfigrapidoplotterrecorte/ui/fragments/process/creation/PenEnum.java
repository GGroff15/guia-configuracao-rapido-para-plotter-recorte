package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui.fragments.process.creation;

import androidx.constraintlayout.widget.Barrier;

public enum PenEnum {

    SCAN_N_CUT("Scan n' Cut"), UNIVERSAL_ADAPTER("Adaptador Universal");

    private String type;

    private PenEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

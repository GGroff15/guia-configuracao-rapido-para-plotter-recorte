package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades;

public enum ProcessoEnum {

    CORTE("Corte"), DESENHO("Desenho");

    private String tipoOperacao;

    ProcessoEnum(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getTipo() {
        return tipoOperacao;
    }
}

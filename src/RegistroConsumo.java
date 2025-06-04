public class RegistroConsumo {
    private String acao;
    private double duracao;
    private double litros;

    public RegistroConsumo(String acao, double duracao, double litros) {
        this.acao = acao;
        this.duracao = duracao;
        this.litros = litros;
    }

    public String getAcao() { return acao; }
    public double getDuracao() { return duracao; }
    public double getLitros() { return litros; }
}

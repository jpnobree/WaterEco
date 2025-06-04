import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class WaterEcoApp extends JFrame {
    private JComboBox<String> comboAcao;
    private JTextField campoDuracao;
    private JLabel labelResultado;
    private DefaultTableModel tabelaModelo;
    private JTable tabela;
    private ArrayList<RegistroConsumo> registros = new ArrayList<>();
    private double totalConsumo = 0;
    private JLabel labelTotal;
    
    private JTextField campoMensagem;
    private Timer timerMensagem;
    private int indiceMensagem = 0;
    private final String[] mensagens = {
        " Estima-se que uma pessoa gasta mais de 100L de água por dia.",
        " 1 em cada 3 pessoas no mundo não tem acesso à água potável.",
        " Um banho de 10 minutos pode consumir até 150L de água.",
        " Reduzir o tempo no banho é uma forma simples de economizar água.",
        " Escovar os dentes com a torneira aberta gasta até 5L em 1 minuto.",
        " A ONU alerta: precisamos mudar já nosso consumo de água.",
        " A ONU estima que, em 2025, o consumo de água será próximo de 593 bilhões de litros por hora.",
        " Apenas 2,5% da água do mundo é doce e potável.",
        " Consumo consciente é responsabilidade de todos."
    };
    
    private void atualizarMensagem() {
        indiceMensagem = (indiceMensagem + 1) % mensagens.length;
        campoMensagem.setText(mensagens[indiceMensagem]);
    };

    public WaterEcoApp() {
        setTitle("WaterEco - Controle de Consumo de Água");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] acoes = {
            "Banho (chuveiro)", 
            "Escovar os dentes (com a torneira aberta)",
            "Escovar os dentes (torneira fechada)",
            "Lavar roupa (máquina)", 
            "Lavar louça"
            };

        JPanel painelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Registrar Consumo"));

        comboAcao = new JComboBox<>(acoes);
        campoDuracao = new JTextField();
        labelResultado = new JLabel("Consumo: 0 litros");
        labelTotal = new JLabel("Consumo total: 0 Litros");

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnLimpar = new JButton("Limpar Tudo");

        painelEntrada.add(new JLabel("Ação:"));
        painelEntrada.add(comboAcao);
        painelEntrada.add(new JLabel("Duração (min / ciclos):"));
        painelEntrada.add(campoDuracao);
        painelEntrada.add(labelResultado);
        painelEntrada.add(labelTotal);
        painelEntrada.add(btnRegistrar);
        painelEntrada.add(btnLimpar);

        tabelaModelo = new DefaultTableModel(new Object[]{"Ação", "Duração", "Consumo (litros)"}, 0);
        tabela = new JTable(tabelaModelo);
        JScrollPane scrollTabela = new JScrollPane(tabela);

        add(painelEntrada, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrar());
        btnLimpar.addActionListener(e -> limparTudo());

        setVisible(true);
        
        campoMensagem = new JTextField();
        campoMensagem.setEditable(false);
        campoMensagem.setHorizontalAlignment(JTextField.CENTER);
        campoMensagem.setFont(new Font("Arial", Font.BOLD, 13));
        campoMensagem.setBackground(new Color(230, 240, 255));
        campoMensagem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        campoMensagem.setText(mensagens[0]);
        add(campoMensagem, BorderLayout.SOUTH);

        timerMensagem = new Timer(7500, e -> atualizarMensagem());
        timerMensagem.start();

    }

    private void registrar() {
        String acao = (String) comboAcao.getSelectedItem();
        String duracaoStr = campoDuracao.getText();

        if (duracaoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a duração ou quantidade.");
            return;
        }

        try {
            double duracao = Double.parseDouble(duracaoStr);
            double litros = calcularConsumo(acao, duracao);

            RegistroConsumo r = new RegistroConsumo(acao, duracao, litros);
            registros.add(r);

            tabelaModelo.addRow(new Object[]{acao, duracao, litros});
            labelResultado.setText("Consumo: " + litros + " litros");
            totalConsumo += litros;
            labelTotal.setText("Consumo total: " + totalConsumo + " litros");

            campoDuracao.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Insira um valor numérico válido.");
        }
    }

    private double calcularConsumo(String acao, double duracao) {
        return switch (acao) {
            case "Banho (chuveiro)" -> duracao * 9;
            case "Escovar os dentes (com a torneira aberta)" -> duracao * 5;
            case "Escovar os dentes (torneira fechada)" -> duracao * 0.2;
            case "Lavar roupa (máquina)" -> duracao * 45;
            case "Lavar louça" -> duracao * 8;
            default -> 0;
        };
    }

    
    private void limparTudo() {
        registros.clear();
        tabelaModelo.setRowCount(0);
        labelResultado.setText("Consumo: 0 litros");
        totalConsumo = 0;
        labelTotal.setText("Consumo total: 0 litros");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WaterEcoApp::new);
    }
}

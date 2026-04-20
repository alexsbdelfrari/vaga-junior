import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static ArrayList<Combustivel> lista = new ArrayList<>();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Cadastro de Combustível");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();

        JLabel precoLabel = new JLabel("Preco (R$):");
        JTextField precoField = new JTextField();

        JButton salvarBtn = new JButton("Salvar");
        JButton removerBtn = new JButton("Remover");

        // Tabela
        String[] colunas = {"Nome", "Preco"};
        javax.swing.table.DefaultTableModel modelo =
                new javax.swing.table.DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        // 🔥 Clique na tabela (preenche campos)
        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha >= 0) {
                nomeField.setText(modelo.getValueAt(linha, 0).toString());
                precoField.setText(modelo.getValueAt(linha, 1).toString());
            }
        });

        // 🔥 SALVAR (CREATE + UPDATE)
        salvarBtn.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                double preco = Double.parseDouble(precoField.getText());

                int linhaSelecionada = tabela.getSelectedRow();

                if (linhaSelecionada >= 0) {
                    // UPDATE
                    lista.set(linhaSelecionada, new Combustivel(nome, preco));
                    modelo.setValueAt(nome, linhaSelecionada, 0);
                    modelo.setValueAt(preco, linhaSelecionada, 1);
                } else {
                    // CREATE
                    Combustivel c = new Combustivel(nome, preco);
                    lista.add(c);
                    modelo.addRow(new Object[]{nome, preco});
                }

                nomeField.setText("");
                precoField.setText("");
                tabela.clearSelection();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
            }
        });

        // 🔥 DELETE
        removerBtn.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha >= 0) {
                lista.remove(linha);
                modelo.removeRow(linha);

                nomeField.setText("");
                precoField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um item para remover");
            }
        });

        // Layout
        panel.add(nomeLabel);
        panel.add(nomeField);

        panel.add(precoLabel);
        panel.add(precoField);

        panel.add(salvarBtn);
        panel.add(removerBtn);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
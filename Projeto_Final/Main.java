import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class Main extends JFrame {
    private JTextArea textArea;
    private Connection connection;

    public Main() {
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton saveAndRetrieveButton = new JButton("Salvar");
        saveAndRetrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndRetrieveDrafts();
            }
        });

        JButton viewDraftsButton = new JButton("Visualizar Rascunhos");
        viewDraftsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveDrafts();
            }
        });

        JButton editDraftButton = new JButton("Editar Rascunho Selecionado");
        editDraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedDraft();
            }
        });

        JButton createPDFButton = new JButton("Criar PDF do Rascunho");
        createPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPDF();
            }
        });

        JButton newDraftButton = new JButton("Novo Rascunho");
        newDraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewDraft();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveAndRetrieveButton);
        buttonPanel.add(viewDraftsButton);
        buttonPanel.add(newDraftButton);
        buttonPanel.add(editDraftButton);
        buttonPanel.add(createPDFButton);


        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(scrollPane);
        add(buttonPanel);

        setTitle("Editor de Documentos com H2 JDBC");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
            String createTableQuery = "CREATE TABLE IF NOT EXISTS drafts (id INTEGER AUTO_INCREMENT PRIMARY KEY, content TEXT)";
            connection.createStatement().executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao inicializar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAndRetrieveDrafts() {
        String draftContent = textArea.getText();

        if (draftContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O rascunho está vazio. Nada para salvar.");
            return;
        }

        try {
            // Salvar rascunho no banco de dados
            String insertDraftQuery = "INSERT INTO drafts (content) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertDraftQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, draftContent);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                JOptionPane.showMessageDialog(this, "Rascunho salvo com ID: " + id);
            }

            preparedStatement.close();

            // Recuperar rascunhos do banco de dados
            retrieveDrafts();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar/recuperar rascunhos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retrieveDrafts() {
        try {
            String selectDraftsQuery = "SELECT * FROM drafts";
            PreparedStatement preparedStatement = connection.prepareStatement(selectDraftsQuery);
            var resultSet = preparedStatement.executeQuery();

            StringBuilder draftsText = new StringBuilder("Rascunhos:\n");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                draftsText.append("ID: ").append(id).append(", Conteúdo: ").append(content).append("\n");
            }

            textArea.setText(draftsText.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao recuperar rascunhos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSelectedDraft() {
        String idString = JOptionPane.showInputDialog(this, "Digite o ID do rascunho que deseja editar:");

        if (idString != null && !idString.isEmpty()) {
            try {
                int draftId = Integer.parseInt(idString);

                // Verifica se o rascunho com o ID fornecido existe
                if (draftExists(draftId)) {
                    String selectDraftQuery = "SELECT content FROM drafts WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(selectDraftQuery);
                    preparedStatement.setInt(1, draftId);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String content = resultSet.getString("content");
                        textArea.setText(content);  // Preenche a área de texto com o conteúdo do rascunho selecionado para edição
                    } else {
                        JOptionPane.showMessageDialog(this, "Rascunho não encontrado com o ID fornecido.");
                    }

                    preparedStatement.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Rascunho não encontrado com o ID fornecido.");
                }
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao recuperar rascunho para edição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Digite um ID válido.");
        }
    }

    private boolean draftExists(int draftId) throws SQLException {
        String checkDraftQuery = "SELECT 1 FROM drafts WHERE id = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkDraftQuery);
        checkStatement.setInt(1, draftId);
        ResultSet resultSet = checkStatement.executeQuery();
        boolean exists = resultSet.next();
        checkStatement.close();
        return exists;
    }

    private void createPDF() {
        String idString = JOptionPane.showInputDialog(this, "Digite o ID do rascunho que deseja transformar em PDF:");

        if (idString != null && !idString.isEmpty()) {
            try {
                int draftId = Integer.parseInt(idString);

                // Verifica se o rascunho com o ID fornecido existe
                if (draftExists(draftId)) {
                    String selectDraftQuery = "SELECT content FROM drafts WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(selectDraftQuery);
                    preparedStatement.setInt(1, draftId);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String content = resultSet.getString("content");
                        saveAsPDF(content);
                        JOptionPane.showMessageDialog(this, "PDF criado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Rascunho não encontrado com o ID fornecido.");
                    }

                    preparedStatement.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Rascunho não encontrado com o ID fornecido.");
                }
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao criar PDF: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Digite um ID válido.");
        }
    }

    private void saveAsPDF(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como PDF");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Garanta que o nome do arquivo tenha a extensão .pdf
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".pdf");
            }

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(20, 700);
                    contentStream.showText(content);
                    contentStream.endText();
                }

                document.save(fileToSave);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createNewDraft() {
        int response = JOptionPane.showConfirmDialog(this, "Deseja criar um novo rascunho?", "Novo Rascunho", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            textArea.setText("");  // Limpa a área de texto para criar um novo rascunho
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}

package ru.saiev.technesistask.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.saiev.technesistask.TechnesisApp;
import ru.saiev.technesistask.config.DataStore;
import ru.saiev.technesistask.service.NoteService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML private Button addButton;

    @FXML private Button updateButton;

    @FXML private Button deleteButton;

    @FXML private Label noteCreationTime;

    @FXML private TextArea noteText;

    @FXML private VBox panelVBox;

    @FXML private Label windowHeader;

    @FXML private Label noteDelField;

    private int selectedNoteIndex = -1;


    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            try {
                showNewNoteWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        updateButton.setOnAction(event -> {
            if (selectedNoteIndex == -1) {
                NoteService.timedMessage(noteDelField,2, "Ничего не выбрано");
                return;
            }
            fillUpdateFields();
        });
        deleteButton.setOnAction(event -> {
            if (selectedNoteIndex == -1){
                NoteService.timedMessage(noteDelField,2, "Ничего не выбрано");
                return;
            }
            deleteNote(selectedNoteIndex);
        });

        DataStore.addSomeNotes();
        addNotesToVbox();
        System.out.println("selectedNoteIndex = " + selectedNoteIndex);

    }

    /**
     * Метод выводит на экран новое окно из заданого fxml файла.
     * В качетве параметров принимает имя FXML файла и его заголовок.
     *
     * @return экземпляр fxmlLoader
     */
    private FXMLLoader showNewNoteWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TechnesisApp.class.getResource("/fxml/" + "note_creation_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 320);
        Stage stage = new Stage();
        stage.setTitle("Technesis: \"Форма создания заявки.\"");
        stage.setScene(scene);
        stage.show();
        return fxmlLoader;
    }

    /**
     * Метод заполняет поля формы для редактирования заявки текстом выбранной заявки.
     */
    private void fillUpdateFields(){
        EditorController controller;
        try {
            controller = showNewNoteWindow().getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String headerText = DataStore.getNoteStore().get(selectedNoteIndex).getHeader();
        String noteText = DataStore.getNoteStore().get(selectedNoteIndex).getNoteText().toString();
        controller.setHeader(headerText);
        controller.setNoteText(noteText);

    }

    /**
     * Метод выводит заголовки заявкок из хранилища в область {@link ScrollPane}
     */
    public void addNotesToVbox() {
        panelVBox.getChildren().clear();
        int size = DataStore.getNoteStore().size();
        int counter = 0;

        while (counter < size) {
            Button button = new Button(getNoteHeader(counter));
            int finalCounter = counter;
            button.setOnAction(el -> {
                selectedNoteIndex = Math.toIntExact(DataStore.getNoteStore().get(finalCounter).getId());
                System.out.println("selectedNoteIndex = " + selectedNoteIndex);
                setWindowHeader(finalCounter);
                setNoteTime(finalCounter);
                setNoteText(finalCounter);
            });
            button.getStyleClass().add("noteButton");
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_CENTER);
            HBox.setHgrow(button, Priority.ALWAYS);
            hBox.getChildren().add(button);
            button.setMaxWidth(Double.MAX_VALUE);
            hBox.setAlignment(Pos.TOP_CENTER);
            panelVBox.getChildren().add(hBox);
            panelVBox.setSpacing(5);

            counter++;
        }
    }

    /**
     * Метод удаляет заметку из хранилища.
     * @param selectedNoteIndex индекс элемента.
     */
    private void deleteNote(int selectedNoteIndex){
        if (selectedNoteIndex != -1) {
            DataStore.getNoteStore().remove(selectedNoteIndex);
            NoteService.timedMessage(noteDelField,1000, "УДАЛЕНО");
            windowHeader.setText("");
            noteCreationTime.setText("");
            noteText.setText("");
            this.selectedNoteIndex = -1;
            addNotesToVbox();
            System.out.println(DataStore.getNoteStore());
        }

    }

    /**
     * Метод устанавливает заголовок заметки в соответствующее поле.
     *
     * @param index индекс заметки.
     */
    private void setWindowHeader(int index) {
        windowHeader.setText(getNoteHeader(index));
    }

    /**
     * Метод выводит текст выбранной завяки в соответсвтующее поле.
     * @param index индекс заявки в хранилище.
     */
    private void setNoteText(int index) {
        noteText.setText(getNoteText(index).toString());
    }

    /**
     * Метод устанавливает время создания заявки в соответствующее поле.
     *
     * @param index индекс заметки.
     */
    private void setNoteTime(int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime time = getNoteTime(index);
        String noteTime = time.format(formatter);
        noteCreationTime.setText(noteTime);
    }

    /**
     * Метод получает заголовок заявки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link String}
     */
    private String getNoteHeader(int index) {
        return DataStore.getNoteStore().get(index).getHeader();
    }

    /**
     * Метод получает текст заявки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link StringBuilder}
     */
    private StringBuilder getNoteText(int index) {
        return DataStore.getNoteStore().get(index).getNoteText();
    }

    /**
     * Метод получает время создания завки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link LocalDateTime}
     */
    private LocalDateTime getNoteTime(int index) {
        return DataStore.getNoteStore().get(index).getDateTime();
    }
}

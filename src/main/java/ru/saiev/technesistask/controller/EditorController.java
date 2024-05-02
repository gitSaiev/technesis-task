package ru.saiev.technesistask.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.saiev.technesistask.config.DataStore;
import ru.saiev.technesistask.model.Note;
import ru.saiev.technesistask.service.NoteService;

public class EditorController {

    @FXML private Button canselBtn;

    @FXML private Label dateTime;

    @FXML private Label warning;

    @FXML private TextField header;

    @FXML private TextArea noteText;

    @FXML private Button okBtn;

    @FXML void initialize() {
        extractCurrentTime();

        okBtn.setOnAction(event -> {
            newNoteCreate();
            System.out.println("Содержание хранилища:\n" + DataStore.getNoteStore());
        });

        canselBtn.setOnAction(actionEvent -> closeWindow(canselBtn));
    }

    /**
     * Метод создает новую заявку в хранилище.
     * <ul> В начале метод получает текст и время из полей:
     * <li>{@link EditorController#header}</li>
     * <li>{@link EditorController#dateTime}</li>
     * <li>{@link EditorController#noteText}</li>
     * </ul>
     * Далее, проводится проверка, не пусты ли эти поля, т.к. создавать пустую заявку нельзя.
     * В случае если и заголовок и текст заявки заполнены, то она добавится в хранилище.
     * Если нет, то пользователь увидит в тоже окне уведомление.
     * <br>В случае успешного создания завки окно создания будет закрыто.
     * Метод возвращает true если завяка создана, в противном случа - false</br>
     */
    private void newNoteCreate() {
        String header = this.header.getText();

        extractCurrentTime();

        LocalDateTime dateTime = LocalDateTime.now();

        StringBuilder noteText = new StringBuilder(this.noteText.getText());

        Note newNote;
        if (!header.isEmpty() &&
                !header.isBlank() &&
                !noteText.toString().isEmpty() &&
                header.length() >= 5 &&
                noteText.length() >= 5) {
            newNote = new Note(header, dateTime, noteText);
            closeWindow(okBtn);
            DataStore.getNoteStore().add(newNote);

        } else {
            NoteService.timedMessage(warning, 8, "ВНИМАНИЕ: Заявка не была создана!");
        }
    }

    /**
     * Метод закрывает окно по нажатию кнопки.
     *
     * @param button кнопка на которую назначается закрытие окна.
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * Метод добавляет текущее время и дату в поле {@link EditorController#dateTime},
     * которое отображается в форме создания заявки.
     */
    private void extractCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        dateTime.setText(currentTime);
    }

    public void setHeader(String text) {
        this.header.setText(text);
    }

    public void setNoteText(String noteText) {
        this.noteText.setText(noteText);
    }
}

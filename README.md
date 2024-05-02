<h1>Technesis - тестовое задание, приложение для создание заметок</h1>

<h3>Стек:</h3>

- Java 11
- JavaFX 17
- PostgreSQL config

Главное окно состоит из 3-х частей: 
1. Панель работы с заметками. Включающая в себя дейтсивя: создание, редактирование и удаление заметки.
2. Список созданных заметок
3. Окно просмотра выделенной в списке заметки.

---
- Каждая "заметка" состоит из Заголовка, Времени создания и Текста.
Заголовок и Текст могут быть отредактированы. Время создания - нет.
---

- При нажатии кнопки "+" (создание) открывается диалог создания новой заметки ("редактор заметок", параметр: создание).
- При нажатии кнопки "-" (удаление) открывается запрос на удаление заметки.
- При нажатии кнопки "..." (редактирование) открывается диалог редактирования (тот же что "редактор заметок", но обработан параметр "редактирование").
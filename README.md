# kotlinExam - консольное приложение для заметок
Позволяет создавать, редактировать, удалять, а также сортировать заметки в консольном приложении. Из бонусов, отмечу возможность экспорта заметок в файл
### Создание классов `Note` и `NoteBook` а также функции `createNote`
```kotlin
class Note(
    var title: String,
    var text: String,
    var date: LocalDateTime,
    var lastEdit: LocalDateTime? = null
)

class NoteBook(val notes: MutableList<Note>)

val noteBook = NoteBook(mutableListOf<Note>())
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

fun createNote(): Note {
    println("Введите заголовок:")
    val title = readLine()!!
    println("Введите текст:")
    val text = readLine()!!
    val date = LocalDateTime.now()
    return Note(title, text, date)
}
```
# Создание заметки
![one](https://github.com/gabdulkaliev/kotlinExam/blob/main/one.png)
___
### Часть основного кода, работающего церез `while` и `when`
```kotlin
while (userInput != 9) {
        try {
            userInput = readLine()?.toInt()
            if (userInput == null || userInput < 0 || userInput > 9) {
                println("Некорректный ввод. Введите целое число от 0 до 9.")
                continue
            }
            when (userInput) {
                1 -> {
                    val note = createNote()
                    noteBook.notes.add(note)
                    Greeting()
                }

                2 -> {
                    viewNotes(noteBook.notes)
                    Greeting()
                }

                3 -> {
                    println("Выберите заметку:")
                    val noteNum = readLine()!!.toInt()
                    selectedNote = noteBook.notes.getOrNull(noteNum - 1)
                    val selectedFromSearch = searchResults.getOrNull(noteNum - 1)
                    if (selectedFromSearch != null) {
                        selectedNote = selectedFromSearch
                    }
                    if (selectedNote != null) {
                        viewNote(selectedNote)
                    } else {
                        println("Неверный номер заметки")
                    }
                    Greeting()
                }
```
___
### Добавление второй заметки и общий вывод в консоль
![two](https://github.com/gabdulkaliev/kotlinExam/blob/main/two.png)

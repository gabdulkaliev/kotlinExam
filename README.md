﻿# kotlinExam - консольное приложение для заметок
## Позволяет создавать, редактировать, удалять, а также сортировать заметки в консольном приложении
## Из бонусов, возможность экспорта заметок в файл
# Создание классов `Note` и `NoteBook` а также функции `createNote`
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
![one]()

import java.time.LocalDateTime
import java.io.File
import java.io.PrintWriter
import java.time.format.DateTimeFormatter

//1. Классы:
//- Заметка (заголовок, текст, дата создания)
//- Блокнот (список заметок, методы для управления заметками)

//2. Функции:
//- Создание новой заметки
//- Редактирование существующей заметки
//- Удаление заметки
//- Просмотр списка всех заметок
//- Просмотр конкретной заметки

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

fun editNote(existingNote: Note, newTitle: String, newText: String): Note {
    existingNote.title = newTitle
    existingNote.text = newText
    existingNote.lastEdit = LocalDateTime.now()
    return existingNote
}

fun deleteNote(existingNote: Note): Boolean {
    return noteBook.notes.remove(existingNote)
}

fun viewNotes(notes: MutableList<Note>) {
    println("Все заметки:")
    for (note in notes) {
        println("Заголовок: ${note.title}, Текст: ${if (note.text.length > 10)
            note.text.substring(0..10) else note.text}, Дата создания: " +
                "${note.date.format(formatter)}, Последнее редактирование: " +
                "${note.lastEdit?.format(formatter) ?: "Нет"}")
    }
}

fun viewNote(selectedNote: Note) {
    println("Выбранная заметка - Заголовок: ${selectedNote.title}, Текст: " +
            "${selectedNote.text}, Дата создания: ${selectedNote.date.format(formatter)}, " +
            "Последнее редактирование: ${selectedNote.lastEdit?.format(formatter) ?: "Нет"}")
}

fun searchNotes(notes: MutableList<Note>, searchText: String): List<Note> {
    return notes.filter { it.title.contains(searchText, ignoreCase = true) ||
            it.text.contains(searchText, ignoreCase = true) }
}

fun sortNotesByDateCreatedOrLastEdit(notes: MutableList<Note>, sortByDateCreated: Boolean = true) {
    notes.sortWith(compareByDescending<Note> {
        if (sortByDateCreated) it.date else it.lastEdit ?: LocalDateTime.MIN
    })
}

fun saveNotesToFile(notes: MutableList<Note>, fileName: String) {
    try {
        PrintWriter(File(fileName)).use { writer ->
            notes.forEach { obj ->
                writer.println("${obj.title}, ${obj.text}, ${obj.date.format(formatter)}, " +
                        "${obj.lastEdit?.format(formatter) ?: ""}")
            }
            println("Данные успешно экспортированы в файл $fileName.")
        }
    } catch (e: Exception) {
        println("Произошла ошибка при экспорте данных в файл: ${e.message}")
    }
}

fun Greeting() {
    println("1 - Создание заметки")
    println("2 - Показать все заметки")
    println("3 - Выбрать заметку")
    println("4 - Редактирование заметки")
    println("5 - Удаление заметки")
    println("6 - Поиск заметки")
    println("7 - Сортировка заметок по дате создания")
    println("8 - Сортировка заметок по дате последнего редактирования")
    println("9 - Выход из программы")
    println("0 - Экспорт заметок в файл")
}

fun main() {
    var searchResults: List<Note> = emptyList()
    var selectedNote: Note? = null

    Greeting()
    var userInput: Int? = null

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

                4 -> {
                    if (selectedNote != null) {
                        println("Редактирование заметки:")
                        println("Измените заголовок:")
                        val newTitle = readLine()!!
                        println("Измените текст:")
                        val newText = readLine()!!
                        editNote(selectedNote, newTitle, newText)
                    } else {
                        println("Сначала выберите заметку для редактирования")
                    }
                    Greeting()
                }

                5 -> {
                    if (selectedNote != null) {
                        deleteNote(selectedNote)
                    } else {
                        println("Сначала выберите заметку для редактирования")
                    }
                    println("Заметка удалена")
                    Greeting()
                }

                6 -> {
                    println("Поиск заметок: введите текст")
                    val searchText = readLine()!!
                    searchResults = searchNotes(noteBook.notes, searchText)
                    println("Результаты поиска:")
                    searchResults.forEachIndexed { index, note ->
                        println("${index + 1}. Заголовок: ${note.title}, Текст: " +
                                "${if (note.text.length > 10) note.text.substring(0..10)
                                else note.text}, Дата создания: ${note.date.format(formatter)}," +
                                " Последнее редактирование: ${note.lastEdit?.format(formatter) ?: "Нет"}")
                    }
                    Greeting()
                }

                7 -> {
                    sortNotesByDateCreatedOrLastEdit(noteBook.notes, sortByDateCreated = true)
                    println("Заметки отсортированы по дате создания.")
                    viewNotes(noteBook.notes)
                    Greeting()
                }

                8 -> {
                    sortNotesByDateCreatedOrLastEdit(noteBook.notes, sortByDateCreated = false)
                    println("Заметки отсортированы по дате последнего редактирования.")
                    viewNotes(noteBook.notes)
                    Greeting()
                }

                0 -> {
                    saveNotesToFile(noteBook.notes, "notes.txt")
                    break
                    Greeting()
                }
            }
        } catch (e: NumberFormatException) {
            println("Некорректный ввод. Введите целое число.")
            readLine()
        } catch (e: Exception) {
            println("Произошла ошибка: ${e.message}")
        }
    }

    println("Программа завершена")
}

//3. Коллекции: Используйте подходящие коллекции для управления наборами заметок.
//
//4. Дата и Время: Отображение даты создания или последнего редактирования заметок.
//
//5. Консольный Интерфейс: Простой текстовый UI для взаимодействия.
//
//---
//
//📋 Техническое Задание:
//
//- Меню с командами для управления заметками при запуске
//- Команды для создания, редактирования, удаления и просмотра заметок
//- Вывод списка всех заметок с заголовками
//- Возможность просмотра полного текста выбранной заметки
//
//---
//
//⭐ Дополнительно:
//
//- Поиск заметок по заголовку или тексту
//- Возможность экспорта заметок в текстовый файл
//- Возможность сортировки заметок по дате создания или последнего редактирования
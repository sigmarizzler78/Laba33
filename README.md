# Лабораторная работа №3. Работа с базой данных.
Студент: Перов Иван, ИСП-221С

Приложение управляет таблицей "Одногруппники", позволяя добавлять, обновлять и просматривать записи.

## Работа приложения

Часть 1:  Базовое приложение для управления таблицей "Одногруппники".

Часть 2:  Расширенное приложение с переопределенной функцией onUpgrade для обработки изменений версии базы данных.

## Функциональность приложения:

### Часть 1:

• Создание базы данных: При первом запуске приложения создается база данных odnogruppniki.db и таблица odnogruppniki.

• Таблица "Одногруппники": Таблица содержит поля: ID (целое число, первичный ключ, автоинкремент), ФИО (текст), Время добавления записи (целое число, временная метка). Пример создания таблицы в onCreate метода DatabaseHelper:
```
db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIO + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)");
```
• Заполнение начальными данными: При первом запуске в таблицу добавляется 5 записей с данными одногруппников.  Пример вставки данных:
```
ContentValues values = new ContentValues();
values.put(COLUMN_FIO, "Кастырин Сегрей Алексеевич");
values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
db.insert(TABLE_NAME, null, values);
```
• MainActivity:  Содержит три кнопки:

 * Показать данные: Открывает новое активити (MainActivity2), отображающее все записи из таблицы в удобочитаемом формате (ID, ФИО, время добавления).
    
* Добавить запись: Добавляет новую запись в таблицу с текущей временной меткой и стандартным ФИО.
    
* Обновить последнюю запись:  Изменяет ФИО в последней добавленной записи на "Иванов Иван Иванович".


### Часть 2:

• Управление версиями БД:  В этой части переопределена функция onUpgrade в DatabaseHelper. При обновлении версии базы данных (в данном случае с версии 1 до 2), старая таблица "Одногруппники" удаляется, и создается новая таблица с измененной структурой (ФИО разделено на Фамилию, Имя и Отчество).  Пример onUpgrade:
```
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
```

• Новая структура таблицы:  Таблица "Одногруппники" теперь имеет поля: ID, lastName, firstName, middleName, timestamp.


## Структура проекта:

Проект содержит два приложения: myapplication3 (Часть 1) и m32 (Часть 2).  Каждый проект включает:

• MainActivity: Главное активити с тремя кнопками.

• MainActivity2: Активити для отображения данных из базы данных. Пример отображения данных:
```
TextView textViewData = findViewById(R.id.textViewData);
StringBuilder sb = new StringBuilder();
if (cursor.moveToFirst()) {
    do {
        ...
    } while (cursor.moveToNext());
}
textViewData.setText(sb.toString());
```
• DatabaseHelper: Класс для работы с базой данных (SQLiteOpenHelper).

## Сборка проекта:


1. Клонирование репозитория: Клонируйте репозиторий с помощью git clone [URL репозитория].
2. Открытие проекта: Откройте проект в Android Studio.
3. Запуск приложения: Нажмите кнопку "Run" в Android Studio.
![Screenshot_2024-12-04-20-00-38-790_com example m32](https://github.com/user-attachments/assets/9858acfb-02fc-44c1-83ae-bb07eac39a0a)
![Screenshot_2024-12-04-20-00-32-928_com example m32](https://github.com/user-attachments/assets/c56547f2-2f33-4b18-808e-721be721f657)
![Screenshot_2024-12-04-20-00-28-119_com example m32](https://github.com/user-attachments/assets/63a0f1a9-1842-4929-851a-0e74d17970df)
![Screenshot_2024-12-04-20-00-18-302_com example m32](https://github.com/user-attachments/assets/9c58b310-68c7-45f2-b109-f8fc33a99b95)
![Screenshot_2024_12_04_20_00_02_098_com_example_myapplication3](https://github.com/user-attachments/assets/fad6e9ce-24d1-4c07-b209-15dd131baf84)
![Screenshot_2024_12_04_19_59_57_159_com_example_myapplication3](https://github.com/user-attachments/assets/e2cff47d-9618-4494-a8d2-fda7178b74e2)
![Screenshot_2024_12_04_19_59_52_148_com_example_myapplication3](https://github.com/user-attachments/assets/e2d06b72-4718-420f-a9a4-efa7d6c75785)
![Screenshot_2024_12_04_19_59_48_348_com_example_myapplication3](https://github.com/user-attachments/assets/d36399bd-e748-47e8-94f4-7aa5912ba2de)

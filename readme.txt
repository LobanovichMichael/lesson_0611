
Способ	            Когда использовать
Files.readString	весь файл — одна строка
Files.readAllLines	нужен список строк
Scanner	            нужно читать числа/слова
BufferedReader	    большой файл, читаем построчно


Запись:
Способ	                    Поведение
Files.writeString(path, s)	перезапись
Files.write(path, list)	    перезапись, каждая строка списка — строка файла
StandardOpenOption.APPEND	дописать в конец

Важные правила:
try-with-resources (try (...) { ... }) — автоматически закрывает поток.
NoSuchFileException — подкласс IOException, ловить его нужно раньше.
Пустой catch — плохая практика, ошибка «теряется».
После catch переменные могут остаться null — проверяйте перед использованием.
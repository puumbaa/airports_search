### Библиотека autocomplete вводимого текста
___
Консольное Java-приложение, позволяющее быстро искать
данные аэропортов по вводимому пользователем тексту.

#### Предысловие
В ходе написания приложения я пришел к двум реализациям:
1. На основе префиксного дерева
2. На основе бинарного поиска

В качестве окончательного решения я выбрал вариант на основе дерева, но я не стал убирать второй вариант: может быть, вам будет интересно взглянуть.

Запускать проект нужно из ветки prod. Разница между ветками - небольшой фикс парсера. Я решил залить в другую ветку по той причине, что эти изменения я сделал спустя некоторое время после дедлайна, но без них тесты не проходили

### Запуск
1. `git clone https://github.com/puumbaa/airports_search.git`
2. `cd airports_search` 
3. `git checkout prod`
4. `mvn clean package`
5. `cd target`
6. `java -Xmx7m -jar airports-search-1.0-SNAPSHOT.jar [column]`

## Сборка проекта:

Требования: необходим Apache Ant 1.8 или выше, **Java8**

Для сборки проекта нужно запустить: 

	ant -f search-engine.xml

## Индексирование

Для построения индекса необходимы документы, обработанные с помощью myStem с опциями **-nl -e utf-8** и сложенные в одну директорию

Пример запуска программы для индекса (нужна **Java8**):

	./indexer docs/ index.txt

## Поиск

Пример запуска искалки:

	./searcher index.txt

Запросы оформляются так же, как и в описании задания:

	автомобиль
    found somelongdocname.html, someotherdocname.html and 38 more

	автомобиль AND каско
    found somedoc1.html

	зенит OR бенфика OR монако OR байер
    found somedoc42.html, somedoc43.html and 5 more

## Метрики (DCG, NDCG, PFound)

Пример запуска программы для тестирования метрик:

	./metrics

Пример ввода:

	10
	2 3 1 0 0 2 0 2 0 1

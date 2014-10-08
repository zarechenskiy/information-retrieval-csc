docs="$1"
target="$2"

java -classpath jar:out/production/information-retrieval:lib/annotations ru.csc.ir.index.Indexer $docs $target

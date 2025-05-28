




fun main() {
    val crawl = Crawl()
    val save = Save()
    val result = crawl.invoke()
    save.invoke(result)
}
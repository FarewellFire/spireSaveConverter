import tornadofx.*

class ConverterApp: App(MainView::class)

fun main(args: Array<String>) {
    launch<ConverterApp>(args)
}
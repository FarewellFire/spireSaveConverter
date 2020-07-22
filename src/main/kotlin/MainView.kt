import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.stage.FileChooser
import tornadofx.*
import java.awt.Desktop
import java.net.URI


class MainView: View("Slay The Spire Save Converter") {
    val savePath = SimpleStringProperty(this, "savePath", config.string("savePath"))
    val jsonPath = SimpleStringProperty(this, "jsonPath", config.string("jsonPath"))

    override val root = form {
        spacing = 25.0

        fieldset {
            spacing = 10.0
            hbox {
                spacing = 10.0
                field("Save path") {
                    val textFld = textfield(savePath)
                    textFld.setMinSize(500.0, 25.0)
                }
                val chooseSaveBtn = button("Choose save file...") {
                    action {
                        val fileChooser = FileChooser();
                        val file = fileChooser.showOpenDialog(null)
                        savePath.value = file?.absolutePath

                        saveConfig()
                    }
                }
                chooseSaveBtn.padding = Insets(10.0, 25.0, 10.0, 36.0)
            }
            hbox {
                spacing = 10.0
                field("Output JSON path") {
                    val textFld = textfield(jsonPath)
                    textFld.setMinSize(500.0, 25.0)
                }

                val chooseOutputBtn = button("Choose output file...") {
                    action {
                        val fileChooser = FileChooser();
                        val file = fileChooser.showOpenDialog(null)
                        jsonPath.value = file?.absolutePath

                        saveConfig()
                    }
                }
                chooseOutputBtn.padding = Insets(10.0, 25.0, 10.0, 25.0)
            }
        }

        hbox {
            spacing = 500.0
            val saveToJsonBtn = button("Convert save to JSON") {
                action {
                    saveToJson(savePath.value, jsonPath.value)
                    saveConfig()
                }
            }
            saveToJsonBtn.padding = Insets(10.0)

            val jsonToSaveBtn = button("Convert JSON to save") {
                action {
                    jsonToSave(savePath.value, jsonPath.value)
                    saveConfig()
                }
            }
            jsonToSaveBtn.padding = Insets(10.0)


        }

        hyperlink("View this project on GitHub").action {
            Desktop.getDesktop().browse(URI("https://github.com/DeanUA/spireSaveConverter"))
        }
    }

    private fun saveConfig()
    {
        with(config) {
            set("savePath" to savePath.value)
            set("jsonPath" to jsonPath.value)
            save()
        }
    }
}
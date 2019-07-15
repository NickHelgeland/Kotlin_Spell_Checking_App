package com.example.demo.view

import com.example.demo.Controller.*
import com.example.demo.Model.Dictionary
import com.example.demo.app.Styles
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import tornadofx.*

class MainView : View("Hello TornadoFX")
{
    init
    {
        TreeInitializer.initializeDictionary()
        TreeInitializer.initializeLearningTree()
    }

    val controller = MainViewController()

    val manager = TreeManager()

    val trainer = Trainer()

    val inPlaceChecker = InPlaceSpellCorrecter()

    val result = SimpleStringProperty()

    val progress = SimpleDoubleProperty()

    val topView = vbox {
        spacing = 15.0
        padding = Insets(10.0,30.0,50.0,30.0)
        alignment = Pos.CENTER

        button("Run in place correction") {
            action {
                inPlaceChecker.correctSpelling("/Users/Nick/eclipse-workspace/Spell_Checker/Data/training.txt",
                        "/Users/Nick/eclipse-workspace/Spell_Checker/Data/test.txt", progress)
            }
        }

        progressbar(progress) {
            minWidth = 500.0
        }

        button("Reset progress bar") {
            action {
                progress.set(0.0)
            }
        }
    }

    val bottomView = vbox {
        spacing = 15.0
        padding = Insets(10.0,30.0,50.0,30.0)
        alignment = Pos.CENTER

        val inputField = textfield {
            minWidth = 200.0
        }

        button("Check Validity") {
            action {
                val foundWord = manager.findWord(inputField.text, Dictionary.tree)
                if (foundWord != null)
                {
                    result.set(foundWord.data.valid.toString())
                }
                else
                {
                    result.set("not found")
                }
            }
        }

        label(result)
    }

    override val root = borderpane {
        label(title) {
            addClass(Styles.heading)
        }
        top = topView
        bottom = bottomView
    }
}
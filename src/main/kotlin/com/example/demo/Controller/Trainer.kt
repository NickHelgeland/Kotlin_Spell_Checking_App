package com.example.demo.Controller

import com.example.demo.Model.LearningTree
import com.example.demo.Model.Word
import java.io.File

class Trainer
{
    val manager = TreeManager()

    fun readInNew(path: String)
    {
        File(path).forEachLine {
            for (word in it.split(" "))
            {
                val re = Regex("[^A-Za-z0-9 ]")
                val tempWord = re.replace(word, "")
                manager.addNewWord(Word(tempWord), LearningTree.tree)
            }
        }
    }
}
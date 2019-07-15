package com.example.demo.Controller

import com.example.demo.Model.*
import java.io.*

object TreeInitializer
{
    val treeManager = TreeManager()

    fun initializeDictionary()
    {
        try
        {
            File("/Users/Nick/eclipse-workspace/Spell_Checker/Data/dictionary.txt").forEachLine {
                val tempWord = Word(it)
                tempWord.valid = true
                treeManager.addNewWord(tempWord, Dictionary.tree)
            }
        }
        catch (e: IOException)
        {
            println("Failed to load dictionary.")
            e.printStackTrace()
        }
    }

    fun initializeLearningTree()
    {
        try
        {
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data1.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data2.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data3.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data4.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data5.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data6.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data7.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data8.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data9.txt", LearningTree.tree)
            readFile("/Users/Nick/eclipse-workspace/Spell_Checker/Data/data10.txt", LearningTree.tree)
        }
        catch (e: IOException)
        {
            println("One or more files could not be loaded.")
            e.printStackTrace()
        }
    }

    private fun readFile(pathname: String, tree: WordTree)
    {
        File(pathname).forEachLine {
            val tempWord = Word(it)
            treeManager.addNewWord(tempWord, tree)
        }
    }

}
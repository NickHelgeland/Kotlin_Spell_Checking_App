package com.example.demo.Controller

import com.example.demo.Model.LearningTree
import com.example.demo.Model.Word
import com.example.demo.Model.WordTree

class MainViewController
{
    val spellChecker = SpellChecker()

    val treeManager = TreeManager()

    fun getCorrection(word: String): String
    {
        treeManager.addNewWord(Word(word), LearningTree.tree)

        var node = treeManager.findWord(word, LearningTree.tree)

        if (node == null)
        {
            node = WordTree(Word("Nothing found."))
        }

        return spellChecker.getCorrectWord(node)
    }
}
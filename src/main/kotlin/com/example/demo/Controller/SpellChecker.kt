package com.example.demo.Controller

import com.example.demo.Model.LearningTree
import com.example.demo.Model.Word
import com.example.demo.Model.WordTree

class SpellChecker
{
    val treeSearcher = TreeGroupSearch()

    val stringAlgorithm = DamerauLevenshteinAlgorithm(1,1,1,2)

    fun getCorrectWord(word: WordTree): String
    {
        var suggestion = Word("No suggestion available.")

        treeSearcher.getUpperLevelMatches(LearningTree.tree, word)
        treeSearcher.getOnLevelMatches(LearningTree.tree, word)
        treeSearcher.getLowerLevelMatches(LearningTree.tree, word)

        for (match in treeSearcher.listOfMatches)
        {
            if (match.valid)
            {
                if (suggestion.equals("No suggestion available."))
                {
                    suggestion = match
                }
                else if (stringAlgorithm.execute(word.data.value, match.value) < stringAlgorithm.execute(suggestion.value, word.data.value))
                {
                    suggestion = match
                }
            }
        }

        treeSearcher.clearMatches()

        return suggestion.value
    }
}
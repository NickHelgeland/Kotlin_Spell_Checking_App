package com.example.demo.Controller

import com.example.demo.Model.Dictionary
import com.example.demo.Model.LearningTree
import com.example.demo.Model.Word
import javafx.beans.property.SimpleDoubleProperty
import java.io.File

class InPlaceSpellCorrecter
{
    val manager = TreeManager()

    val controller = MainViewController()

    fun correctSpelling(input: String, output: String, progress: SimpleDoubleProperty)
    {
        val lines = File(input).readLines()
        var progressOfLines: Double = 100.0 / lines.size
        for (line in lines)
        {
            var progressOfWords: Double = progressOfLines / line.length
            val words = line.split(" ")
            for (string in words)
            {
                val re = Regex("[^A-Za-z' ]")
                val noPuncString = re.replace(string, "")
                manager.addNewWord(Word(noPuncString), LearningTree.tree)
                val word = manager.findWord(noPuncString.toLowerCase(), Dictionary.tree)
                var temp = noPuncString
                if (word == null)
                {
                    temp = controller.getCorrection(noPuncString)
                }
                else if (!word.data.valid)
                {
                    temp = controller.getCorrection(noPuncString)
                }
                File(output).appendText("${temp} ")
                progress.set(progress.get() + progressOfWords)
            }
        }
        File(output).appendText("\n")
        File(output).appendText("\n")
    }

    fun collectContextualData(lines: List<String>, word: String): Word
    {
        val previousLine = lines.get(0).split(" ")

        val currentLine = lines.get(1).split(" ")

        val nextLine = lines.get(2).split(" ")

        val lineIndex = currentLine.indexOf(word)

        var newWord = Word(word)

        if (lineIndex < 1 && lineIndex >= (currentLine.size - 1))
        {
            if (!previousLine.equals(""))
            {
                if (!newWord.wordBefore.containsKey(word))
                {
                    newWord.wordBefore.put(word,1)
                }
                else
                {
                    val newVal = newWord.wordBefore.get(word)!! + 1
                    newWord.wordBefore.set(word, newVal)
                }
            }
        }

        return newWord
    }

    fun getContextLines(lines: List<String>, line: String): ArrayList<String>
    {
        val filteredList = ArrayList<String>()

        if (lines.indexOf(line) < 1 && lines.indexOf(line) >= (lines.size - 1))
        {
            filteredList.add("")
            filteredList.add(line)
            filteredList.add("")
        }
        else if (lines.indexOf(line) < 1)
        {
            filteredList.add("")
            filteredList.add(line)
            filteredList.add(lines.get(lines.indexOf(line)))
        }
        else if (lines.indexOf(line) >= 1 && lines.indexOf(line) >= (lines.size - 1))
        {
            filteredList.add(lines.get(lines.indexOf(line) - 1))
            filteredList.add(line)
            filteredList.add("")
        }
        else if (lines.indexOf(line) >= 1 && lines.indexOf(line) <= (lines.size - 2))
        {
            filteredList.add(lines.get(lines.indexOf(line) - 1))
            filteredList.add(line)
            filteredList.add(lines.get(lines.indexOf(line) + 1))
        }

        return filteredList
    }

    private fun checkPunctuation(word: String): Boolean
    {
        var punctuation = false

        if (word.contains(".") || word.contains("?") || word.contains("!"))
        {
            punctuation = true
        }

        return punctuation
    }

}
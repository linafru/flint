package ru.ispras.modis.flint.textprocessing

import spark.RDD
import ru.ispras.modis.flint.instances.{Instance, BinaryFeature}

/**
 * Created with IntelliJ IDEA.
 * User: valerij
 * Date: 7/26/13
 * Time: 11:31 PM
 */
class TextToInstances(private val tokenizer: Tokenizer, private val preprocessor: TextPreprocessor) extends Serializable {
    def apply(texts: RDD[String]) = {
        val tokens = texts.map(text => tokenizer(preprocessor(text)))
        val tokenToIndex = tokens.flatMap(x => x).distinct().collect().zipWithIndex.toMap

        tokens.map(tokens => new Instance(tokens.map(token => new BinaryFeature(tokenToIndex(token)))))
    }
}

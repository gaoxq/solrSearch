package com.qing.language;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qing on 5/24/14.
 */
public class IKAnalyzerTokenizer implements Tokenizer{
    private Analyzer analyzer;

    public IKAnalyzerTokenizer() {
        analyzer = new IKAnalyzer(true);
    }

    public IKAnalyzerTokenizer(boolean isMaxWordLength) {
        analyzer = new IKAnalyzer(isMaxWordLength);
    }

    /**
     * word segmentation.
     */
    @Override
    public List<String> splits(String text, int minTermLength)
            throws IOException {
        List<String> terms = new ArrayList<String>();
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("", new StringReader(text));
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            ts.reset();

            while (ts.incrementToken()) {
                String str = term.toString();
                if (str.length() >= minTermLength) {
                    terms.add(str);
                }
            }
            ts.end();
        } finally {
            if (ts != null) {
                ts.close();
            }
        }
        return terms;
    }

}

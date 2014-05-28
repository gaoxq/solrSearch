package com.qing.language;

import java.io.IOException;
import java.util.List;

/**
 * Created by qing on 5/24/14.
 */
public interface Tokenizer {
    public List<String> splits(String text, int minTermLength) throws IOException;

}

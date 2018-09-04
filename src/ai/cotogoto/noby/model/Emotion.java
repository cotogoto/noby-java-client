package ai.cotogoto.noby.model;

import lombok.Data;

/**
 * 感情解析Data.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Emotion {

    /**
     * 単語.
     */
    private String word;

    /** */
    private double likeDislike;

    /** */
    private double joySad;

    /** */
    private double angerFear;

}

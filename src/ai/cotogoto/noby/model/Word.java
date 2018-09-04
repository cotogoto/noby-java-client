package ai.cotogoto.noby.model;

import lombok.Data;

/**
 * 単語Data.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Word {

    /**
     * 特徴.
     */
    private String feature;

    /**
     * 開始位置.
     */
    private String start;

    /**
     * 表面.
     */
    private String surface;

}

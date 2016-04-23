/*
 * タイトル：コトゴトプロジェクト.
 * 説明    ：
 * 著作権  ：Copyright(c) 2013-2014 LineDesign
 * 会社名  ：株式会社 LineDesign
 * 変更履歴：2014.12.03
 *         ：新規登録
 */
package ai.cotogoto.noby.model;

import lombok.Data;

/**
 * 単語Data.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Negaposi {

    /**
     * 単語.
     */
    private String word;

    /**
     * 判定.
     */
    private float  score;

}

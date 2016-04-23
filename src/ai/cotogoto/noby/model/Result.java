/*
 * タイトル：コトゴトプロジェクト.
 * 説明    ：
 * 著作権  ：Copyright(c) 2013-2014 LineDesign
 * 会社名  ：株式会社 LineDesign
 * 変更履歴：2014.12.03
 *         ：新規登録
 */
package ai.cotogoto.noby.model;

import java.util.List;

import lombok.Data;

/**
 * 人工無能Data.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Result {

    /** コマンドID. */
    private String              commandId;

    /** コマンド名. */
    private String              commandName;

    /** 会話. */
    private String              text;

    /** 返信タイプ. */
    private String              type;

    /** ムード. */
    private double              mood;

    /** ネガポジ. */
    private double              negaposi;

    /** ネガポジ結果. */
    private List <Negaposi> negaposiList;

    /** 解析結果. */
    private List <Word>     wordList;

    /** ART(人工物名). */
    private String              art;

    /** ORG(組織名). */
    private String              org;

    /** PSN(人名). */
    private String              psn;

    /** LOC(地名). */
    private String              loc;

    /** DAT(日付表現). */
    private String              dat;

    /** TIM(時刻表現).*/
    private String              tim;
}

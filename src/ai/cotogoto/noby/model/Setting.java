package ai.cotogoto.noby.model;

import lombok.Data;

/**
 * 設定Data.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Setting {

    /** Set the e-mail address at the time of login of Cotogoto.(Any). */
    private String  mail;

    /** Set the password at the time of login of Cotogoto.(Any). */
    private String  pass;

    /** After logging in to Cotogoto, set the API token for account settings.(Any). */
    private String  token;

    /** latitude. To use the check-in function of Cotogoto.(Any). */
    private Double  lat;

    /** longitude. To use the check-in function of Cotogoto.(Any). */
    private Double  lng;

    /**
     * Select the presence or absence of learning at the time of the conversation.(Any).
     * "0: No", "1: Yes"
     */
    private Integer study;

    /**
     * Select the personality of artificial intelligence.(Any).
     * "0: Normal", "1: Tsundere (woman)", "2: Tsundere (man)"
     */
    private Integer persona;

    /** Set the ending. */
    private String  ending;

}

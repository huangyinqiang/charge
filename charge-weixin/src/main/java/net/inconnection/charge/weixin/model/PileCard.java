package net.inconnection.charge.weixin.model;


import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class PileCard extends Model<PileCard> {
    public static final PileCard me = new PileCard();

	public PileCard queryPileCardByColumn(String column,String value){
        PileCard pileCard = me.findFirst("select * from yc_pile_card where " + column + "= ? ", value);
        return pileCard;
    }
}

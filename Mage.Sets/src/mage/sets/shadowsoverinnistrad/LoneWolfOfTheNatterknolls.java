/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

/**
 *
 * @author LevelX2
 */
public class LoneWolfOfTheNatterknolls extends CardImpl {

    public LoneWolfOfTheNatterknolls(UUID ownerId) {
        super(ownerId, 209, "Lone Wolf of the Natterknolls", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "SOI";
        this.subtype.add("Werewolf");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.nightCard = true;
        this.canTransform = true;

        // Whenever an opponent cast a spell during your turn, draw two cards.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(2), new FilterSpell("a spell"), true),
                new MyTurnCondition(),
                "Whenever an opponent casts a spell during your turn, draw two card."
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lone Wolf of the Natterknolls.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.getInstance(), TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public LoneWolfOfTheNatterknolls(final LoneWolfOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public LoneWolfOfTheNatterknolls copy() {
        return new LoneWolfOfTheNatterknolls(this);
    }
}
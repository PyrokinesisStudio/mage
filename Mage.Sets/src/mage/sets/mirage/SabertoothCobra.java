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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UnlessPaysDelayedEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Rarity;

/**
 *
 * @author LoneFox
 */
public class SabertoothCobra extends CardImpl {

    public SabertoothCobra(UUID ownerId) {
        super(ownerId, 136, "Sabertooth Cobra", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Snake");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Sabertooth Cobra deals damage to a player, he or she gets a poison counter. That player gets another poison counter at the beginning of his or her next upkeep unless he or she pays {2} before that turn.
        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("that player gets a poison counter");
        Ability ability = new DealsDamageToAPlayerTriggeredAbility(effect, false, true);
        effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("That player gets another poison counter.");
        ability.addEffect(new UnlessPaysDelayedEffect(new ManaCostsImpl("{2}"), effect, PhaseStep.UPKEEP, true,
            "That player gets another poison counter at the beginning of his or her next upkeep unless he or she pays {2} before that turn."));
        this.addAbility(ability);
    }

    public SabertoothCobra(final SabertoothCobra card) {
        super(card);
    }

    @Override
    public SabertoothCobra copy() {
        return new SabertoothCobra(this);
    }
}
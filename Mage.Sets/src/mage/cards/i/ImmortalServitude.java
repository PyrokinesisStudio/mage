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
package mage.cards.i;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ImmortalServitude extends CardImpl {

    public ImmortalServitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{W/B}{W/B}{W/B}");


        // Return each creature card with converted mana cost X from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ImmortalServitudeEffect());
        
    }

    public ImmortalServitude(final ImmortalServitude card) {
        super(card);
    }

    @Override
    public ImmortalServitude copy() {
        return new ImmortalServitude(this);
    }
}

class ImmortalServitudeEffect extends OneShotEffect {

    public ImmortalServitudeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each creature card with converted mana cost X from your graveyard to the battlefield";
    }

    public ImmortalServitudeEffect(final ImmortalServitudeEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalServitudeEffect copy() {
        return new ImmortalServitudeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        int count = source.getManaCostsToPay().getX();
        Set<Card> cards = you.getGraveyard().getCards(new FilterCreatureCard(), game);
        for (Card card : cards) {
            if (card.getConvertedManaCost() == count
                    && card != null) {
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
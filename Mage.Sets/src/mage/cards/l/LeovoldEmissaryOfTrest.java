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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

/**
 *
 * @author maxlebedev
 */
public class LeovoldEmissaryOfTrest extends CardImpl {

    public LeovoldEmissaryOfTrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each opponent can't draw more than one card each turn.  (Based on SpiritOfTheLabyrinth)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeovoldEmissaryOfTrestEffect()), new CardsAmountDrawnThisTurnWatcher());

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new LeovoldEmissaryOfTrestTriggeredAbility());
    }

    public LeovoldEmissaryOfTrest(final LeovoldEmissaryOfTrest card) {
        super(card);
    }

    @Override
    public LeovoldEmissaryOfTrest copy() {
        return new LeovoldEmissaryOfTrest(this);
    }
}

class LeovoldEmissaryOfTrestEffect extends ContinuousRuleModifyingEffectImpl {

    public LeovoldEmissaryOfTrestEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each opponent can't draw more than one card each turn";
    }

    public LeovoldEmissaryOfTrestEffect(final LeovoldEmissaryOfTrestEffect effect) {
        super(effect);
    }

    @Override
    public LeovoldEmissaryOfTrestEffect copy() {
        return new LeovoldEmissaryOfTrestEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CardsAmountDrawnThisTurnWatcher watcher = (CardsAmountDrawnThisTurnWatcher) game.getState().getWatchers().get(CardsAmountDrawnThisTurnWatcher.class.getSimpleName());
        Player controller = game.getPlayer(source.getControllerId());
        return watcher != null && controller != null && watcher.getAmountCardsDrawn(event.getPlayerId()) >= 1
                && game.isOpponent(controller, event.getPlayerId());
    }

}

class LeovoldEmissaryOfTrestTriggeredAbility extends TriggeredAbilityImpl {

    LeovoldEmissaryOfTrestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    LeovoldEmissaryOfTrestTriggeredAbility(final LeovoldEmissaryOfTrestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeovoldEmissaryOfTrestTriggeredAbility copy() {
        return new LeovoldEmissaryOfTrestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller != null && targetter != null
                && game.isOpponent(controller, targetter.getId())) {
            if (event.getTargetId().equals(controller.getId())) {
                return true; // Player was targeted
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && this.getControllerId().equals(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, " + super.getRule();
    }
}

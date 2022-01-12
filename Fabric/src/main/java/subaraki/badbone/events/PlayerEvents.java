package subaraki.badbone.events;

import net.minecraft.world.entity.player.Player;
import subaraki.badbone.ClientEventImpl;
import subaraki.badbone.EventImpl;
import subaraki.badbone.mod.BadBone;

public class PlayerEvents {

    public static void registerEvents() {
        LivingEntityTickCallback.EVENT.register(livingEntity -> {
            if (livingEntity instanceof Player player) {
                if (player.level.isClientSide()) {
                    ClientEventImpl.clientPlayerTick(player, BadBone.BACK_HURT, BadBone.KNEE_HURT);
                }
                EventImpl.playerTick(player, BadBone.BACK_HURT, BadBone.ARTHRITIS, BadBone.KNEE_HURT, BadBone.CHRONO, BadBone.BLIND);
            }
        });
        ItemCraftedEvent.EVENT.register((player, crafted) -> EventImpl.itemCraftedOrPicked(player, crafted, BadBone.CHRONO));
        ItemPickupEvent.EVENT.register((player, pickedUpStack) -> EventImpl.itemCraftedOrPicked(player, pickedUpStack, BadBone.CHRONO));
        PlayerHurtEvent.EVENT.register((player, source, amt) -> EventImpl.playerHurt(player, source, amt, BadBone.KNEE_HURT));
    }

}

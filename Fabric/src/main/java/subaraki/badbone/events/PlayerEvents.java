package subaraki.badbone.events;

import net.minecraft.world.entity.player.Player;
import subaraki.badbone.ClientEventImpl;
import subaraki.badbone.EventImpl;
import subaraki.badbone.mod.BadBoneFabric;

public class PlayerEvents {

    public static void registerEvents() {
        LivingEntityTickCallback.EVENT.register(livingEntity -> {
            if (livingEntity instanceof Player player) {
                if (player.level.isClientSide()) {
                    ClientEventImpl.clientPlayerTick(player, BadBoneFabric.BACK_HURT, BadBoneFabric.KNEE_HURT);
                }
                EventImpl.playerTick(player);
            }
        });
        ItemCraftedEvent.EVENT.register((player, crafted) -> EventImpl.itemCraftedOrPicked(player, crafted, BadBoneFabric.CHRONO));
        ItemPickupEvent.EVENT.register((player, pickedUpStack) -> EventImpl.itemCraftedOrPicked(player, pickedUpStack, BadBoneFabric.CHRONO));
        PlayerHurtEvent.EVENT.register((player, source, amt) -> EventImpl.playerHurt(player, source, amt, BadBoneFabric.KNEE_HURT));
    }

}

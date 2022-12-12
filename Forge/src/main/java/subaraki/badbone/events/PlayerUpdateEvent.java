package subaraki.badbone.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.badbone.BadBone;
import subaraki.badbone.EventImpl;
import subaraki.badbone.registry.BadBoneEffects;

@Mod.EventBusSubscriber(modid = BadBone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerUpdateEvent {

    @SubscribeEvent
    public static void playerUpdate(TickEvent.PlayerTickEvent event) {
        EventImpl.playerTick(event.player);
    }

    @SubscribeEvent
    public static void playerFall(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player)
            EventImpl.playerHurt(player, event.getSource(), event.getAmount(), BadBoneEffects.KNEE_HURT.get());
    }

    @SubscribeEvent
    public static void playerPickup(PlayerEvent.ItemPickupEvent event) {
        EventImpl.itemCraftedOrPicked(event.getEntity(), event.getStack(), BadBoneEffects.CHRONO.get());
    }

    @SubscribeEvent
    public static void playerCraft(PlayerEvent.ItemCraftedEvent event) {
        EventImpl.itemCraftedOrPicked(event.getEntity(), event.getCrafting(), BadBoneEffects.CHRONO.get());
    }
}

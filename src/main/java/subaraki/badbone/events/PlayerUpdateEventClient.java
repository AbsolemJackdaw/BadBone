package subaraki.badbone.events;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.badbone.mod.BadBone;
import subaraki.badbone.registry.BadBoneEffects;

@Mod.EventBusSubscriber(modid = BadBone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerUpdateEventClient {

    @SubscribeEvent
    public static void clientUpdatePose(TickEvent.PlayerTickEvent event) {
        if (event.phase != null && event.phase.equals(TickEvent.Phase.START))
            if (event.player instanceof LocalPlayer player) {
                if (player.hasEffect(BadBoneEffects.BACK_HURT.get()) || player.hasEffect(BadBoneEffects.KNEE_HURT.get())) {
                    player.input.shiftKeyDown = true;
                    player.setShiftKeyDown(true);
                }
            }
    }
}

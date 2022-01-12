package subaraki.badbone.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.badbone.BadBone;
import subaraki.badbone.ClientEventImpl;
import subaraki.badbone.registry.BadBoneEffects;

@Mod.EventBusSubscriber(modid = BadBone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerUpdateEventClient {

    @SubscribeEvent
    public static void clientUpdatePose(TickEvent.PlayerTickEvent event) {
        if (event.phase != null && event.phase.equals(TickEvent.Phase.START)) {
            ClientEventImpl.clientPlayerTick(event.player, BadBoneEffects.BACK_HURT.get(), BadBoneEffects.KNEE_HURT.get());
        }
    }
}

package subaraki.badbone;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

public class ClientEventImpl {
    public static void clientPlayerTick(Player player, MobEffect backHurt, MobEffect kneeHurt) {
        if (player instanceof LocalPlayer localPlayer) {
            if (localPlayer.hasEffect(backHurt) || localPlayer.hasEffect(kneeHurt)) {
                localPlayer.input.shiftKeyDown = true;
                localPlayer.setShiftKeyDown(true);
            }
        }
    }
}

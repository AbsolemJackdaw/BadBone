package subaraki.badbone.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import subaraki.badbone.registry.BadBoneEffects;

@Mixin(ForgeIngameGui.class)
public class NauseaForgeGuiMixin {

    @Redirect(method = "lambda$static$4", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private static boolean hasEffect(LocalPlayer instance, MobEffect mobEffect) {
        return instance.hasEffect(MobEffects.CONFUSION) || instance.hasEffect(BadBoneEffects.CHRONO.get());
    }
}

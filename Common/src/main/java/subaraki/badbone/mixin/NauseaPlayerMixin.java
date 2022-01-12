package subaraki.badbone.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import subaraki.badbone.platform.Services;

@Mixin(LocalPlayer.class)
public class NauseaPlayerMixin {

    @Redirect(method = "handleNetherPortalClient", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private boolean hasEffect(LocalPlayer instance, MobEffect mobEffect) {
        return instance.hasEffect(MobEffects.CONFUSION) || instance.hasEffect(Services.EFFECT_HELPER.getChronoEffect());
    }

    @Redirect(method = "handleNetherPortalClient", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getEffect(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/world/effect/MobEffectInstance;"))
    private MobEffectInstance getEffect(LocalPlayer instance, MobEffect mobEffect) {
        if (instance.hasEffect(MobEffects.CONFUSION))
            return instance.getActiveEffectsMap().get(MobEffects.CONFUSION);
        if (instance.hasEffect(Services.EFFECT_HELPER.getChronoEffect()))
            return instance.getActiveEffectsMap().get(Services.EFFECT_HELPER.getChronoEffect());
        //in essence this code is never reached, as there's a short circuit check for the instance of the effect first,
        //the check is hijacked above, and only checks for confusion or chrono
        return instance.getActiveEffectsMap().get(mobEffect);
    }
}

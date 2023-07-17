package subaraki.badbone.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import subaraki.badbone.effects.NotCurable;

import java.util.Iterator;

@Mixin(MilkBucketItem.class)
public abstract class MixinMilkBucket {
    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    public boolean injectEffects(LivingEntity entity) {
        if (entity.level().isClientSide) {
            return false;
        } else {
            Iterator<MobEffectInstance> iterator = entity.getActiveEffects().iterator();
            boolean $$1;
            for ($$1 = false; iterator.hasNext(); $$1 = true) {
                MobEffectInstance mobEffectInstance = iterator.next();
                if (!(mobEffectInstance.getEffect() instanceof NotCurable)) {
                    ((AccessorLivingEntity) entity).invokeOnEffectRemoved(mobEffectInstance);
                    iterator.remove();
                }
            }
            return $$1;
        }
    }
}

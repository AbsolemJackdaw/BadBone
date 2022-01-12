package subaraki.badbone.platform;

import net.minecraft.world.effect.MobEffect;
import subaraki.badbone.registry.BadBoneEffects;

public class EffectHelper implements IEffectHelper {
    @Override
    public MobEffect getBlindEffect() {
        return BadBoneEffects.BLIND.get();
    }

    @Override
    public MobEffect getChronoEffect() {
        return BadBoneEffects.CHRONO.get();
    }
}

package subaraki.badbone.platform;

import net.minecraft.world.effect.MobEffect;
import subaraki.badbone.mod.BadBone;

public class EffectHelper implements IEffectHelper {
    @Override
    public MobEffect getBlindEffect() {
        return BadBone.BLIND;
    }

    @Override
    public MobEffect getChronoEffect() {
        return BadBone.CHRONO;
    }
}

package subaraki.badbone.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import subaraki.badbone.events.ShaderRenderEvent;

import javax.annotation.Nullable;

@Mixin(GameRenderer.class)
public abstract class EffectToggleRIPMixin {

    @Shadow
    @Nullable
    public abstract PostChain currentEffect();

    /**
     * @author Subaraki
     */
    @Overwrite
    public void togglePostEffect() {
        if (this.currentEffect() != null && this.currentEffect().getName() != null)
            if (!this.currentEffect().getName().equals(ShaderRenderEvent.SHADER.toString())) {
                ((EffectField) this).setActive(!((EffectField) this).getEffectActive());
            }
    }

    @Mixin(GameRenderer.class)
    public interface EffectField {
        @Accessor("effectActive")
        void setActive(boolean isActive);

        @Accessor("effectActive")
        boolean getEffectActive();
    }
}

package subaraki.badbone.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.badbone.items.GlassesItem;
import subaraki.badbone.mod.BadBone;
import subaraki.badbone.registry.BadBoneEffects;

@Mod.EventBusSubscriber(modid = BadBone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShaderRenderEvent {

    private static final String SHADER_NAME = "badbone:shaders/post/blur.json";
    private static final ResourceLocation SHADER = new ResourceLocation(SHADER_NAME);

    @SubscribeEvent
    public static void renderShader(RenderWorldLastEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player.hasEffect(BadBoneEffects.BLIND.get()) && !(player.getInventory().getArmor(3).getItem() instanceof GlassesItem)) {
            PostChain shader = Minecraft.getInstance().gameRenderer.currentEffect();
            if (shader == null || !shader.getName().equals(SHADER_NAME)) {
                Minecraft.getInstance().gameRenderer.loadEffect(SHADER);
            }
        } else {
            PostChain shader = Minecraft.getInstance().gameRenderer.currentEffect();
            if (shader != null && shader.getName().equals(SHADER_NAME))
                Minecraft.getInstance().gameRenderer.shutdownEffect();
        }
    }
}
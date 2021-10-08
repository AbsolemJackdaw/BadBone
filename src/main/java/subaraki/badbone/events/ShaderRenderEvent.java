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
    private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");

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

//    @SubscribeEvent
//    public static void renderScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
//        Player player = Minecraft.getInstance().player;
//        if (player != null && player.hasEffect(BadBoneEffects.CHRONO.get())) {
//            float thefloat = 0.5f;
//            int i = Minecraft.getInstance().getWindow().getGuiScaledWidth();
//            int j = Minecraft.getInstance().getWindow().getGuiScaledHeight();
//            double d0 = Mth.lerp((double) thefloat, 2.0D, 1.0D);
//            float f = 0.2F * thefloat;
//            float f1 = 0.4F * thefloat;
//            float f2 = 0.2F * thefloat;
//            double d1 = (double) i * d0;
//            double d2 = (double) j * d0;
//            double d3 = ((double) i - d1) / 2.0D;
//            double d4 = ((double) j - d2) / 2.0D;
//            RenderSystem.disableDepthTest();
//            RenderSystem.depthMask(false);
//            RenderSystem.enableBlend();
//            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderTexture(0, NAUSEA_LOCATION);
//            Tesselator tesselator = Tesselator.getInstance();
//            BufferBuilder bufferbuilder = tesselator.getBuilder();
//            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//            bufferbuilder.vertex(d3, d4 + d2, -90.0D).uv(0.0F, 1.0F).endVertex();
//            bufferbuilder.vertex(d3 + d1, d4 + d2, -90.0D).uv(1.0F, 1.0F).endVertex();
//            bufferbuilder.vertex(d3 + d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
//            bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
//            tesselator.end();
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.defaultBlendFunc();
//            RenderSystem.disableBlend();
//            RenderSystem.depthMask(true);
//            RenderSystem.enableDepthTest();
//        }
//    }
}
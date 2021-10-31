package subaraki.badbone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostPass;
import subaraki.badbone.client.init.BadBoneShaders;
import subaraki.badbone.client.misc.DepthUtil;
import subaraki.badbone.client.shader.ExtendedPostChain;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	private static final Matrix4f
		PROJECTION_INVERSE = new Matrix4f(),
		VIEW_INVERSE = new Matrix4f();

	private static final float[]
		SHORT_SIGHT = new float[] { 4.0f, 20.0f, 0.0f, 32.0f },
		FAR_SIGHT = new float[] { 0.0f, 80.f, 24.0f, 0.0f };

	@Inject(at = @At("TAIL"), method = "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V")
	private void renderLevel(float frameTime, long nanoTime, PoseStack mtx, CallbackInfo ci)
	{
		applyBlur(mtx, frameTime);
	}

	@Unique
	private static void applyBlur(PoseStack mtx, float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		// if(!mc.player.hasEffect(BadBoneEffects.BLIND.get()) || !PlayerUpdateEvent.isInSurvivalMode(mc.player) || mc.player.getInventory().getArmor(3).getItem() instanceof GlassesItem)
		{
			// return;
		}

		ExtendedPostChain chain = BadBoneShaders.INSTANCE.getBlur();

		if(chain == null)
		{
			return;
		}

		PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
		PROJECTION_INVERSE.invert();

		VIEW_INVERSE.load(mtx.last().pose());
		VIEW_INVERSE.invert();

		float[] sightType = mc.player.getUUID().getMostSignificantBits() % 100 < 50 ? SHORT_SIGHT : FAR_SIGHT;

		for(PostPass pass : chain.passes)
		{
			EffectInstance shader = pass.getEffect();
	
			shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
			shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);
			shader.safeGetUniform("SightedType").set(sightType);
		}

		// if fabulous is enabled, restore our saved depth
		if(DepthUtil.fabulousOn)
		{
			DepthUtil.restoreDepth();
			DepthUtil.fabulousOn = false;
		}

		chain.process(frameTime);
		mc.getMainRenderTarget().bindWrite(false);
	}
}
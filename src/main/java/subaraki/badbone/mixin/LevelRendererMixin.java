package subaraki.badbone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;
import subaraki.badbone.client.init.BadBoneShaders;
import subaraki.badbone.client.shader.ExtendedPostChain;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
	private static final Matrix4f
		PROJECTION_INVERSE = new Matrix4f(),
		VIEW_INVERSE = new Matrix4f();

	@Shadow
	private PostChain transparencyChain;

	@Unique
	private RenderTarget depthCopy;

	@Unique
	private RenderTarget lazyDepthCopy()
	{
		Minecraft mc = Minecraft.getInstance();

		if(this.depthCopy == null)
		{
			this.depthCopy = new TextureTarget(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, Minecraft.ON_OSX);
		}

		return this.depthCopy;
	}

	@Inject(
		at = @At(
			value = "invoke",
			target = "Lnet/minecraft/client/renderer/PostChain;process(F)V",
			ordinal = 1),
		method = "renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V")
	private void renderLevelPreTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		// This is required because apparently the transparency (fabulous) shader decides it's a wonderful idea to nuke the depth buffer!!
		// FIXME this is better than nothing, but the depth of particles and other things is lost this way

		Minecraft mc = Minecraft.getInstance();

		RenderTarget main = mc.getMainRenderTarget();
		this.lazyDepthCopy();

		if(this.depthCopy.width != main.width || this.depthCopy.height != main.height)
		{
			this.depthCopy.resize(main.width, main.height, false);
		}

		this.depthCopy.setClearColor(0f, 0f, 0f, 0f);
		this.depthCopy.copyDepthFrom(main);
	}

	@Inject(
		at = @At(
			value = "TAIL"),
		method = "renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V")
	private void renderLevelPostTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		this.applyBlur(mtx, frameTime);
	}

	@Unique
	private void applyBlur(PoseStack mtx, float frameTime)
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

		EffectInstance shader = chain.getMainShader();

		PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
		PROJECTION_INVERSE.invert();

		VIEW_INVERSE.load(mtx.last().pose());
		VIEW_INVERSE.invert();

		shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
		shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);

		// if fabulous is enabled, restore our saved depth
		if(this.transparencyChain != null)
		{
			mc.getMainRenderTarget().copyDepthFrom(depthCopy);
		}

		chain.process(frameTime);
		mc.getMainRenderTarget().bindWrite(false);
	}
}
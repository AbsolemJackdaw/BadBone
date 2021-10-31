package subaraki.badbone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import subaraki.badbone.client.misc.DepthUtil;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
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

		DepthUtil.copyDepth();
		DepthUtil.fabulousOn = true;
	}
}
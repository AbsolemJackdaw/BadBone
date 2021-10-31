package subaraki.badbone.client.misc;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;

import net.minecraft.client.Minecraft;

public final class DepthUtil
{
	public static boolean fabulousOn;

	private static RenderTarget depthCopy; 

	private DepthUtil() {}

	public static RenderTarget lazyDepthCopy()
	{
		Minecraft mc = Minecraft.getInstance();

		if(depthCopy == null)
		{
			depthCopy = new TextureTarget(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, Minecraft.ON_OSX);
		}

		return depthCopy;
	}

	public static void copyDepth()
	{
		Minecraft mc = Minecraft.getInstance();

		RenderTarget main = mc.getMainRenderTarget();
		lazyDepthCopy();

		if(depthCopy.width != main.width || depthCopy.height != main.height)
		{
			depthCopy.resize(main.width, main.height, false);
		}

		depthCopy.setClearColor(0f, 0f, 0f, 0f);
		depthCopy.copyDepthFrom(main);
	}

	public static void restoreDepth()
	{
		Minecraft.getInstance().getMainRenderTarget().copyDepthFrom(lazyDepthCopy());
	}
}
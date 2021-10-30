package subaraki.badbone.client.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import subaraki.badbone.client.shader.ExtendedPostChain;
import subaraki.badbone.mod.BadBone;

@OnlyIn(Dist.CLIENT)
public final class BadBoneShaders implements ResourceManagerReloadListener
{
	public static final BadBoneShaders INSTANCE = new BadBoneShaders();

	protected final List<ExtendedPostChain> shaders = new ArrayList<>(2);

	protected ExtendedPostChain blur;

	private BadBoneShaders() {}

	@Override
	public void onResourceManagerReload(ResourceManager manager)
	{
		this.clear();

		try
		{
			blur = this.add(new ExtendedPostChain(BadBone.MODID, "blur"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void clear()
	{
		this.shaders.forEach(PostChain::close);
		this.shaders.clear();
	}

	public ExtendedPostChain add(ExtendedPostChain shader)
	{
		this.shaders.add(shader);
		return shader;
	}

	public ExtendedPostChain getBlur()
	{
		return blur;
	}
}
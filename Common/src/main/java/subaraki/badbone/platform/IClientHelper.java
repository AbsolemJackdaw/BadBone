package subaraki.badbone.platform;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;

import java.util.List;

public interface IClientHelper {
    int getPostChainScreenWidth(PostChain chain);

    int getPostChainScreenHeight(PostChain chain);

    List<PostPass> getPasses(PostChain chain);
}

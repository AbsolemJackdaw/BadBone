package subaraki.badbone.mod;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import subaraki.badbone.BadBone;

@Config(name = BadBone.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 1, max = 24_000 * 10)
    public int frequencyArthritis = 24_000;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 24_000 * 10)
    public int frequencyHurt = 24_000 / 3;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
    public int chanceHurt = 10;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
    public int frequencyKnee = 10;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 24_000 * 30)
    public int frequencyEyes = 24_000;
}

package subaraki.badbone.platform;

import subaraki.badbone.mod.BadBoneFabric;

public class ConfigHelper implements IConfigHelper {
    @Override
    public int getFrequencyArthritis() {
        return BadBoneFabric.config.frequencyArthritis;
    }

    @Override
    public int getFrequencyHurt() {
        return BadBoneFabric.config.frequencyHurt;
    }

    @Override
    public int getChanceHurt() {
        return BadBoneFabric.config.chanceHurt;
    }

    @Override
    public int getFrequencyKnee() {
        return BadBoneFabric.config.frequencyKnee;
    }

    @Override
    public int getFrequencyEyes() {
        return BadBoneFabric.config.frequencyEyes;
    }
}

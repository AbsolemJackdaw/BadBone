package subaraki.badbone.platform;

import subaraki.badbone.mod.ConfigData;

public class ConfigHelper implements IConfigHelper {

    @Override
    public int getFrequencyArthritis() {
        return ConfigData.SERVER.freqArth.get();
    }

    @Override
    public int getFrequencyHurt() {
        return ConfigData.SERVER.freqHurt.get();
    }

    @Override
    public int getChanceHurt() {
        return ConfigData.SERVER.chanHurt.get();
    }

    @Override
    public int getFrequencyKnee() {
        return ConfigData.SERVER.freqKnee.get();
    }

    @Override
    public int getFrequencyEyes() {
        return ConfigData.SERVER.freqEyes.get();
    }
}

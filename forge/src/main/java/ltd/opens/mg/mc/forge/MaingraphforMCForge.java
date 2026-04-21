package ltd.opens.mg.mc.forge;

import dev.architectury.platform.forge.EventBuses;
import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(MaingraphforMC.MODID)
public class MaingraphforMCForge {
    public MaingraphforMCForge() {
        EventBuses.registerModEventBus(MaingraphforMC.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        MaingraphforMC.init();
    }
}

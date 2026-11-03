package dxxi3d.simple_inventory_mending.forge;

import dxxi3d.simple_inventory_mending.ModLogger;
import dxxi3d.simple_inventory_mending.SharedConstants;
import net.minecraftforge.fml.common.Mod;

@Mod(SharedConstants.MOD_ID)
public final class MainForge {

    public MainForge() {
        ModLogger.sendInitialized();
    }
}

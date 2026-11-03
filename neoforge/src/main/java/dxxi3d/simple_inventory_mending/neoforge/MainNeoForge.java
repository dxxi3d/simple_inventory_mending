package dxxi3d.simple_inventory_mending.neoforge;

import dxxi3d.simple_inventory_mending.ModLogger;
import dxxi3d.simple_inventory_mending.SharedConstants;
import net.neoforged.fml.common.Mod;

@Mod(SharedConstants.MOD_ID)
public final class MainNeoForge {

    public MainNeoForge() {
        ModLogger.sendInitialized();
    }
}
